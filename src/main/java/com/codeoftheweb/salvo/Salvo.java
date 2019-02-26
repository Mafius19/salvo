package com.codeoftheweb.salvo;


import javax.persistence.*;
import java.util.List;

// implementing salvo class
@Entity
public class Salvo {
    // salvo attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;    // salvo's unique ID
    private long turn;  // salvo's turn

    @ElementCollection
    @Column(name="locations")
    private List<String> locations; // salvo's locations

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer; // salvo's gameplayer

    //default constrctor
    public Salvo(){}

    public Salvo( long turn, GamePlayer gamePlayer, List<String> locations){
        setTurn(turn);
        setGamePlayer(gamePlayer);
        setLocations(locations);
    }

    // extra methods

    // setters and getters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTurn() {
        return turn;
    }

    public void setTurn(long turn) {
        this.turn = turn;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}

