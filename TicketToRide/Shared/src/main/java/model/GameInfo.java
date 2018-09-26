package main.java.model;

import java.util.ArrayList;

public class GameInfo {
    private GameID gameID;
    private ArrayList<Player> players;

    public GameInfo(GameID gameID, ArrayList<Player> players) {
        this.gameID = gameID;
        this.players = players;
    }


}
