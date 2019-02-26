package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private long id;

    private String userName;

    private String password;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private List<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="player", fetch = FetchType.EAGER)
    private List<Score> scores;


    //CONSTRUCTORES

    public Player() { }

    public Player(String userName, String password)
    {
        this.userName = userName;
        this.setPassword(password);
    }


    //GETTERS AND SETTERS

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    public List<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(List<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }


    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //METODOS

    // add gameplayer method
    public void addGamePlayer(GamePlayer gamePlayer){
        gamePlayer.setPlayer(this);
        getGamePlayers().add(gamePlayer);
    }

    // add score method
    public void addScore(Score score){
        score.setPlayer(this);
        getScores().add(score);
    }

    // creating the getScore method that returns the total
    public float getScore(Player player){
        return getWins(player.getScores())*1 + getDraws(player.getScores())*((float) 0.5) + getLoses(player.getScores())*0;
    }

    public float getWins(List<Score> scores){
        return scores
                .stream()
                .filter(score -> score.getScore() == 1)
                .count();
    }

    public float getDraws(List<Score> scores){
        return scores
                .stream()
                .filter(score -> score.getScore() == 0.5)
                .count();
    }

    public float getLoses(List<Score> scores){
        return scores
                .stream()
                .filter(score -> score.getScore() == 0)
                .count();
    }
}
