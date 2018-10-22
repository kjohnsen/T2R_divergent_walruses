package server;

import java.util.Arrays;
import java.util.List;

import data.Command;
import data.CommandManager;
import model.ServerModel;
import modelclasses.DestinationCard;
import modelclasses.User;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.GameName;
import modelclasses.GameInfo;
import modelclasses.TrainCard;

public class ClientProxy {

    public ClientProxy(){}

    public void loginUser(String username) {
        CommandManager.getInstance().addClient(username);
    }

    public void registerUser(String username) {

        CommandManager.getInstance().addClient(username);
    }

    public void joinGame(Player player, GameName gameName, String clientUsername) {
        for(String username : ServerModel.getInstance().getUsers().keySet()) {
            if (!username.equals(clientUsername)) {
                Command clientCommand = new Command("model.CommandFacade", "joinGame", Arrays.asList(new Object[] {player, gameName}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void createGame(GameInfo gameInfo, String clientUsername) {
        //create commands for every client in the server model except the one that asked
        for(String username : ServerModel.getInstance().getUsers().keySet()) {
            if (!username.equals(clientUsername)) {
                Command clientCommand = new Command("model.CommandFacade", "createGame", Arrays.asList(new Object[] {gameInfo}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void startGame(GameName gameName, String clientUsername) {
        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            // this checks that the user is a player in the game, and that it is not the client user
            if (game.getPlayer(username) != null && !username.equals(clientUsername)) {
                Player currPlayer = game.getPlayer(username);
                List<TrainCard> playerTrainCards = currPlayer.getTrainCards();
                List<DestinationCard> playerDestCards = currPlayer.getDestinationCards();
                Command clientCommand = new Command("model.CommandFacade", "startGame", Arrays.asList(new Object[] {gameName, playerTrainCards, playerDestCards}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }

    public void claimColor(String clientUsername, PlayerColor playerColor) {
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            if (!username.equals(clientUsername)) {
                Command clientCommand = new Command("model.CommandFacade", "claimColor", Arrays.asList(new Object[] {clientUsername, playerColor}));
                CommandManager.getInstance().addCommand(username, clientCommand);
            }
        }
    }
}
