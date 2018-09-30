package server;

import java.lang.reflect.Type;
import java.util.ArrayList;

import interfaces.IServer;
import model.ServerModel;
import modelclasses.GameInfo;
import modelclasses.Player;
import results.LoggedInResults;
import results.GameResults;
import results.Results;
import data.Command;
import modelclasses.GameID;
import modelclasses.PlayerColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

// this will implement iServer, once that is a thing
public class ServerFacade implements IServer {

    public LoggedInResults loginUser(String username, String password) {

        //create the logged in results because you have to return something if it fails.
        //should is et success equal to false? yes because we assume failure until success
        LoggedInResults loggedInResults = new LoggedInResults();
        loggedInResults.setSuccess(false);

        //************** Check parameters with Model/DB *******************
        ServerModel serverModel = ServerModel.getInstance();
        if(!serverModel.checkUserExists(username)) {
            loggedInResults.setErrorMessage("Username doesn't exist");
            return loggedInResults;
        }
        if(!serverModel.checkPassword(username,password)) {
            loggedInResults.setErrorMessage("Password incorrect");
            return loggedInResults;
        }
        //*****************************************************************

        //once all checks have passed... get an authtoken.
        String authToken = UUID.randomUUID().toString();

        //this is where we will call DAO methods in the future
        serverModel.get_authTokens().put(username, authToken);

        //**************** BUILD COMMAND OBJECT  **********************
        Command loginClientCommand = new Command("CommandFacade","loginUser", Arrays.asList(new Object[] {username, password}));
        //************************************************************

        //set results
        loggedInResults.setAuthToken(authToken);
        loggedInResults.getClientCommands().add(loginClientCommand);
        loggedInResults.setSuccess(true);

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

        Command registerUserCommand = new Command("CommandFacade", "registerUser", Arrays.asList(new Object[] {username, password}));

        //set results
        loggedInResults.setAuthToken(authToken);
        loggedInResults.getClientCommands().add(registerUserCommand);
        loggedInResults.setSuccess(true);

        return loggedInResults;
    }

    public GameResults createGame(String name, int numPlayers) {

        //creating a game doesn't add any players.. going to be all null.
        GameID gameID = new GameID(name, UUID.randomUUID().toString());

        //players are going to be null for now... until they join the game.
        //this way you can still check the size of the arrayList.
        ArrayList<Player> players = new ArrayList<>();
        for(int i = 0; i < numPlayers; i++) {
            Player player = null;
            players.add(player);
        }

        //create game info and add to server model.
        GameInfo gameInfo = new GameInfo(gameID, players, numPlayers);
        ServerModel.getInstance().get_games().put(gameID, gameInfo);

        //create command for client side.
        //TODO: this should update everyone's screen.
        //TODO: so i have to create a command for every user with an authtoken??
        Command createGameCommand = new Command("CommandFacade", "createGame", Arrays.asList(new Object[] {gameInfo}));

        GameResults gameResults = new GameResults(gameID);
        gameResults.getClientCommands().add(createGameCommand);
        gameResults.setSuccess(true);

        return gameResults;
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
