package model;

import java.util.ArrayList;
import java.util.List;

import data.Serializer;
import interfaces.iClient;
import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.GameName;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import modelclasses.User;
import modelclasses.Route;

public class CommandFacade implements iClient {

    private static CommandFacade ourInstance = new CommandFacade();

    public static CommandFacade getInstance() {
        //the check is necessary because Travis fails otherwise
        if (ourInstance == null) {
            ourInstance = new CommandFacade();
        }
        return ourInstance;
    }

    private CommandFacade() {
    }

    public static void _selectDestinationCards(GameName gameName, ArrayList<DestinationCard> rejections, Player player) {
        ourInstance.selectDestinationCards(rejections, player);
    }
    public static void _selectTrainCard(TrainCard card, Player player) {
        ourInstance.selectTrainCard(card, player);
    }
    public static void _clearWilds(ArrayList<TrainCard> replacements) {
        ourInstance.clearWilds(replacements);
    }
    public static void _replaceTrainCard(TrainCard replacement, Integer selected) {
        ourInstance.replaceTrainCard(replacement, selected);
    }
    public static void _drawTrainCard(TrainCard card, Player player) {
        ourInstance.drawTrainCard(card, player);
    }
    public static void _displayDestinationCards(ArrayList<DestinationCard> tickets, Player player) {
        ourInstance.displayDestinationCards(tickets, player);
    }
    public static void _registerUser(User user, String authToken, ArrayList<GameInfo> gameInfos) {
        ourInstance.registerUser(user, authToken, gameInfos);
    }
    public static void _loginUser(User user, String authToken, ArrayList<GameInfo> gameInfos) {
        ourInstance.loginUser(user, authToken, gameInfos);
    }
    public static void _createGame(GameInfo gameInfo) {
        ourInstance.createGame(gameInfo);
    }
    public static void _joinGame(Player player, GameName gameName) {
        ourInstance.joinGame(player, gameName);
    }
    public static void _joinGame(GameInfo gameInfo) {
        ourInstance.joinGame(null, gameInfo.getGameName());
    }
    public static void _claimColor(String username, PlayerColor playerColor) {
        ourInstance.claimColor(username, playerColor);
    }
    public static void _startGame(GameInfo game) {
        ourInstance.startGame(game);
    }

    public static void _addGameHistory(ChatMessage message) { ourInstance.addChatMessage(message); }

    public static void _addChatMessage(ChatMessage message) { ourInstance.addChatMessage(message); }

    public static void _claimRoute(GameName gameName, Route route, String username) {
        ourInstance.claimRoute(gameName, route, username);
    }

    public static void _startTurn() {
        ourInstance.startTurn();
    }

    public static void _startLastRound() {
        ourInstance.startLastRound();
    }

    public static void _endGame() {
        ourInstance.endGame();
    }

    @Override
    public void selectDestinationCards(ArrayList<DestinationCard> rejections, Player player) {
        ClientModel.getInstance().rejectTickets(rejections, player);
    }

    @Override
    public void selectTrainCard(TrainCard card, Player player) {
        ClientModel.getInstance().selectTrainCardToHand(card, player);
    }

    @Override
    public void replaceTrainCard(TrainCard replacement, Integer selected) {
        ClientModel.getInstance().replaceFaceupCard(replacement, selected);
    }

    @Override
    public void clearWilds(ArrayList<TrainCard> replacements) {
        ClientModel.getInstance().setFaceupCards(replacements);
    }

    @Override
    public void drawTrainCard(TrainCard card, Player player) {
        ClientModel.getInstance().drawTrainCardToHand(card, player);
    }

    @Override
    public void displayDestinationCards(ArrayList<DestinationCard> tickets, Player player) {
        ClientModel.getInstance().setPlayerPreSelectionTickets(tickets);
    }

    @Override
    public void loginUser(User user, String authToken, ArrayList<GameInfo> gameInfos) {
        ClientModel.getInstance().setGameList(gameInfos);
        ClientModel.getInstance().setCurrentUser(user);
        UIFacade.getInstance().setAuthToken(authToken);
        ClientModel.getInstance().notifyObservers(user);
    }

    @Override
    public void registerUser(User user, String authToken, ArrayList<GameInfo> gameInfos) {
        ClientModel.getInstance().setCurrentUser(user);
        UIFacade.getInstance().setAuthToken(authToken);
        ClientModel.getInstance().setGameList(gameInfos);
    }

    @Override
    public void joinGame(Player player, GameName gameName) {
        GameInfo gameInfo = ClientModel.getInstance().getGame(gameName);
        if(player != null && !gameInfo.getPlayers().contains(player)) {
            gameInfo.addPlayer(player);
            ClientModel.getInstance().notifyObservers(gameInfo.getPlayers());
        }
        if (player == null || player.getUsername().equals(ClientModel.getInstance().getCurrentUser().getUsername())) {
            ClientModel.getInstance().setCurrentGame(gameInfo);
        }
    }

    @Override
    public void createGame(GameInfo gameInfo) {
        ArrayList<GameInfo> gameList = ClientModel.getInstance().getGameList();
        gameList.add(gameInfo);
        ClientModel.getInstance().setGameList(gameList);
    }

    @Override
    public void startGame(GameInfo game) {
        ClientModel.getInstance().setFaceupCards(game.getFaceUpCards());
        ClientModel.getInstance().setCurrentGamePlayers(game.getPlayers());

        String username = ClientModel.getInstance().getCurrentUser().getUsername();
        Player player = game.getPlayer(username);
        ClientModel.getInstance().setPlayerPreSelectionTickets(player.getPreSelectionDestCards());
        ClientModel.getInstance().setPlayerTickets(player.getDestinationCards());
        ClientModel.getInstance().setPlayerTrainCards(player.getTrainCards());
    }

    @Override
    public void claimColor(String username, PlayerColor playerColor) {
        Player player = ClientModel.getInstance().getCurrentGame().getPlayer(username);
        player.setPlayerColor(playerColor);
        ClientModel.getInstance().notifyObservers(ClientModel.getInstance().getCurrentGame().getPlayers());
    }

    @Override
    public void addChatMessage(ChatMessage message) {
        ClientModel.getInstance().getChatMessages().add(message);
        ClientModel.getInstance().notifyObservers(ClientModel.getInstance().getChatMessages());
    }

    @Override
    public void claimRoute(GameName gameName, Route route, String username) {
        ArrayList<Route> routes = ClientModel.getInstance().getCurrentGame().getUnclaimedRoutes();
        Player player = ClientModel.getInstance().getCurrentGame().getPlayer(username);
        for (Route r : routes) {
            if (r.equals(route)) {
                r.setPlayer(player);
                ClientModel.getInstance().notifyObservers(r);
                break;
            }
        }
    }

    @Override
    public void startTurn() {
        // TODO: implement this
    }

    @Override
    public void startLastRound() {
        // TODO: implement this
    }

    @Override
    public void endGame() {
        // TODO: implement this
    }
}
