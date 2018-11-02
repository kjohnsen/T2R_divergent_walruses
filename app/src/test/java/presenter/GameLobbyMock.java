package presenter;

import java.util.ArrayList;
import java.util.List;

import activity.IGameLobbyView;
import modelclasses.Player;
import modelclasses.PlayerColor;

public class GameLobbyMock implements IGameLobbyView {

    private boolean startGameEnabled = false;
    private List<Player> players;
    private List<String> colors;

    public boolean gameEnabled() {
        return startGameEnabled;
    }

    public List<String> getColors() {
        return colors;
    }

    public List<Player> getPlayers() {
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
    public void updateAvailableColors(List<String> colors, int position) {
        this.colors = colors;
    }

    @Override
    public void updatePlayerList(List<Player> players) {
        this.players = players;
    }

    @Override
    public void startGame() {
        //just here for mocking purposes
    }
}
