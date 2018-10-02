package model;

import clientserver.ServerProxy;
import modelclasses.GameInfo;
import modelclasses.PlayerColor;
import results.LoggedInResults;

public class UIFacade {

    private static final UIFacade ourInstance = new UIFacade();

    public static UIFacade getInstance() {
        return ourInstance;
    }

    private UIFacade() {
    }

    //returns null if no error, or an error message if there is one
    public String loginUser(String username, String password) {
        LoggedInResults loggedInResults = ServerProxy.getInstance().loginUser(username, password);
        if(loggedInResults != null && loggedInResults.getSuccess()) {
            //execute command
            return null;
        } else {
            if(loggedInResults == null) {
                return "Server error-- no connection";
            } else {
                return loggedInResults.getErrorMessage();
            }
        }
    }

    //returns null if no error, or an error message if there is one
    public String registerUser(String username, String password) {
        LoggedInResults loggedInResults = ServerProxy.getInstance().registerUser(username, password);
        if(loggedInResults != null && loggedInResults.getSuccess()) {
            //execute command
            return null;
        } else {
            if(loggedInResults == null) {
                return "Server error-- no connection";
            } else {
                return loggedInResults.getErrorMessage();
            }
        }
    }

    public void joinGame(String gameName) {

    }

    public void createGame(String gameName, int numPlayers) {
        //GameInfo needs methods to get attributes
        //GameResults gameResults = ServerProxy.getInstance().createGame()

    }

    public void startGame() {

    }

    public void claimColor(PlayerColor playerColor) {

    }
}
