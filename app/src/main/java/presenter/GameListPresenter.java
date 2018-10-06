package presenter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import activity.IGameListActivity;
import model.ClientModel;
import model.UIFacade;
import modelclasses.GameInfo;

public class GameListPresenter implements IGameListPresenter, Observer {

    private IGameListActivity activity;
    private String gameName = null;

    public GameListPresenter(IGameListActivity activity) {
        this.activity = activity;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void createGameNameChanged(String gameName) {
        if (gameName.equals("")) {
            activity.setCreateGameEnabled(false);
            return;
        }
        ArrayList<GameInfo> games = UIFacade.getInstance().getGameList();
        for (GameInfo g : games) {
            if (g.getGameName().getName().equals(gameName)) {
                activity.setCreateGameEnabled(false);
                return;
            }
        }
        this.gameName = gameName;
        activity.setCreateGameEnabled(true);
    }

    @Override
    public void createGame(int numPlayers) {
        String message = UIFacade.getInstance().createGame(gameName, numPlayers);
        if (message != null) {
            activity.displayErrorMessage(message);
        }
    }

    @Override
    public void joinGame(String gameName) {
        String message = UIFacade.getInstance().joinGame(gameName);
        if (message != null) {
            activity.displayErrorMessage(message);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof GameInfo) {
            observable.deleteObserver(this);
            activity.goToGameLobby();
        } else {
            ArrayList<Object> array = (ArrayList<Object>) o;
            if (array.get(0) instanceof GameInfo) {
                ArrayList<GameInfo> games = new ArrayList<>();
                for (Object object : array) {
                    GameInfo game = (GameInfo) object;
                    if (game.getNumPlayers() - game.getPlayers().size() != 0) {
                        games.add(game);
                    }
                }
                activity.populateGameList(games);
            }
        }
    }
}
