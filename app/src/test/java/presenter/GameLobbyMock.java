package presenter;

<<<<<<< HEAD
public class GameLobbyMock {
=======
import java.util.ArrayList;

import activity.IGameLobbyActivity;
import modelclasses.Player;
import modelclasses.PlayerColor;

public class GameLobbyMock implements IGameLobbyActivity {

    private boolean startGameEnabled = false;
    private ArrayList<Player> players;
    private ArrayList<String> colors;

    public boolean gameEnabled() {
        return startGameEnabled;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public GameLobbyMock() {
        colors = PlayerColor.getColors();
        players = new ArrayList<>();
    }

    public void reset() {

    }

    @Override
    public void setStartGameEnabled(boolean enabled) {
        startGameEnabled = enabled;
    }

    @Override
    public void updateAvailableColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    @Override
    public void updatePlayerList(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public void displayErrorMessage(String message) {
        //just here for mocking purposes
    }

    @Override
    public void startGame() {
        //just here for mocking purposes
    }
>>>>>>> master
}
