package presenter;


import java.util.ArrayList;

import activity.IGameListActivity;
import modelclasses.GameInfo;

public class GameListMock implements IGameListActivity {

    private boolean createGameEnabled = false;
    private ArrayList<GameInfo> games;

    public boolean gameEnabled() {
        return createGameEnabled;
    }

    public ArrayList<GameInfo> getGames() { return games; }

    public void reset() {
        createGameEnabled = false;
        if (games != null) {
            games.clear();
        }
    }

    @Override
    public void populateGameList(ArrayList<GameInfo> games) {
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
}
