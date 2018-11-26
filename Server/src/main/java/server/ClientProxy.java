package server;

import java.util.ArrayList;
import java.util.Arrays;

import data.Command;
import model.ServerModel;
import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.GameName;
import modelclasses.GameInfo;
import modelclasses.Route;
import modelclasses.TrainCard;

public class ClientProxy {

    public ClientProxy(){}

    public void loginUser(String username) {
        CommandManager.getInstance().addClient(username);
    }

    public void registerUser(String username) {

        CommandManager.getInstance().addClient(username);
    }

    public void selectDestinationCards(GameName gameName, ArrayList<DestinationCard> rejections, Player player, GameInfo game) {
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            // this checks that the user is a player in the game, and that it is not the client user
            if (game.getPlayer(username) != null && !username.equals(player.getUsername())) {
                Command clientCommand = new Command("model.CommandFacade", "_selectDestinationCards", Arrays.asList(new Object[] {gameName, rejections, player}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void selectTrainCard(TrainCard card, Player player, GameInfo game) {
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            // this checks that the user is a player in the game, and that it is not the client user
            if (game.getPlayer(username) != null && !username.equals(player.getUsername())) {
                Command clientCommand = new Command("model.CommandFacade", "_selectTrainCard", Arrays.asList(new Object[] {card, player}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void replaceTrainCard(TrainCard replacement, Integer selected, GameInfo game, String name) {
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            // this checks that the user is a player in the game, and that it is not the client user
            if (game.getPlayer(username) != null && !username.equals(name)) {
                Command clientCommand = new Command("model.CommandFacade", "_replaceTrainCard", Arrays.asList(new Object[] {replacement, selected}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void replaceTrainDeck(ArrayList<TrainCard> newDeck, GameInfo game, String name) {
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            // this checks that the user is a player in the game, and that it is not the client user
            if (game.getPlayer(username) != null && !username.equals(name)) {
                Command clientCommand = new Command("model.CommandFacade", "_replaceTrainDeck", Arrays.asList(new Object[] {newDeck}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void clearWilds(ArrayList<TrainCard> replacements, GameInfo game, String name) {
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            // this checks that the user is a player in the game, and that it is not the client user
            if (game.getPlayer(username) != null && !username.equals(name)) {
                Command clientCommand = new Command("model.CommandFacade", "_clearWilds", Arrays.asList(new Object[] {replacements}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void drawTrainCard(TrainCard card, Player player, GameInfo game) {
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            // this checks that the user is a player in the game, and that it is not the client user
            if (game.getPlayer(username) != null && !username.equals(player.getUsername())) {
                Command clientCommand = new Command("model.CommandFacade", "_drawTrainCard", Arrays.asList(new Object[] {card, player}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void displayDestinationCards(ArrayList<DestinationCard> tickets, Player player, GameInfo game) {
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            // this checks that the user is a player in the game, and that it is not the client user
            if (game.getPlayer(username) != null && !username.equals(player.getUsername())) {
                Command clientCommand = new Command("model.CommandFacade", "_displayDestinationCards", Arrays.asList(new Object[] {tickets, player}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void joinGame(Player player, GameName gameName, String clientUsername) {
        for(String username : ServerModel.getInstance().getUsers().keySet()) {
            if (!username.equals(clientUsername)) {
                Command clientCommand = new Command("model.CommandFacade", "_joinGame", Arrays.asList(new Object[] {player, gameName}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void createGame(GameInfo gameInfo, String clientUsername) {
        //create commands for every client in the server model except the one that asked
        for(String username : ServerModel.getInstance().getUsers().keySet()) {
            if (!username.equals(clientUsername)) {
                Command clientCommand = new Command("model.CommandFacade", "_createGame", Arrays.asList(new Object[] {gameInfo}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void startGame(GameName gameName, String clientUsername) {
        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            // this checks that the user is a player in the game, and that it is not the client user
            if (game.getPlayer(username) != null && !username.equals(clientUsername)) {
                Command clientCommand = new Command("model.CommandFacade", "_startGame", Arrays.asList(new Object[] {game}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void claimColor(String clientUsername, PlayerColor playerColor) {
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            if (!username.equals(clientUsername)) {
                Command clientCommand = new Command("model.CommandFacade", "_claimColor", Arrays.asList(new Object[] {clientUsername, playerColor}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void addChatMessage(GameName gameName, ChatMessage message) {
        for(Player player: ServerModel.getInstance().getGameInfo(gameName).getPlayers()) {
            if(!player.getUsername().equals(message.getUsername())) {
                Command clientCommand = new Command("model.CommandFacade", "_addChatMessage", Arrays.asList(new Object[] {message}));
                CommandManager.getInstance().addCommand(player.getUsername(), clientCommand);
            }
        }
    }

    public void addGameHistory(GameName gameName, ChatMessage message) {
        Command clientCommand = new Command("model.CommandFacade", "_addGameHistory", Arrays.asList(new Object[] {message}));
        for(Player player: ServerModel.getInstance().getGameInfo(gameName).getPlayers()) {
            CommandManager.getInstance().addCommand(player.getUsername(), clientCommand);
        }
    }

    public void claimRoute(GameName gameName, Route route, String clientUsername, ArrayList<TrainCard> playerTrainCards, int playerTrainNum) {
        for (Player player : ServerModel.getInstance().getGameInfo(gameName).getPlayers()) {
            if (!player.getUsername().equals(clientUsername)) {
                Command clientCommand = new Command("model.CommandFacade", "_claimRoute", Arrays.asList(new Object[] {gameName, route, clientUsername, playerTrainCards, playerTrainNum}));
                CommandManager.getInstance().addCommand(player.getUsername(), clientCommand);
            }
        }
    }

    public void startTurn(GameName gameName, String clientUsername, String nextPlayerUsername) {
        for (Player player : ServerModel.getInstance().getGameInfo(gameName).getPlayers()) {
            if (!player.getUsername().equals(clientUsername)) {
                Command startTurnCommand = new Command("model.CommandFacade", "_startNextTurn", Arrays.asList(new Object[] {nextPlayerUsername}));
                CommandManager.getInstance().addCommand(player.getUsername(), startTurnCommand);
            }
        }
    }

    public void startLastRound(GameName gameName, String clientUsername) {
        for (Player player : ServerModel.getInstance().getGameInfo(gameName).getPlayers()) {
            if (!player.getUsername().equals(clientUsername)) {
                Command clientCommand = new Command("model.CommandFacade", "_startLastRound", Arrays.asList(new Object[] {}));
                CommandManager.getInstance().addCommand(player.getUsername(), clientCommand);
            }
        }
    }

    public void endGame(GameName gameName, String clientUsername) {
        for (Player player : ServerModel.getInstance().getGameInfo(gameName).getPlayers()) {
            if (!player.getUsername().equals(clientUsername)) {
                Command endGameCommand = new Command("model.CommandFacade", "_endGame", Arrays.asList(new Object[] {}));
                CommandManager.getInstance().addCommand(player.getUsername(), endGameCommand);
            }
        }
    }
}
