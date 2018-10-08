package server;

import java.util.ArrayList;

import data.CommandManager;
import interfaces.IServer;
import model.ServerModel;
import modelclasses.GameInfo;
import modelclasses.Player;
import results.Results;
import data.Command;
import modelclasses.GameName;
import modelclasses.PlayerColor;
import modelclasses.User;

import java.util.Arrays;
import java.util.UUID;

public class ServerFacade implements IServer {

    private static final ServerFacade ourInstance = new ServerFacade();

    private ServerFacade() {}

    public static ServerFacade getInstance() {
        return ourInstance;
    }

    public Results loginUser(String username, String password) {

        //create the logged in results because you have to return something if it fails.
        //should is et success equal to false? yes because we assume failure until success
        Results results = new Results();

        //************** Check parameters with Model/DB *******************
        ServerModel serverModel = ServerModel.getInstance();
        if(!serverModel.checkUserExists(username)) {
            results.setErrorMessage("Username doesn't exist");
            return results;
        }
        if(!serverModel.checkPassword(username,password)) {
            results.setErrorMessage("Password incorrect");
            return results;
        }
        //*****************************************************************

        //once all checks have passed... get an authtoken.
        String authToken = UUID.randomUUID().toString();

        //this is where we will call DAO methods in the future
        serverModel.getAuthTokens().put(authToken, username);

        //**************** BUILD COMMAND OBJECT  **********************
        Command loginClientCommand = new Command("model.CommandFacade","loginUser", Arrays.asList(new Object[] {username, authToken}));
        //************************************************************

        //set results
        results.getClientCommands().add(loginClientCommand);
        results.setSuccess(true);
        results.setAuthToken(authToken);


        ClientProxy clientProxy = new ClientProxy();
        User user = new User(username, password);
        clientProxy.loginUser(user, authToken);

        return results;
    }

    public Results registerUser(String username, String password) {

        //create the logged in results because you have to return something if it fails.
        //should is et success equal to false? yes because we assume failure until success
        Results results = new Results();

        //first check to see if the username exists.
        ServerModel serverModel = ServerModel.getInstance();
        if(serverModel.checkUserExists(username)) {
            results.setErrorMessage("Username already exists!");
            return results;
        }

        //once all checks have passed... get an authtoken.
        String authToken = UUID.randomUUID().toString();
        User newUser = new User(username, password);
        serverModel.getUsers().put(username, newUser);
        serverModel.getAuthTokens().put(authToken, username);
        User user = new User(username, password);

        Command registerUserCommand = new Command("model.CommandFacade", "registerUser", Arrays.asList(user, authToken));

        //set results
        results.getClientCommands().add(registerUserCommand);
        results.setSuccess(true);
        results.setAuthToken(authToken);

        ClientProxy clientProxy = new ClientProxy();

        clientProxy.registerUser(user, password);

        return results;
    }

    public Results createGame(String name, Integer numPlayers, String clientAuthToken) {

        Results results = new Results();

        //creating a game doesn't add any players.. going to be all null.
        GameName gameName = new GameName(name);

        // check that the game name is unique
        if (ServerModel.getInstance().getGames().containsKey(gameName)) {
            results.setErrorMessage("Game name already exists");
            return results;
        }

        // check that numPlayers is valid
        if (numPlayers < 1 || numPlayers > 5) {
            results.setErrorMessage("Invalid player number");
            return results;
        }

        //create game info and add to server model.
        ArrayList<Player> players = new ArrayList<>();
        GameInfo gameInfo = new GameInfo(gameName, players, numPlayers);
        ServerModel.getInstance().getGames().put(gameName, gameInfo);

        // user joins the game they just created
        Player player = addUserToGame(clientAuthToken, gameInfo);

        // createGame command sent to all other clients
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.createGame(gameInfo, clientAuthToken);

        // createGame and joinGame commands created to be sent back to current client
        Command createGameCommand = new Command("model.CommandFacade", "createGame", Arrays.asList(new Object[] {gameInfo}));
        Command joinGameCommand = new Command("model.CommandFacade", "joinGame", Arrays.asList(new Object[] {gameInfo}));

        results.getClientCommands().add(createGameCommand);
        results.getClientCommands().add(joinGameCommand);
        results.setSuccess(true);

        return results;
    }

    public Results joinGame(GameName gameName, String clientAuthToken) {

        Results results = new Results();

        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        if (game == null) {
            results.setErrorMessage("Game does not exist");
            return results;
        }

        ArrayList<Player> gamePlayers = game.getPlayers();
        if (gamePlayers.size() == game.getNumPlayers()) {
            results.setErrorMessage("Game is full");
            return results;
        }

        Player player = addUserToGame(clientAuthToken, game);

        ClientProxy clientProxy = new ClientProxy();
        clientProxy.joinGame(player, gameName, clientAuthToken);

        Command joinGameCommand = new Command("model.CommandFacade", "joinGame", Arrays.asList(new Object[] {player, gameName}));

        results.getClientCommands().add(joinGameCommand);
        results.setSuccess(true);

        return results;
    }

    private Player addUserToGame(String clientAuthToken, GameInfo game) {
        String username = ServerModel.getInstance().getAuthTokens().get(clientAuthToken); // look up username using authToken
        Player player = new Player(username); // create new player
        game.addPlayer(player); // add player to game
        User user = ServerModel.getInstance().getUser(username);
        user.addGame(game.getGameName());
        return player;
    }

    public Results startGame(GameName gameName, String clientAuthToken) {

        Results results = new Results();

        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        if (game == null) {
            results.setErrorMessage("Game does not exist");
            return results;
        }

        ArrayList<Player> gamePlayers = game.getPlayers();
        if (gamePlayers.size() < 2) {
            results.setErrorMessage("Not enough players to start game");
            return results;
        }

        ClientProxy clientProxy = new ClientProxy();
        clientProxy.startGame(gameName, clientAuthToken);

        Command startGameCommand = new Command("model.CommandFacade", "startGame", Arrays.asList(new Object[] {gameName}));

        results.getClientCommands().add(startGameCommand);
        results.setSuccess(true);

        return results;
    }

    public Results chooseColor(PlayerColor color, GameName gameName, String clientAuthToken) {

        Results results = new Results();
        results.setSuccess(false);

        GameInfo gameInfo = ServerModel.getInstance().getGameInfo(gameName);
        if(!gameInfo.checkColorAvailable(color)) {
            results.setErrorMessage("Color already taken");
            return results;
        }

        String username = ServerModel.getInstance().getAuthTokens().get(clientAuthToken);
        gameInfo.getPlayer(username).setPlayerColor(color);

        ClientProxy clientProxy = new ClientProxy();
        clientProxy.claimColor(username, color, clientAuthToken);

        Command chooseColorCommand = new Command("model.CommandFacade", "claimColor", Arrays.asList(new Object[] {username, color}));
        results.getClientCommands().add(chooseColorCommand);
        results.setSuccess(true);

        return results;
    }

    public Results getCommands(String authToken) {
        Results results = new Results();
        results.setClientCommands(CommandManager.getInstance().getCommands(authToken));
        results.setSuccess(true);
        results.setAuthToken(authToken);
        return results;
    }

    //these two methods are necessary for the client side, but not the server side
    @Override
    public void setHostIP(String hostIP) { }
    @Override
    public void setHostPort(String hostPort) { }
}