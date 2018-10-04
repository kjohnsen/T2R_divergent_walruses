package model;

import clientserver.ServerProxy;
import interfaces.IServer;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.PlayerColor;
import results.GameResults;
import results.LoggedInResults;
import results.Results;

public class UIFacade {

    private static final UIFacade ourInstance = new UIFacade();
    private IServer serverProxy = ServerProxy.getInstance();
    private String authToken = null;

    public static UIFacade getInstance() {
        return ourInstance;
    }

    private UIFacade() {}

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public IServer getServerProxy() {
        return serverProxy;
    }

    public void setServerProxy(IServer serverProxy) {
        this.serverProxy = serverProxy;
    }

    //These should all return strings, or null if there is no error message
    public String loginUser(String username, String password) {
        LoggedInResults loggedInResults = serverProxy.loginUser(username, password);
        if(loggedInResults != null && loggedInResults.getSuccess()) {
            for(int i = 0; i < loggedInResults.getClientCommands().size(); ++i) {
                loggedInResults.getClientCommands().get(i).execute();
            }
        } else {
            //throw an error and have the presenter catch it and display it in a toast?
            if(loggedInResults == null) {
                return "Cannot reach server";
            } else {
                return loggedInResults.getErrorMessage();
            }
        }

        return null;
    }

    //returns null if no error, or an error message if there is one
    public String registerUser(String username, String password) {
        LoggedInResults loggedInResults = serverProxy.registerUser(username, password);
        if(loggedInResults != null && loggedInResults.getSuccess()) {
            for(int i = 0; i < loggedInResults.getClientCommands().size(); ++i) {
                loggedInResults.getClientCommands().get(i).execute();
            }
        } else {
            //throw an error and have the presenter catch it and display it in a toast?
            if(loggedInResults == null) {
                return "Cannot reach server";
            } else {
                return loggedInResults.getErrorMessage();
            }
        }

        return null;
    }

    public String joinGame(String gameName) {
        GameResults gameResults = serverProxy.joinGame(new GameName(gameName), authToken);
        if(gameResults != null && gameResults.getSuccess()) {
            for(int i = 0; i < gameResults.getClientCommands().size(); ++i) {
                gameResults.getClientCommands().get(i).execute();
            }
        } else {
            if(gameResults == null) {
                return "Cannot reach server";
            } else {
                return gameResults.getErrorMessage();
            }
        }
        return null;
    }

    public String createGame(String gameName, int numPlayers) {
        GameResults gameResults = serverProxy.createGame(gameName, numPlayers, authToken);
        if(gameResults != null && gameResults.getSuccess()) {
            for(int i = 0; i < gameResults.getClientCommands().size(); ++i) {
                gameResults.getClientCommands().get(i).execute();
            }
        } else {
            if(gameResults == null) {
                return "Cannot reach server";
            } else {
                return gameResults.getErrorMessage();
            }
        }
        return null;
    }

    public String startGame() {
        GameInfo gameInfo = ClientModel.getInstance().getCurrentGame();
        GameResults gameResults = serverProxy.startGame(gameInfo.getGameName(), authToken);
        if(gameResults != null && gameResults.getSuccess()) {
            for(int i = 0; i < gameResults.getClientCommands().size(); ++i) {
                gameResults.getClientCommands().get(i).execute();
            }
        } else {
            if(gameResults == null) {
                return "Cannot reach server";
            } else {
                return gameResults.getErrorMessage();
            }
        }
        return null;
    }

    public String claimColor(PlayerColor playerColor) {
        Results results = serverProxy.chooseColor(playerColor, authToken);
        if(results != null && results.getSuccess()) {
            for(int i = 0; i < results.getClientCommands().size(); ++i) {
                results.getClientCommands().get(i).execute();
            }
        } else {
            if(results == null) {
                return "Cannot reach server";
            } else {
                return results.getErrorMessage();
            }
        }
        return null;
    }



}
