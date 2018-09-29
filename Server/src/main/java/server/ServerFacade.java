package server;

import java.util.ArrayList;

import interfaces.IServer;
import model.ServerModel;
import results.LoggedInResults;
import results.GameResults;
import results.Results;
import data.Command;
import modelclasses.GameID;
import modelclasses.PlayerColor;

import java.util.UUID;

public class ServerFacade implements IServer {

    public LoggedInResults loginUser(String username, String password) {

        //create the logged in results because you have to return something if it fails.
        //should is et success equal to false? yes because we assume failure until success
        LoggedInResults loggedInResults = new LoggedInResults();
        loggedInResults.setSuccess(false);

        //first check to see if the username exists.
        ServerModel serverModel = ServerModel.getInstance();
        if(!serverModel.checkUserExists(username)) {
            loggedInResults.setErrorMessage("Username doesn't exist");
            return loggedInResults;
        }

        //check password
        if(!serverModel.checkPassword(username,password)) {
            loggedInResults.setErrorMessage("Password incorrect");
            return loggedInResults;
        }

        //once all checks have passed... get an authtoken.
        String authToken = UUID.randomUUID().toString();
        serverModel.get_authTokens().put(username, authToken);

        //set results
        loggedInResults.setAuthToken(authToken);
        loggedInResults.setSuccess(true);

        String className = "iClient";
        String methodName = "loginUser";
        String[] paramTypes = new String[] {"String", "String"};
        Object[] paramValues = new Object[] {username, password};
        Command command = new Command(className, methodName, paramTypes, paramValues);
        Command[] commands = new Command[] {command};
        loggedInResults.setClientCommands(commands);

        ClientProxy clientProxy = new ClientProxy();
        clientProxy.loginUser(username, password);

        return loggedInResults;
    }

    public LoggedInResults registerUser(String username, String password) {

        //create the logged in results because you have to return something if it fails.
        //should is et success equal to false? yes because we assume failure until success
        LoggedInResults loggedInResults = new LoggedInResults();
        loggedInResults.setSuccess(false);

        //first check to see if the username exists.
        ServerModel serverModel = ServerModel.getInstance();
        if(serverModel.checkUserExists(username)) {
            loggedInResults.setErrorMessage("Username already exists!");
            return loggedInResults;
        }

        //once all checks have passed... get an authtoken.
        String authToken = UUID.randomUUID().toString();
        serverModel.get_users().put(username, password);
        serverModel.get_authTokens().put(username, authToken);

        //set results
        loggedInResults.setAuthToken(authToken);
        loggedInResults.setSuccess(true);

        String className = "iClient";
        String methodName = "registerUser";
        String[] paramTypes = new String[] {"String", "String"};
        Object[] paramValues = new Object[] {username, password};
        Command command = new Command(className, methodName, paramTypes, paramValues);
        Command[] commands = new Command[] {command};
        loggedInResults.setClientCommands(commands);

        ClientProxy clientProxy = new ClientProxy();
        clientProxy.registerUser(username, password);

        return loggedInResults;
    }

    public GameResults createGame(String name, int numPlayers) {
        return null;
    }

    public GameResults joinGame(GameID gameID) {
        return null;
    }

    public GameResults startGame(GameID gameID) {
        return null;
    }

    public Results chooseColor(PlayerColor color) {
        return null;
    }

    public ArrayList<Command> getCommands(String clientID) {
        return null;
    }

}
