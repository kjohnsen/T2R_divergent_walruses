package presenter;

import java.util.ArrayList;

import activity.IGameListActivity;
import model.ClientModel;
import model.UIFacade;
import modelclasses.GameInfo;

public class GameListPresenter implements IGameListPresenter {

    private IGameListActivity activity;
    private String gameName = null;

    public GameListPresenter(IGameListActivity activity) {
        this.activity = activity;
    }

    @Override
    public void createGameNameChanged(String gameName) {
        if (gameName.equals("")) {
            activity.setCreateGameEnabled(false);
            return;
        }
        ArrayList<GameInfo> games = ClientModel.getInstance().getGameList();
        for (GameInfo g : games) {
            if (g.getGameID().getName().equals(gameName)) {
                activity.setCreateGameEnabled(false);
                return;
            }
        }
        this.gameName = gameName;
        activity.setCreateGameEnabled(true);
    }

    @Override
    public void createGame(int numPlayers) {
        UIFacade.getInstance().createGame(gameName, numPlayers);
    }

    @Override
    public void joinGame(String gameName) {
        UIFacade.getInstance().joinGame(gameName);
    }
}
