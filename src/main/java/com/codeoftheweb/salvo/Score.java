package com.codeoftheweb.salvo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;        //score instance id

    private Date finishDate; // score finish date

    private float score;     //score's score

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game")
    private Game game;      //score's game

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player")
    private Player player;  //score's player

    //default constructor
    public Score(){}

    //methods
    public Score(Date finishDate, Game game, Player player, float score){
        setFinishDate(finishDate);
        setGame(game);
        setPlayer(player);
        setScore(score);
    }

    //setters and getters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
