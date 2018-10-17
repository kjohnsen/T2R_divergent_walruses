package activity;

import java.util.ArrayList;

import modelclasses.GameInfo;

public interface IGameListView {
    void populateGameList(ArrayList<GameInfo> games);
    void setCreateGameEnabled(boolean enabled);
    void goToGameLobby();
}
