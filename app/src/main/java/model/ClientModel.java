package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import modelclasses.GameInfo;
import modelclasses.User;

public class ClientModel extends Observable {
    private User currentUser;

    private ArrayList<GameInfo> gameList;

    private GameInfo currentGame;

    private static final ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        this.notifyObservers();
    }

    public void setGameList(ArrayList<GameInfo> gameList) {
        this.gameList = gameList;
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

    public GameInfo getCurrentGame() {
        return currentGame;
    }
}
