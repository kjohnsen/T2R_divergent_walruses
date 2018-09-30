package modelclasses;

import java.util.Observable;

public class Player extends Observable{
    private String username;
    private PlayerColor playerColor;

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
        this.notifyObservers();
    }
}
