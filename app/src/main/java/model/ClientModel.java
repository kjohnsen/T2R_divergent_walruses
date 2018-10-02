package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import modelclasses.GameName;
import modelclasses.GameInfo;
import modelclasses.User;

public class ClientModel extends Observable implements Observer {
    private User currentUser;

    private ArrayList<GameInfo> gameList = new ArrayList<>();

    private GameInfo currentGame;

    private static final ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {
    }

    public void reset() {
        currentGame = null;
        currentUser = null;
        gameList = new ArrayList<>();
    }

    @Override
    public void update(Observable observable, Object o) {
        // ClientModel observes objects (e.g. games), and can then report changes
        // to anyone observing ClientModel
        this.notifyObservers(o);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        this.notifyObservers();
    }

    public void setGameList(ArrayList<GameInfo> gameList) {
        this.gameList = gameList;
        for (GameInfo game : gameList) {
            game.addObserver(this);
        }
        this.notifyObservers();
    }

    public void setCurrentGame(GameInfo currentGame) {
        this.currentGame = currentGame;
        this.notifyObservers();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<GameInfo> getGameList() {
        return gameList;
    }
    public GameInfo getGame(GameName gameName) {
        for (GameInfo gameInfo : gameList) {
            if (gameInfo.getGameName().equals(gameName)) {
                return gameInfo;
            }
        }
        return null;
    }

    public GameInfo getCurrentGame() {
        return currentGame;
    }
}
