package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class SalvoController {


    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    //Registrar a un jugador
    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(@RequestParam String name, @RequestParam String pwd) {
        if (name.isEmpty() || pwd.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "Missing data"), HttpStatus.FORBIDDEN);
        }
        if (playerRepository.findByUserName(name) !=  null) {
            return new ResponseEntity<>(makeMap("error", "User already exist!"), HttpStatus.FORBIDDEN);
        }
        playerRepository.save(new Player(name, passwordEncoder.encode(pwd)));
        return new ResponseEntity<>(makeMap("success", "User Created"),HttpStatus.CREATED);
    }
    private Map<String, Object> makeMap(String key, Object value){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createGame(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return new ResponseEntity<>(makeMap("error","Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
        //creo un nuevo juego y gameplayer asociado
        Game newGame = gameRepository.save(new Game(new Date()));
        GamePlayer newGP = gamePlayerRepository.save(new GamePlayer(new Date(),newGame,playerRepository.findByUserName(authentication.getName())));

        gamePlayerRepository.save(newGP);

        return new ResponseEntity<>(makeMap("gpid", newGP.getId()), HttpStatus.CREATED);

    }

    @RequestMapping("/games")
    public Map<String, Object> getPlayer(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            dto.put("player", "Guest");
        else {
            Player player = playerRepository.findByUserName(authentication.getName());
            dto.put("player", loggedPlayerDTO(player));
        }
        dto.put("games", getAllGames());
        return dto;
    }

    public Map<String,Object> loggedPlayerDTO(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("name",player.getUserName());

        return dto;
    }


    @RequestMapping(path = "/game/{gameId}/players", method = RequestMethod.POST) //JOIN GAME
    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable long id, Authentication authentication) {
        if (authentication == null) {
            return new ResponseEntity<>(makeMap("error", "You can't Join a Game if You're Not Logged In! Please Log in or Sign Up for a new player account."), HttpStatus.UNAUTHORIZED);
        } else {
            Game joinGame = gameRepository.findById(id).orElse(null);
            if (joinGame == null) {
                return new ResponseEntity<>(makeMap("error", "No such game"), HttpStatus.FORBIDDEN);
            } else {
                if (joinGame.getGamePlayers().size() > 1) {
                    return new ResponseEntity<>(makeMap("error", "Game is full"), HttpStatus.FORBIDDEN);
                } else {
                    GamePlayer newGameplayer = gamePlayerRepository.save(new GamePlayer(new Date(),joinGame, getLoggedPlayer(authentication)));
                    return new ResponseEntity<>(makeMap("gpid", newGameplayer.getId()), HttpStatus.CREATED);                          //cambio de clabe de gpid por gamePlayers
                    //return new ResponseEntity<>(makeMap("gpid", newGameplayer.getId()), HttpStatus.OK);                          //cambio de clabe de gpid por gamePlayers

                }
            }
        }
    }
//    public ResponseEntity<Map<String,Object>> joinGame(Authentication authentication, @PathVariable long gameid){
//        if (authentication == null){
//            return new ResponseEntity<>(makeMap("error","Unauthorized"), HttpStatus.UNAUTHORIZED);
//        }
//        if (!gameRepository.existsById(gameid)) {
//            return new ResponseEntity<>(makeMap("error", "No such game"), HttpStatus.FORBIDDEN);
//        }
//        if(gameRepository.findById(gameid).get().getGamePlayers().size() == 2){
//            return new ResponseEntity<>(makeMap("error", "Game is full"), HttpStatus.FORBIDDEN);
//        }
//        GamePlayer newGP = gamePlayerRepository.save(new GamePlayer(new Date(),gameRepository.findById(gameid).get(),
//                playerRepository.findByUserName(authentication.getName())));
//
//        return new ResponseEntity<>(makeMap("gpid", newGP.getId()), HttpStatus.CREATED);
//
//    }


    // Devuelve un List con todos los game
    public List<Object> getAllGames() {
        return gameRepository
                .findAll()
                .stream()
                .map(game -> makeGameDTO(game))
                .collect(Collectors.toList());
    }

    // Devuelve un Map con los atributos de un game específico
    private Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("creationDate", game.getCreationDate());
        dto.put("gamePlayers", getGamePlayerList(game.getGamePlayers()));
        dto.put("scores",makeScoreList(game.getScores()));

        return dto;
    }

    private List<Map<String, Object>> makeScoreList(List<Score> scores) {
        return scores
                .stream()
                .map(score -> ScoreDTO(score))
                .collect(Collectors.toList());
//                .findFirst();
    }

    private Map<String,Object> ScoreDTO(Score score){
        Map<String,Object> dto = new LinkedHashMap<String,Object>();
        dto.put("playerId",score.getPlayer().getId());
        dto.put("score",score.getScore());
        dto.put("finishedDate",score.getFinishDate());
        return dto;
    }

    // Devuelve un List de todos los gamePlayer de un game
    private List<Object> getGamePlayerList(List<GamePlayer> gamePlayers){
        return gamePlayers
                .stream()
                .map(gamePlayer -> makeGamePlayerDTO(gamePlayer))
                .collect(Collectors.toList());
    }

    // Devuelve un Map con los atributos de un gamePlayer específico
    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
        return dto;
    }

    // Devuelve un Map con los atributos de un player específico
    private Map<String, Object> makePlayerDTO(Player player){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("userName", player.getUserName());
        dto.put("score", makeScoreDTO(player));     //get and put score
        return dto;
    }




    public Map<String, Object> makeScoreDTO(Player player){
        Map<String,Object> dto = new LinkedHashMap<>();
        dto.put("name", player.getUserName());
        dto.put("total", player.getScore(player));
        dto.put("won", player.getWins(player.getScores()));
        dto.put("lost", player.getLoses(player.getScores()));
        dto.put("tied", player.getDraws(player.getScores()));

        return dto;
    }


    //Tarea3

    Player getLoggedPlayer(Authentication authentication){
        return playerRepository.findByUserName(authentication.getName());
    }

    @RequestMapping("/game_view/{gpid}")
    public ResponseEntity<Object> cheat(@PathVariable long gpid, Authentication authentication) {
        Player player = getLoggedPlayer(authentication);
        GamePlayer gamePlayer = gamePlayerRepository.findById(gpid).orElse(null);
        if (gamePlayer == null) {
            return new ResponseEntity<>(makeMap("error", "Forbidden"), HttpStatus.FORBIDDEN);
        }

        if (player.getId() != gamePlayer.getPlayer().getId()) {
            return new ResponseEntity<>(makeMap("error", "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
        //return gameViewDTO(gamePlayerRepository.findById(gpid).get());

        //private Map<String, Object> gameViewDTO(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("creationDate", gamePlayer.getGame().getCreationDate().getTime());
        dto.put("gamePlayers", getGamePlayerList(gamePlayer.getGame().getGamePlayers()));
        dto.put("ships", getShipList(gamePlayer.getShips()));
        dto.put("salvoes", getSalvoList(gamePlayer.getGame()));
        //return dto;
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //estoy en develop
//    private Map<String, Object> gameViewDTO(GamePlayer gamePlayer){
//        Map<String, Object> dto = new LinkedHashMap<String, Object>();
//        dto.put("id", gamePlayer.getId());
//        dto.put("creationDate", gamePlayer.getGame().getCreationDate());
//        dto.put("gamePlayers", getGamePlayerList(gamePlayer.getGame().getGamePlayers()));
//        dto.put("ships", getShipList(gamePlayer.getShips()));
//        dto.put("salvos", getSalvoList( gamePlayer.getGame()));
//
//        return dto;
//    }

    private List<Map<String, Object>> getShipList(List<Ship> ships){
        return ships
                .stream()
                .map(ship -> makeShipDTO(ship))
                .collect(Collectors.toList());
    }

    private Map<String, Object> makeShipDTO(Ship ship){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", ship.getType());
        dto.put("shipLocations", ship.getShipLocations());
        return dto;
    }

    // create the salvoe list for the json
    private List<Map<String,Object>> getSalvoList(Game game){
        List<Map<String,Object>> myList = new ArrayList<>();
        game.getGamePlayers().forEach(gamePlayer -> myList.addAll( makeSalvoList(gamePlayer.getSalvoes())));

        return myList;
    }

    // create the salvo dto
    private List<Map<String, Object>> makeSalvoList(List<Salvo> salvos) {
        return salvos
                .stream()
                .map(salvo -> salvoDTO(salvo))
                .collect(Collectors.toList());
    }
    private Map<String,Object> salvoDTO(Salvo salvo){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", salvo.getTurn());
        dto.put("player", salvo.getGamePlayer().getPlayer().getId());
        dto.put("Locations", salvo.getLocations());

        return dto;
    }

    //create the leaderBoard page.
    @RequestMapping("/leaderBoard")
    public List<Map<String,Object>> makeLeaderBoard(){
        return playerRepository
                .findAll()
                .stream()
                .map(player -> makePlayerDTO(player))
                .collect(Collectors.toList());
    }
}

