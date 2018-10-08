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
    public String createGame(int numPlayers) {
        String message = UIFacade.getInstance().createGame(gameName, numPlayers);
        if (message != null) {
            return message;
        }
        //this is easier than going all the way back to the activity and initiating a JoinGameTask
        return UIFacade.getInstance().joinGame(gameName);
    }

    @Override
    public String joinGame(String gameName) {
        return UIFacade.getInstance().joinGame(gameName);
    }

    @Override
    public void getGameListInfo() {
        ArrayList<GameInfo> games = UIFacade.getInstance().getGameList();
        activity.populateGameList(games);
    }

    @Override
    public void update(Observable observable, Object o) {
        /* If you got a GameInfo object, that means you joined a game and this is the info the
        GameLobby needs */
        if (o instanceof GameInfo) {
            observable.deleteObserver(this);
            activity.goToGameLobby();
        } else {
            /* Otherwise, you
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
