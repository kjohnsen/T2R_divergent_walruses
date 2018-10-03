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
        serverModel.getAuthTokens().put(authToken, username);

        //**************** BUILD COMMAND OBJECT  **********************
        Command loginClientCommand = new Command("CommandFacade","loginUser", Arrays.asList(new Object[] {username, password}));
        //************************************************************

        //set results
        loggedInResults.setAuthToken(authToken);
        loggedInResults.getClientCommands().add(loginClientCommand);
        loggedInResults.setSuccess(true);


        ClientProxy clientProxy = new ClientProxy();
        User user = new User(username, password);
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
        User newUser = new User(username, password);
        serverModel.getUsers().put(username, newUser);
        serverModel.getAuthTokens().put(authToken, username);

        Command registerUserCommand = new Command("CommandFacade", "registerUser", Arrays.asList(new Object[] {username, password}));

        //set results
        loggedInResults.setAuthToken(authToken);
        loggedInResults.getClientCommands().add(registerUserCommand);
        loggedInResults.setSuccess(true);

        ClientProxy clientProxy = new ClientProxy();
        User user = new User(username, password);
        clientProxy.registerUser(user, password);

        return loggedInResults;
    }

    public GameResults createGame(String name, int numPlayers, String clientAuthToken) {

        //creating a game doesn't add any players.. going to be all null.
        GameName gameName = new GameName(name);

        //players are going to be null for now... until they join the game.
        //this way you can still check the size of the arrayList.
        ArrayList<Player> players = new ArrayList<>();
        for(int i = 0; i < numPlayers; i++) {
            Player player = null;
            players.add(player);
        }

        //create game info and add to server model.
        GameInfo gameInfo = new GameInfo(gameName, players, numPlayers);
        ServerModel.getInstance().getGames().put(gameName, gameInfo);

        //create commands for every client in the server model except the one that asked
        //TODO: this adds a command for every auth token... we need to clear authtokens at some point.
        //TODO: how do I exclude the client that is calling from the command manager?
        for(String authToken : ServerModel.getInstance().getAuthTokens().values()) {
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

        GameResults gameResults = new GameResults(gameName);
        gameResults.setSuccess(false);

        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        if (game == null) {
            gameResults.setErrorMessage("Game does not exist");
            return gameResults;
        }

        ArrayList<Player> gamePlayers = game.getPlayers();
        if (gamePlayers.size() > 4) {
            gameResults.setErrorMessage("Game is full");
            return gameResults;
        }

        String username = ServerModel.getInstance().getAuthTokens().get(clientAuthToken); // look up username using authToken
        Player player = new Player(username); // create new player
        game.addPlayer(player); // add player to game
        User user = ServerModel.getInstance().getUser(username); // look up user
        user.addGame(gameName); // add game to user game list

        for(String authToken : ServerModel.getInstance().getAuthTokens().values()) {
            if (!authToken.equals(clientAuthToken)) {
                Command clientCommand = new Command("CommandFacade", "joinGame", Arrays.asList(new Object[] {player, gameName}));
                CommandManager.getInstance().addCommand(authToken, clientCommand);
            }
        }

        Command joinGameCommand = new Command("CommandFacade", "joinGame", Arrays.asList(new Object[] {player, gameName}));

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

    public ArrayList<Command> getCommands(String clientID, String authToken) {
        return null;
    }

}
