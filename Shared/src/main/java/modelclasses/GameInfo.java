package modelclasses;

import java.util.ArrayList;
import java.util.Objects;

public class GameInfo {
    private GameName gameName;
    private ArrayList<Player> players = new ArrayList<>();
    private int numPlayers;

    public GameInfo(GameName gameName, ArrayList<Player> players, int numPlayers) {
        this.gameName = gameName;
        this.numPlayers = numPlayers;
        setPlayers(players);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public GameName getGameName() {
        return gameName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String username) {
        for (Player p : players) {
            if (p.getUsername().equals(username)) {
                return p;
            }
        }
        return null;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public boolean ready() {
        return (numPlayers - players.size() == 0);
    }

    public boolean checkColorAvailable(PlayerColor color) {
        for(Player player: getPlayers()){
            if(player.getPlayerColor().equals(color)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfo gameInfo = (GameInfo) o;
        return numPlayers == gameInfo.numPlayers &&
                gameName.equals(gameInfo.gameName) &&
                Objects.equals(players, gameInfo.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName, players, numPlayers);
    }
}
