package presenter;

import activity.IGameListActivity;

public class GameListPresenter implements IGameListPresenter {

    private IGameListActivity activity;

    public GameListPresenter(IGameListActivity activity) {
        this.activity = activity;
    }

    @Override
    public void createGame(String gameName, int numPlayers) {

    }

    @Override
    public void joinGame(String gameID) {

    }
}
