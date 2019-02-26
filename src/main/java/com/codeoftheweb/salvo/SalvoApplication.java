package com.codeoftheweb.salvo;

//package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }

    Date date = new Date();
    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepository,
                                      GameRepository gameRepository,
                                      GamePlayerRepository gamePlayerRepository,
                                      ShipRepository shipRepository,
                                      SalvoRepository salvoRepository,
                                      ScoreRepository scoreRepository) {
        return (args) -> {
            // save a couple of customers

            Player player1 = new Player("guest12355@outlook.com", passwordEncoder().encode("qwe"));
            playerRepository.save(player1);

            Player player2 = new Player("fred@gmail.com", passwordEncoder().encode("asd"));
            playerRepository.save(player2);

            Player player3 = new Player("matias.f.guerrero@gmail.com", passwordEncoder().encode("zxc"));
            playerRepository.save(player3);

            Player player4 = new Player("j.bauer@ctu.gov", passwordEncoder().encode("iop"));
            playerRepository.save(player4);

            Player player5 = new Player("c.obrian@ctu.gov", passwordEncoder().encode("jkl"));
            playerRepository.save(player5);

            Player player6 = new Player("kim_bauer@gmail.com", passwordEncoder().encode("bnm"));
            playerRepository.save(player6);

            Player player7 = new Player("t.almeida@ctu.gov", passwordEncoder().encode("rty"));
            playerRepository.save(player7);

            Player player8 = new Player("t.almeida@ctu.gov", passwordEncoder().encode("fgh"));
            playerRepository.save(player8); // create player 2 with name fred

//            playerRepository.save(new Player("Jack"));
//            playerRepository.save(new Player("Chloe"));
//            playerRepository.save(new Player("Kim"));
//            playerRepository.save(new Player("David"));
//            playerRepository.save(new Player("Michelle"));

//            Game game1 = new Game(date);
//            gameRepository.save(game1);
//            Date date2 = date.from(date.toInstant().plusSeconds(3600));
//            Game game2 = new Game(date2);
//            gameRepository.save(game2);

            Game game1 = new Game(new Date());
            gameRepository.save(game1);

            Game game2 = new Game(new Date());
            gameRepository.save(game2);

            Game    game3 = new Game(new Date());
            gameRepository.save(game3);

            Game    game4 = new Game(new Date());
            gameRepository.save(game4);

            Game    game5 = new Game(new Date());
            gameRepository.save(game5);

            Game    game6 = new Game(new Date());
            gameRepository.save(game6);

            Game    game7 = new Game(new Date());
            gameRepository.save(game7);

            Game    game8 = new Game(new Date());
            gameRepository.save(game8);

            Game    game9 = new Game(new Date());
            gameRepository.save(game9);

            Date date = new Date();

//            Date gamePlayerCreationDate1 = new Date();
//            GamePlayer gamePlayer1 = new GamePlayer(gamePlayerCreationDate1, game1, player1);
//            gamePlayerRepository.save(gamePlayer1);
//            Date gamePlayerCreationDate2 = new Date();
//            GamePlayer gamePlayer2 = new GamePlayer(gamePlayerCreationDate2, game1, player2);
//            gamePlayerRepository.save(gamePlayer2);
//            Date gamePlayerCreationDate3 = new Date();
//            GamePlayer gamePlayer3 = new GamePlayer(gamePlayerCreationDate3, game2, player1);
//            gamePlayerRepository.save(gamePlayer3);
//            Date gamePlayerCreationDate4 = new Date();
//            GamePlayer gamePlayer4 = new GamePlayer(gamePlayerCreationDate4, game2, player3);
//            gamePlayerRepository.save(gamePlayer4);

            GamePlayer gamePlayer1 = new GamePlayer(date,game1, player1);
            gamePlayerRepository.save(gamePlayer1);

            GamePlayer gamePlayer2 = new GamePlayer(date,game1, player2);
            gamePlayerRepository.save(gamePlayer2);

            GamePlayer gamePlayer3 = new GamePlayer(date,game2, player2);
            gamePlayerRepository.save(gamePlayer3);

            GamePlayer gamePlayer4 = new GamePlayer(date,game2, player3);
            gamePlayerRepository.save(gamePlayer4);

            GamePlayer gamePlayer5 = new GamePlayer(date,game3, player3);
            gamePlayerRepository.save(gamePlayer5);

            GamePlayer gamePlayer6 = new GamePlayer(date,game3, player4);
            gamePlayerRepository.save(gamePlayer6);

            GamePlayer gamePlayer7 = new GamePlayer(date,game4, player5);
            gamePlayerRepository.save(gamePlayer7);

            GamePlayer gamePlayer8 = new GamePlayer(date,game4, player5);
            gamePlayerRepository.save(gamePlayer8);

            GamePlayer gamePlayer9 = new GamePlayer(date,game5, player6);
            gamePlayerRepository.save(gamePlayer9);

            GamePlayer gamePlayer10 = new GamePlayer(date,game6, player7);
            gamePlayerRepository.save(gamePlayer10);

            GamePlayer gamePlayer11 = new GamePlayer(date,game7, player8);
            gamePlayerRepository.save(gamePlayer11);

            GamePlayer gamePlayer12 = new GamePlayer(date,game8, player2);
            gamePlayerRepository.save(gamePlayer12);


            //Ships player 1
            List<String> shipLocations1 = new ArrayList<>();
            shipLocations1.add("H1");
            shipLocations1.add("H2");
            shipLocations1.add("H3");

            Ship ship1 = new Ship("destroyer", shipLocations1, gamePlayer1);
            shipRepository.save(ship1);

            List<String> shipLocations2 = new ArrayList<>();
            shipLocations2.add("A3");
            shipLocations2.add("A4");
            shipLocations2.add("A5");

            Ship ship2 = new Ship("submarine", shipLocations2, gamePlayer1);
            shipRepository.save(ship2);

            List<String> shipLocations3 = new ArrayList<>();
            shipLocations3.add("C5");
            shipLocations3.add("D5");
            shipLocations3.add("E5");
            shipLocations3.add("F5");

            Ship ship3 = new Ship("battleship", shipLocations3, gamePlayer1);
            shipRepository.save(ship3);

            List<String> shipLocations4 = new ArrayList<>();
            shipLocations4.add("B8");
            shipLocations4.add("C8");
            shipLocations4.add("D8");
            shipLocations4.add("E8");
            shipLocations4.add("F8");

            Ship ship4 = new Ship("carrier", shipLocations4, gamePlayer1);
            shipRepository.save(ship4);

            List<String> shipLocations5 = new ArrayList<>();
            shipLocations5.add("C2");
            shipLocations5.add("D2");

            Ship ship5 = new Ship("patrolboat", shipLocations5, gamePlayer1);
            shipRepository.save(ship5);

            //Ships player 2
            List<String> shipLocations6 = new ArrayList<>();
            shipLocations6.add("C2");
            shipLocations6.add("C3");

            Ship ship6 = new Ship("patrolboat", shipLocations6, gamePlayer2);
            shipRepository.save(ship6);

            List<String> shipLocations7 = new ArrayList<>();
            shipLocations7.add("A5");
            shipLocations7.add("B5");
            shipLocations7.add("C5");

            Ship ship7 = new Ship("destroyer", shipLocations7, gamePlayer2);
            shipRepository.save(ship7);

            List<String> shipLocations8 = new ArrayList<>();
            shipLocations8.add("F7");
            shipLocations8.add("F8");
            shipLocations8.add("F9");

            Ship ship8 = new Ship("submarine", shipLocations8, gamePlayer2);
            shipRepository.save(ship8);

            List<String> shipLocations9 = new ArrayList<>();
            shipLocations9.add("A8");
            shipLocations9.add("B8");
            shipLocations9.add("C8");
            shipLocations9.add("D8");

            Ship ship9 = new Ship("battleship", shipLocations9, gamePlayer2);
            shipRepository.save(ship9);

            List<String> shipLocations10 = new ArrayList<>();
            shipLocations10.add("E2");
                shipLocations10.add("E3");
            shipLocations10.add("E4");
            shipLocations10.add("E5");
            shipLocations10.add("E6");
            Ship ship10 = new Ship("carrier", shipLocations10, gamePlayer2);
            shipRepository.save(ship10);

            //save a couple of salvoes
            // set the turn and locations for specific gamePlayer
            Salvo salvo1 = new Salvo((long) 1, gamePlayer1, Arrays.asList(new String[]{"F2","E1"}));
            salvoRepository.save(salvo1);
            Salvo salvo2 = new Salvo((long) 2, gamePlayer2, Arrays.asList(new String[]{"F1","H2"}));
            salvoRepository.save(salvo2);

            //save a couple of scores
            // theere was only one game, it was won by player 1, therefore he will have a score of 1 and the opponent of zero.
            // both have the same finished time too.
            Score score1 = new Score(new Date(),game1, player1, 1);
            scoreRepository.save(score1);
            Score score2 = new Score(new Date(),game1, player2, 0);
            scoreRepository.save(score2);

            Score score3 = new Score(new Date(),game2, player2, 1);
            scoreRepository.save(score3);

            Score score4 = new Score(new Date(),game2, player2, (float)0.5);
            scoreRepository.save(score4);
        };
    }
    //Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName -> {
            Player player = playerRepository.findByUserName(inputName);
            if (player != null) {
                return new User(player.getUserName(), player.getPassword(),
                        AuthorityUtils.createAuthorityList("USER"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });

    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/web/games_3.html").permitAll()
                    .antMatchers("/api/games").permitAll()
                    .antMatchers("/api/leaderBoard").permitAll()
                    .antMatchers("/api/game_view/*").hasAnyAuthority("USER")
//                    .antMatchers("/rest/**").hasAuthority("ADMIN")
                    .antMatchers("/rest/**").permitAll()

                    .antMatchers( "/api/players").permitAll()

                    .and()
                    .formLogin()
                    .usernameParameter("name")
                    .passwordParameter("pwd")
                    .loginPage("/api/login");

            //Agregar luego el usuario ADMIN para gestionar mejores usos
            
            http.logout().logoutUrl("/api/logout");
            // turn off checking for CSRF tokens
            http.csrf().disable();

            // if user is not authenticated, just send an authentication failure response
            http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

            // if login is successful, just clear the flags asking for authentication
            http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

            // if login fails, just send an authentication failure response
            http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

            // if logout is successful, just send a success response
            http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        }

        private void clearAuthenticationAttributes(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            }
        }
    }
}


