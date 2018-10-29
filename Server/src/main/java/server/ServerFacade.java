package server;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import data.CommandManager;
import data.Serializer;
import interfaces.IServer;
import model.ServerModel;
import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.Player;
import results.Results;
import data.Command;
import modelclasses.GameName;
import modelclasses.PlayerColor;
import modelclasses.User;
import modelclasses.TrainCard;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServerFacade implements IServer {

    private static final ServerFacade ourInstance = new ServerFacade();

    private ServerFacade() {}

    public static ServerFacade getInstance() {
        return ourInstance;
    }

    public static Results _selectDestinationCards(ArrayList<DestinationCard> tickets, GameName name, String authToken) {
        return ourInstance.selectDestinationCards(tickets, name, authToken);
    }

    public static Results _selectTrainCard(int index, GameName name, String authtoken) {
        return ourInstance.selectTrainCard(index, name, authtoken);
    }

    public static Results _drawTrainCard(GameName name, String authtoken) {
        return ourInstance.drawTrainCard(name, authtoken);
    }

    public static Results _drawDestinationCards(GameName name, String authtoken) {
        return ourInstance.drawDestinationCards(name, authtoken);
    }

    public static Results _registerUser(String username, String password) {
        return ourInstance.registerUser(username, password);
    }
    public static Results _loginUser(String username, String password) {
        return ourInstance.loginUser(username, password);
    }
    public static Results _createGame(String name, Integer numPlayers, String clientAuthToken) {
        return ourInstance.createGame(name, numPlayers, clientAuthToken);
    }
    public static Results _joinGame(GameName gameName, String clientAuthToken) {
        return ourInstance.joinGame(gameName, clientAuthToken);
    }
    public static Results _chooseColor(PlayerColor color, GameName gameName, String clientAuthToken) {
        return ourInstance.chooseColor(color, gameName, clientAuthToken);
    }
    public static Results _startGame(GameName gameName, String clientAuthToken) {
        return ourInstance.startGame(gameName, clientAuthToken);
    }
    public static Results _getCommands(String authToken) {
        return ourInstance.getCommands(authToken);
    }

    public static Results _sendChatMessage(ChatMessage message, GameName gameName) {
        return ourInstance.sendChatMessage(message, gameName);
    }

    @Override
    public Results selectDestinationCards(ArrayList<DestinationCard> tickets, GameName name, String authToken) {
        if (tickets != null) {
            GameInfo game = ServerModel.getInstance().getGameInfo(name);
            String username = ServerModel.getInstance().getAuthTokens().get(authToken);
            Player player = game.getPlayer(username);
            for (DestinationCard card : tickets) {
                if (player.getDestinationCards().contains(card)) {
                    player.removeDestCardFromHand(card); // remove ticket from player hand
                    game.putDestCardInDeck(card); // put ticket back in dest card deck
                }
                else {
                    Results results = new Results();
                    results.setSuccess(false);
                    results.setErrorMessage("Destination card not in player's hand");
                }
            }
        }

        Results results = new Results();
        Command selectDestCardsCommand = new Command("model.CommandFacade", "_selectDestinationCards", Arrays.asList(new Object[] {name, tickets}));
        results.getClientCommands().add(selectDestCardsCommand);
        results.setSuccess(true);
        return results;
    }

    @Override
    public Results selectTrainCard(int index, GameName name, String authtoken) { return null; }

    @Override
    public Results drawTrainCard(GameName name, String authtoken) { return null; }

    @Override
    public Results drawDestinationCards(GameName name, String authtoken) { return null; }

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

        ClientProxy clientProxy = new ClientProxy();
        User user = new User(username, password);
        clientProxy.loginUser(username);

        ArrayList<GameInfo> gameList = ServerModel.getInstance().getGameList();

        //**************** BUILD COMMAND OBJECT  **********************
        Command loginClientCommand = new Command("model.CommandFacade","_loginUser", Arrays.asList(new Object[] {user, authToken, gameList}));
        //************************************************************

        results.getClientCommands().add(loginClientCommand);
        results.setSuccess(true);
        results.setAuthToken(authToken);

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

        ArrayList<GameInfo> gameList = ServerModel.getInstance().getGameList();

        Command registerUserCommand = new Command("model.CommandFacade", "_registerUser", Arrays.asList(new Object[] {user, authToken, gameList}));

        //set results
        results.getClientCommands().add(registerUserCommand);
        results.setSuccess(true);
        results.setAuthToken(authToken);

        ClientProxy clientProxy = new ClientProxy();
        clientProxy.registerUser(username);

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

        // createGame command sent to all other clients
        ClientProxy clientProxy = new ClientProxy();
        String username = ServerModel.getInstance().getAuthTokens().get(clientAuthToken);
        clientProxy.createGame(gameInfo, username);

        // createGame and joinGame commands created to be sent back to current client
        Command createGameCommand = new Command("model.CommandFacade", "_createGame", Arrays.asList(new Object[] {gameInfo}));
        Command joinGameCommand = new Command("model.CommandFacade", "_joinGame", Arrays.asList(new Object[] {gameInfo}));

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

        String username = ServerModel.getInstance().getAuthTokens().get(clientAuthToken);

        Player player = ServerModel.getInstance().getGameInfo(gameName).getPlayer(username);
        if (player == null || !gamePlayers.contains(player)){
            player = addUserToGame(clientAuthToken, game);
        }

        ClientProxy clientProxy = new ClientProxy();
        clientProxy.joinGame(player, gameName, username);

        Command joinGameCommand = new Command("model.CommandFacade", "_joinGame", Arrays.asList(new Object[] {player, gameName}));

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

        givePlayersInitialTrainCards(game);
        givePlayersInitialDestCards(game);

        ClientProxy clientProxy = new ClientProxy();
        String username = ServerModel.getInstance().getAuthTokens().get(clientAuthToken);
        clientProxy.startGame(gameName, username);

        Player clientPlayer = game.getPlayer(username);
        List<TrainCard> playerTrainCards = clientPlayer.getTrainCards();
        List<DestinationCard> playerDestCards = clientPlayer.getDestinationCards();
        List<TrainCard> faceUpCards = game.getFaceUpCards();
        Command startGameCommand = new Command("model.CommandFacade", "_startGame", Arrays.asList(new Object[] {gameName, playerTrainCards, playerDestCards, faceUpCards}));

        results.getClientCommands().add(startGameCommand);
        results.setSuccess(true);

        return results;
    }

    public void givePlayersInitialTrainCards(GameInfo game) {
        ArrayList<Player> gamePlayers = game.getPlayers();
        for (Player player : gamePlayers) {
            ArrayList<TrainCard> playerCards = game.getPlayerInitialTrainCards();
            player.setTrainCards(playerCards);
        }
    }

    public void givePlayersInitialDestCards(GameInfo game) {
        ArrayList<Player> gamePlayers = game.getPlayers();
        for (Player player : gamePlayers) {
            ArrayList<DestinationCard> playerCards = game.getPlayerInitialDestCards();
            player.setDestinationCards(playerCards);
        }
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
        clientProxy.claimColor(username, color);

        Command chooseColorCommand = new Command("model.CommandFacade", "_claimColor", Arrays.asList(new Object[] {username, color}));
        results.getClientCommands().add(chooseColorCommand);
        results.setSuccess(true);

        return results;
    }

    @Override
    public Results sendChatMessage(ChatMessage message, GameName gameName) {
        Results results = new Results();

        Map<GameName, List<ChatMessage>> chatMessages = ServerModel.getInstance().getChatMessages();
        if(chatMessages.get(gameName) == null) {
            chatMessages.put(gameName, new ArrayList<ChatMessage>());
        }
        chatMessages.get(gameName).add(message);
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.addChatMessage(gameName, message);

        Command addChatMessageCmd = new Command("model.CommandFacade", "_addChatMessage", Arrays.asList(new Object[] {message}));
        results.getClientCommands().add(addChatMessageCmd);
        results.setSuccess(true);

        return results;
    }

    public Results getCommands(String authToken) {
        Results results = new Results();
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);
        results.setClientCommands(CommandManager.getInstance().getCommands(username));
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