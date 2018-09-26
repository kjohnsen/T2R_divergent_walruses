package presenter;

import activity.IGameListActivity;

public class GameListPresenter implements IGameListPresenter {

    private IGameListActivity activity;

    public GameListPresenter(IGameListActivity activity) {
        this.activity = activity;
    }
}
