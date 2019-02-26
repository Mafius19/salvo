package com.codeoftheweb.salvo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy="gamePlayer",fetch =FetchType.EAGER)
    private
    List<Ship> ships;

    //set the relation with salvo
    @OneToMany(mappedBy="gamePlayer")
    private List<Salvo> salvoes;

    // Constructor default
    public GamePlayer(){

    }

    public GamePlayer(Date joinDate, Game game, Player player){
        this.setJoinDate(joinDate);
        this.setGame(game);
        this.setPlayer(player);
    }

    // Getters and Setters
    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Ship> getShips(){
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(List<Salvo> salvoes) {
        this.salvoes = salvoes;
    }

    // extra methods
    // add gameplayer to the ship
    public void addShip(Ship ship){
        ship.setGamePlayer(this);
        getShips().add(ship);
    }

    // add salvoes to the gameplayer
    public void addSalvoes(Salvo salvo){
        salvo.setGamePlayer(this);
        getSalvoes().add(salvo);
    }

}
