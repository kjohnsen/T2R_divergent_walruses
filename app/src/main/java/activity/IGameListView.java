package activity;

import java.util.List;

import modelclasses.GameInfo;

public interface IGameListView {
    void populateGameList(List<GameInfo> games);
    void setCreateGameEnabled(boolean enabled);
    void goToGameLobby();
}
