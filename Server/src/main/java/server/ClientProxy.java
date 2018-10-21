package server;

import java.util.Arrays;

import data.Command;
import data.CommandManager;
import model.ServerModel;
import modelclasses.User;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.GameName;
import modelclasses.GameInfo;

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
        for (String username : ServerModel.getInstance().getUsers().keySet()) {
            if (!username.equals(clientUsername)) {
                Command clientCommand = new Command("model.CommandFacade", "startGame", Arrays.asList(new Object[] {gameName}));
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
