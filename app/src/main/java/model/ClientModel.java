package model;

import java.util.ArrayList;
import java.util.Observable;

import modelclasses.GameName;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.User;

public class ClientModel extends Observable {
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

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        this.setChanged();
        this.notifyObservers();
    }

    /* this is so the model won't notify the GameListPresenter or the GameLobbyPresenter during unit
    tests, which throws an error */
    public void setCurrentUserTest(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setGameList(ArrayList<GameInfo> gameList) {
        this.gameList = gameList;
        this.setChanged();
        this.notifyObservers(gameList);
    }

    public void setCurrentGame(GameInfo currentGame) {
        this.currentGame = currentGame;
        this.setChanged();
        this.notifyObservers(currentGame);
    }

    public void setCurrentGamePlayers(ArrayList<Player> players) {
        currentGame.setPlayers(players);
        this.setChanged();
        this.notifyObservers(players);
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

    public boolean currentGameReady() {
        return currentGame.ready();
    }
}
