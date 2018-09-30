package model;

import java.util.ArrayList;

import clientserver.ServerProxy;
import interfaces.iClient;
import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.User;

public class CommandFacade implements iClient {

    private static final CommandFacade ourInstance = new CommandFacade();

    public static CommandFacade getInstance() {
        return ourInstance;
    }

    private CommandFacade() {
    }


    @Override
    public void loginUser(User user, String authToken) {
        ClientModel.getInstance().setCurrentUser(user);
        ServerProxy.getInstance().setAuthToken(authToken);
    }

    @Override
    public void registerUser(User user, String authToken) {
        ClientModel.getInstance().setCurrentUser(user);
        ServerProxy.getInstance().setAuthToken(authToken);
    }

    @Override
    public void joinGame(Player player, GameID gameID) {
        ClientModel.getInstance().getGame(gameID).addPlayer(player);
    }

    @Override
    public void createGame(GameInfo gameInfo) {
        ArrayList<GameInfo> gameList = ClientModel.getInstance().getGameList();
        gameList.add(gameInfo);
        // Using setGameList, we allow ClientModel to do the setting (thus notifying observers)
        // without the proliferation of setters and adders.
        ClientModel.getInstance().setGameList(gameList);
    }

    @Override
    public void startGame(GameID gameID) {
        GameInfo gameInfo = ClientModel.getInstance().getGame(gameID);
        ClientModel.getInstance().setCurrentGame(gameInfo);
    }

    @Override
    public void claimColor(String username, PlayerColor playerColor) {
        ClientModel.getInstance().getCurrentGame().getPlayer(username).setPlayerColor(playerColor);
    }
}
