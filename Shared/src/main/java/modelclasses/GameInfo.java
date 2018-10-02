package modelclasses;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class GameInfo extends Observable implements Observer {
    private GameName gameName;
    private ArrayList<Player> players;
    private int numPlayers;

    public GameInfo(GameName gameName, ArrayList<Player> players, int numPlayers) {
        this.gameName = gameName;
        this.numPlayers = numPlayers;
        setPlayers(players);
        this.notifyObservers();
    }

    @Override
    public void update(Observable observable, Object o) {
        // observes players and notifies observers (e.g., ClientModel) of changes
        this.notifyObservers(o);
    }

    public boolean addPlayer(Player player) {
        players.add(player);
        this.notifyObservers();
        player.addObserver(this);
        return true;
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
        for (Player p : players) {
            p.addObserver(this);
        }
        this.notifyObservers();
    }

    public int getNumPlayers() {
        return numPlayers;
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
