package modelclasses;

import java.util.ArrayList;

public class GameInfo {
    private GameID gameID;
    private ArrayList<Player> players;
    private int numPlayers;

    public GameInfo(GameID gameID, ArrayList<Player> players, int numPlayers) {
        this.gameID = gameID;
        this.players = players;
        this.numPlayers = numPlayers;
    }

    public boolean addPlayer(Player player) {
        players.add(player);
        return true;
    }

    public GameID getGameID() {
        return gameID;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

}
