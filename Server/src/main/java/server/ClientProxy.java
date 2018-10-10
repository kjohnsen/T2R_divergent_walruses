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

    public void loginUser(String authToken) {
        CommandManager.getInstance().addClient(authToken);
    }

    public void registerUser(String authToken) {

        CommandManager.getInstance().addClient(authToken);
    }

    public void joinGame(Player player, GameName gameName, String clientAuthToken) {
        for(String authToken : ServerModel.getInstance().getAuthTokens().keySet()) {
            if (!authToken.equals(clientAuthToken)) {
                Command clientCommand = new Command("model.CommandFacade", "joinGame", Arrays.asList(new Object[] {player, gameName}));
                CommandManager.getInstance().addCommand(authToken, clientCommand);
            }
        }
    }

    public void createGame(GameInfo gameInfo, String clientAuthToken) {
        //create commands for every client in the server model except the one that asked
        for(String authToken : ServerModel.getInstance().getAuthTokens().keySet()) {
            if (!authToken.equals(clientAuthToken)) {
                Command clientCommand = new Command("model.CommandFacade", "createGame", Arrays.asList(new Object[] {gameInfo}));
                CommandManager.getInstance().addCommand(authToken, clientCommand);
            }
        }
    }

    public void startGame(GameName gameName, String clientAuthToken) {
        for (String authToken : ServerModel.getInstance().getAuthTokens().keySet()) {
            if (!authToken.equals(clientAuthToken)) {
                Command clientCommand = new Command("model.CommandFacade", "startGame", Arrays.asList(new Object[] {gameName}));
                CommandManager.getInstance().addCommand(authToken, clientCommand);
            }
        }
    }

    public void claimColor(String username, PlayerColor playerColor, String clientAuthToken) {
        for (String authToken : ServerModel.getInstance().getAuthTokens().keySet()) {
            if (!authToken.equals(clientAuthToken)) {
                Command clientCommand = new Command("model.CommandFacade", "claimColor", Arrays.asList(new Object[] {username, playerColor}));
                CommandManager.getInstance().addCommand(authToken, clientCommand);
            }
        }
    }
}
