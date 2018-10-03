package server;

import java.util.ArrayList;

import data.CommandManager;
import interfaces.IServer;
import model.ServerModel;
import modelclasses.GameInfo;
import modelclasses.Player;
import results.LoggedInResults;
import results.GameResults;
import results.Results;
import data.Command;
import modelclasses.GameName;
import modelclasses.PlayerColor;
import modelclasses.User;

import java.util.Arrays;
import java.util.UUID;

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
        serverModel.getAuthTokens().put(username, authToken);

        //**************** BUILD COMMAND OBJECT  **********************
        Command loginClientCommand = new Command("CommandFacade","loginUser", Arrays.asList(new Object[] {username, password}));
        //************************************************************

        //set results
        loggedInResults.setAuthToken(authToken);
        loggedInResults.getClientCommands().add(loginClientCommand);
        loggedInResults.setSuccess(true);


        ClientProxy clientProxy = new ClientProxy();
        User user = new User(username);
        clientProxy.loginUser(user, authToken);

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
        serverModel.getUsers().put(username, password);
        serverModel.getAuthTokens().put(username, authToken);

        Command registerUserCommand = new Command("CommandFacade", "registerUser", Arrays.asList(new Object[] {username, password}));

        //set results
        loggedInResults.setAuthToken(authToken);
        loggedInResults.getClientCommands().add(registerUserCommand);
        loggedInResults.setSuccess(true);

        ClientProxy clientProxy = new ClientProxy();
        User user = new User(username);
        clientProxy.registerUser(user, password);

        return loggedInResults;
    }

    public GameResults createGame(String name, int numPlayers, String clientAuthToken) {

        //creating a game doesn't add any players.. going to be all null.
        GameName gameName = new GameName(name);

        ArrayList<Player> players = new ArrayList<>();

        //create game info and add to server model.
        GameInfo gameInfo = new GameInfo(gameName, players, numPlayers);
        ServerModel.getInstance().getGames().put(gameName, gameInfo);

        //create commands for every client in the server model except the one that asked
        for(String authToken : ServerModel.getInstance().getAuthTokens().keySet()) {
            if (!authToken.equals(clientAuthToken)) {
                Command clientCommand = new Command("CommandFacade", "createGame", Arrays.asList(new Object[] {gameInfo}));
                CommandManager.getInstance().addCommand(authToken, clientCommand);
            }
        }

        Command createGameCommand = new Command("CommandFacade", "createGame", Arrays.asList(new Object[] {gameInfo}));

        GameResults gameResults = new GameResults(gameName);
        gameResults.getClientCommands().add(createGameCommand);
        gameResults.setSuccess(true);

        return gameResults;
    }

    public GameResults joinGame(GameName gameName, String clientAuthToken) {

        String username = ServerModel.getInstance().getAuthTokens().get(clientAuthToken);

        Player player = new Player(username);
        ServerModel.getInstance().getGames().get(gameName).addPlayer(player);

        //add commands to command manager that are not the client's command
        for(String authToken : ServerModel.getInstance().getAuthTokens().keySet()) {
            if (!authToken.equals(clientAuthToken)) {
                Command clientCommand = new Command("CommandFacade", "joinGame", Arrays.asList(player, gameName));
                CommandManager.getInstance().addCommand(authToken, clientCommand);
            }
        }

        //why do we need to have anything other than the command array in the results?
        Command joinGameCommand = new Command("CommandFacade", "createGame", Arrays.asList(new Object[] {player, gameName}));

        //there's no point in including this but whatever
        GameResults gameResults = new GameResults(gameName);
        gameResults.getClientCommands().add(joinGameCommand);
        gameResults.setSuccess(true);

        return gameResults;
    }

    public GameResults startGame(GameName gameName, String authToken) {
        return null;
    }

    public Results chooseColor(PlayerColor color, String authToken) {
        return null;
    }

    //not sure what's happening here either...
    //TODO: asdfasdf
    public ArrayList<Command> getCommands(String clientID, String authToken) {

        //wait is this supposed to return results??

        //what's the difference between client id and authtoken??
        return CommandManager.getInstance().getCommands(authToken);
    }

}
