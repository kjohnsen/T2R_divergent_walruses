package presenter;


import java.util.ArrayList;
import java.util.List;

import activity.IGameListView;
import modelclasses.GameInfo;

public class GameListMock implements IGameListView {

    private boolean createGameEnabled = false;
    private List<GameInfo> games;

    public boolean gameEnabled() {
        return createGameEnabled;
    }

    public List<GameInfo> getGames() { return games; }

    public void reset() {
        createGameEnabled = false;
        if (games != null) {
            games.clear();
        }
    }

    @Override
    public void populateGameList(List<GameInfo> games) {
        this.games = games;
    }

    @Override
    public void setCreateGameEnabled(boolean enabled) {
        createGameEnabled = enabled;
    }

    @Override
    public void goToGameLobby() {
        //just here for mocking purposes
    }

    @Override
    public void joinGame(String name) {
        //just here for mocking purposes
    }
}
