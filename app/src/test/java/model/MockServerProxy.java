package model;

import android.util.Log;

import java.util.ArrayList;

import data.Command;
import interfaces.IServer;
import modelclasses.GameName;
import modelclasses.PlayerColor;
import results.GameResults;
import results.LoggedInResults;
import results.Results;

public class MockServerProxy implements IServer {
    @Override
    public LoggedInResults loginUser(String username, String password) {
        if(username.equals("success")) {
            LoggedInResults loggedInResults = new LoggedInResults();
            loggedInResults.setSuccess(true);
            loggedInResults.setClientCommands(new ArrayList<Command>());
            return loggedInResults;
        } else if(username.equals("fail")) {
            LoggedInResults loggedInResults = new LoggedInResults();
            loggedInResults.setSuccess(false);
            loggedInResults.setErrorMessage("Password does not match");
            return loggedInResults;
        }

        return null;
    }

    @Override
    public LoggedInResults registerUser(String username, String password) {
        if(username.equals("success")) {
            LoggedInResults loggedInResults = new LoggedInResults();
            loggedInResults.setSuccess(true);
            loggedInResults.setClientCommands(new ArrayList<Command>());
            return loggedInResults;
        } else if(username.equals("fail")) {
            LoggedInResults loggedInResults = new LoggedInResults();
            loggedInResults.setSuccess(false);
            loggedInResults.setErrorMessage("User already exists");
            return loggedInResults;
        }

        return null;
    }

    @Override
    public GameResults createGame(String name, int numPlayers, String authToken) {
        if(name.equals("success")) {
            GameResults gameResults = new GameResults(new GameName("fake"));
            gameResults.setSuccess(true);
            gameResults.setClientCommands(new ArrayList<Command>());
            return gameResults;
        } else if(name.equals("fail")) {
            GameResults gameResults = new GameResults(new GameName("fake"));
            gameResults.setSuccess(false);
            gameResults.setErrorMessage("Game already exists");
            return gameResults;
        }

        return null;
    }

    @Override
    public GameResults joinGame(GameName gameName, String authToken) {
        if(gameName.getName().equals("success")) {
            GameResults gameResults = new GameResults(new GameName("fake"));
            gameResults.setSuccess(true);
            gameResults.setClientCommands(new ArrayList<Command>());
            return gameResults;
        } else if(gameName.getName().equals("fail")) {
            GameResults gameResults = new GameResults(new GameName("fake"));
            gameResults.setSuccess(false);
            gameResults.setErrorMessage("Game is full");
            return gameResults;
        }

        return null;
    }

    @Override
    public GameResults startGame(GameName gameName, String authToken) {
        if(gameName.getName().equals("success")) {
            GameResults gameResults = new GameResults(new GameName("fake"));
            gameResults.setSuccess(true);
            gameResults.setClientCommands(new ArrayList<Command>());
            return gameResults;
        } else if(gameName.getName().equals("fail")) {
            GameResults gameResults = new GameResults(new GameName("fake"));
            gameResults.setSuccess(false);
            gameResults.setErrorMessage("Game cannot start");
            return gameResults;
        }

        return null;
    }

    @Override
    public Results chooseColor(PlayerColor color, String authToken) {
        if(authToken.equals("success")) {
            GameResults gameResults = new GameResults(new GameName("fake"));
            gameResults.setSuccess(true);
            return gameResults;
        } else if(authToken.equals("fail")) {
            GameResults gameResults = new GameResults(new GameName("fake"));
            gameResults.setSuccess(false);
            gameResults.setErrorMessage("Color already taken");
            return gameResults;
        }

        return null;
    }

    @Override
    public ArrayList<Command> getCommands(String clientID, String authToken) {
        return null;
    }
}