package server;

import java.util.ArrayList;
import java.util.List;

import interfaces.IServer;
import model.ServerModel;
import modelclasses.ServerState;
import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.TrainCardColor;
import results.Results;
import data.Command;
import modelclasses.GameName;
import modelclasses.PlayerColor;
import modelclasses.User;
import modelclasses.TrainCard;
import modelclasses.Route;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class ServerFacade implements IServer {

    private static final ServerFacade ourInstance = new ServerFacade();

    private ServerFacade() {}

    public static ServerFacade getInstance() {
        return ourInstance;
    }

    public static void startTransaction(){
        ServerModel.getInstance().getiPersistencePluginFactory().startTransaction();
    }

    public static void endTransaction(){
        ServerModel.getInstance().getiPersistencePluginFactory().endTransaction();
    }

    public void addToDB(Command command, GameName gameName){
        startTransaction();
        ServerModel.getInstance().getDaoProxy().createCommand(command, gameName);
        endTransaction();
    }


    public static Results _selectDestinationCards(ArrayList<DestinationCard> tickets, GameName name, String authToken) {
        return ourInstance.selectDestinationCards(tickets, name, authToken);
    }

    public static Results _selectTrainCard(Integer index, GameName name, String authToken) {
        return ourInstance.selectTrainCard(index, name, authToken);
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

    public static Results _claimRoute(GameName gameName, Route route, String username, TrainCardColor chosenColor) {
        return ourInstance.claimRoute(gameName, route, username, chosenColor);
    }

    public Results selectDestinationCards(ArrayList<DestinationCard> tickets, GameName name, String authToken) {
        return GamePlay.selectDestinationCards(tickets, name, authToken);
    }

    @Override
    public Results selectTrainCard(Integer index, GameName name, String authToken) {
        return GamePlay.selectTrainCard(index, name, authToken);
    }

    @Override
    public Results drawTrainCard(GameName name, String authToken) {
        return GamePlay.drawTrainCard(name, authToken);
    }

    @Override
    public Results drawDestinationCards(GameName name, String authToken) {
        return GamePlay.drawDestinationCard(name, authToken);
    }

    public Results loginUser(String username, String password) {

        //createCommand the logged in results because you have to return something if it fails.
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
        if(serverModel.userIsAlreadyInGame(username)) {
            results.setErrorMessage("User is already in game");
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

        //createCommand the logged in results because you have to return something if it fails.
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
        newUser.setAuthToken(authToken);

        ArrayList<GameInfo> gameList = ServerModel.getInstance().getGameList();

        Command registerUserCommand = new Command("model.CommandFacade", "_registerUser", Arrays.asList(new Object[] {newUser, authToken, gameList}));

        startTransaction();
        ServerModel.getInstance().getDaoProxy().createUser(newUser);
        endTransaction();

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

        //createCommand game info and add to server model.
        ArrayList<Player> players = new ArrayList<>();
        GameInfo gameInfo = new GameInfo(gameName, players, numPlayers);
        ServerModel.getInstance().getGames().put(gameName, gameInfo);
        ServerModel.getInstance().getGameInfo(gameName).setServerState(ServerState.TURNSTART);

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

        //don't need to update gameinfo because we only initialize it and then update it
        //whenever we hit the deltamax

        results.getClientCommands().add(joinGameCommand);
        results.setSuccess(true);

        return results;
    }

    private Player addUserToGame(String clientAuthToken, GameInfo game) {
        String username = ServerModel.getInstance().getAuthTokens().get(clientAuthToken); // look up username using authToken
        Player player = new Player(username); // createCommand new player
        game.addPlayer(player); // add player to game
        User user = ServerModel.getInstance().getUser(username);
        user.addGame(game.getGameName());
        return player;
    }

    private void assignPlayersColors(GameInfo game) {
        List<PlayerColor> takenColors = new ArrayList<>();
        for (Player p : game.getPlayers()) {
            if (!p.getPlayerColor().equals(PlayerColor.UNCHOSEN)) {
                takenColors.add(p.getPlayerColor());
            }
        }
        List<PlayerColor> allColors = PlayerColor.getRandomColors();
        int index = 0;
        for (Player p : game.getPlayers()) {
            while (p.getPlayerColor().equals(PlayerColor.UNCHOSEN)) {
                if (!takenColors.contains(allColors.get(index))) {
                    p.setPlayerColor(allColors.get(index));
                }
                index++;
            }
        }
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
        assignPlayersColors(game);
        givePlayersInitialTrainCards(game);
        givePlayersInitialDestCards(game);
        game.setCurrentPlayer(gamePlayers.get(0));

        ClientProxy clientProxy = new ClientProxy();
        String username = ServerModel.getInstance().getAuthTokens().get(clientAuthToken);
        clientProxy.startGame(gameName, username);
        clientProxy.startTurn(gameName, username, game.getCurrentPlayer().getUsername());

        Command startGameCommand = new Command("model.CommandFacade", "_startGame", Arrays.asList(new Object[] {game}));
        Command startFirstTurnCommand = new Command("model.CommandFacade", "_startNextTurn", Arrays.asList(new Object[] {game.getCurrentPlayer().getUsername()}));

        //add the game to the database when it is started
        //add new game to database
        startTransaction();
        ServerModel.getInstance().getDaoProxy().createGameInfo(ServerModel.getInstance().getGameInfo(gameName));
        endTransaction();

        //initialize delta
        ServerModel.getInstance().initializeDelta(gameName);

        results.getClientCommands().add(startGameCommand);
        results.getClientCommands().add(startFirstTurnCommand);
        results.setSuccess(true);

        return results;
    }

    private void givePlayersInitialTrainCards(GameInfo game) {
        ArrayList<Player> gamePlayers = game.getPlayers();
        for (Player player : gamePlayers) {
            ArrayList<TrainCard> playerCards = game.getPlayerInitialTrainCards();
            player.setTrainCards(playerCards);
        }
    }

    private void givePlayersInitialDestCards(GameInfo game) {
        ArrayList<Player> gamePlayers = game.getPlayers();
        for (Player player : gamePlayers) {
            ArrayList<DestinationCard> playerCards = game.getPlayerInitialDestCards();
            player.setPreSelectionDestCards(playerCards);
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

    public Results claimRoute(GameName gameName, Route route, String authToken, TrainCardColor chosenColor) {
        return GamePlay.claimRoute(gameName, route, authToken, chosenColor);
    }

    public Results getCommands(String authToken) {
        Results results = new Results();
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);
        results.setClientCommands(CommandManager.getInstance().getCommands(username));
        results.setSuccess(true);
        results.setAuthToken(authToken);
        return results;
    }

    public void makeGameHistory(Command command, GameName gameName) {
        String commandMessage = CommandTranslator.translateCommand(command);
        if(!commandMessage.isEmpty()) {
            ChatMessage message = new ChatMessage("History", commandMessage);
            if(gameName != null) {
                addGameHistory(gameName, message);
            }
        }
    }

    public void makeGameHistory(Command command) {
        String commandMessage = CommandTranslator.translateCommand(command);
        if(!commandMessage.isEmpty()) {
            ChatMessage message = new ChatMessage("History", commandMessage);
            GameName gameName = null;
            switch(command.get_methodName()) {
                case CommandMethodNames.claimRoute: gameName = (GameName)command.get_paramValues()[0];  break;
                case CommandMethodNames.clearWilds: return;
                case CommandMethodNames.drawDestinationCards: gameName = (GameName)command.get_paramValues()[0];    break;
                case CommandMethodNames.selectDestinationCards: gameName = (GameName)command.get_paramValues()[1];  break;
                case CommandMethodNames.drawTrainCard: gameName = (GameName)command.get_paramValues()[0];   break;
                case CommandMethodNames.selectTrainCard: gameName = (GameName)command.get_paramValues()[1]; break;
                case CommandMethodNames.replaceTrainCard: return;
            }

            if(gameName != null) {
                addGameHistory(gameName, message);
            }
        }
    }

    public void addGameHistory(GameName gameName, ChatMessage message) {
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.addGameHistory(gameName, message);
    }

    //these two methods are necessary for the client side, but not the server side
    @Override
    public void setHostIP(String hostIP) { }
    @Override
    public void setHostPort(String hostPort) { }
}