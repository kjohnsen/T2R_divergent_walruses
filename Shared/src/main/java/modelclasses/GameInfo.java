package modelclasses;

import java.util.ArrayList;

public class GameInfo {
    private GameID gameID;
    private String name;
    private ArrayList<Player> players;
    private int totalPlayers;

    public GameInfo(String name, ArrayList<Player> players, int totalPlayers) {
        this.name = name;
        this.players = players;
        this.totalPlayers = totalPlayers;
    }

    public GameID getGameID() { return gameID; }
    public String getName() { return name; }
    public ArrayList<Player> getPlayers() { return players; }
    public int getTotalPlayers() { return totalPlayers; }

}
