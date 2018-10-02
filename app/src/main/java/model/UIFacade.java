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

    //These should all return strings, or null if there is no error message
    public void loginUser(String username, String password) {
        LoggedInResults loggedInResults = ServerProxy.getInstance().loginUser(username, password);
        if(loggedInResults != null && loggedInResults.getSuccess()) {
            for(int i = 0; i < loggedInResults.getClientCommands().size(); ++i) {
                loggedInResults.getClientCommands().get(i).execute();
            }
        } else {
            if(loggedInResults == null) {
                //results never got server, make message
            } else {
                //user or server error, get message from results
            }
        }
    }

    public void registerUser(String username, String password) {
        LoggedInResults loggedInResults = ServerProxy.getInstance().registerUser(username, password);
        if(loggedInResults != null && loggedInResults.getSuccess()) {
            for(int i = 0; i < loggedInResults.getClientCommands().size(); ++i) {
                loggedInResults.getClientCommands().get(i).execute();
            }
        } else {
            //throw an error and have the presenter catch it and display it in a toast?
            if(loggedInResults == null) {
                //results never got server, make message
            } else {
                //user or server error, get message from results
            }
        }
    }

    public void joinGame(String gameName) {

    }

    public void createGame(String gameName, int numPlayers) {
        //GameInfo needs methods to get attributes
        //GameResults gameResults = ServerProxy.getInstance().createGame()

    }

    public void startGame(GameInfo gameInfo) {

    }

    public void claimColor(PlayerColor playerColor) {

    }
}
