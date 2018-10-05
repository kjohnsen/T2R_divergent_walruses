package server;

import java.util.Arrays;

import data.Command;
import data.CommandManager;
import modelclasses.User;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.GameName;
import modelclasses.GameInfo;

public class ClientProxy {

    public void loginUser(User user, String authToken) {
        CommandManager.getInstance().addClient(authToken);
    }

    public void registerUser(User user, String authToken) {
        CommandManager.getInstance().addClient(authToken);
    }

    public void joinGame(Player player, GameName gameName) {

    }

    public void createGame(GameInfo gameInfo, String authToken) {
        Command clientCommand = new Command("CommandFacade", "createGame", Arrays.asList(new Object[] {gameInfo}));
        CommandManager.getInstance().addCommand(authToken, clientCommand);
    }

    public void startGame(GameName gameName) {

    }

    public void claimColor(String username, PlayerColor playerColor) {

    }
}
