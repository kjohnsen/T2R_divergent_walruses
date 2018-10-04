package activity;

import java.util.ArrayList;

import modelclasses.GameInfo;

public interface IGameListActivity {
    void populateGameList(ArrayList<GameInfo> games);
    void setCreateGameEnabled(boolean enabled);
    void goToGameLobby();
}
