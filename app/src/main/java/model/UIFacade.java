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

    public static UIFacade getInstance() {
        return ourInstance;
    }

    private UIFacade() {}

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
            if(loggedInResults == null) {
                return "Cannot reach server";
            } else {
                return loggedInResults.getErrorMessage();
            }
        }

        return null;
    }

    public String registerUser(String username, String password) {
        LoggedInResults loggedInResults = serverProxy.registerUser(username, password);
        if(loggedInResults != null && loggedInResults.getSuccess()) {
            for(int i = 0; i < loggedInResults.getClientCommands().size(); ++i) {
                loggedInResults.getClientCommands().get(i).execute();
            }
        } else {
            if(loggedInResults == null) {
                return "Cannot reach server";
            } else {
                return loggedInResults.getErrorMessage();
            }
        }

        return null;
    }

    public String joinGame(String gameName) {
        GameResults gameResults = serverProxy.joinGame(new GameName(gameName), ServerProxy.getInstance().getAuthToken());
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
        GameResults gameResults = serverProxy.createGame(gameName, numPlayers, ServerProxy.getInstance().getAuthToken());
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

    public String startGame(GameInfo gameInfo) {
        GameResults gameResults = serverProxy.startGame(gameInfo.getGameName(), ServerProxy.getInstance().getAuthToken());
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
        Results results = serverProxy.chooseColor(playerColor, ServerProxy.getInstance().getAuthToken());
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
