package model;

import java.util.ArrayList;

import clientserver.ServerProxy;
import interfaces.iClient;
import modelclasses.GameName;
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
    public void loginUser(User user, String authToken, ArrayList<GameInfo> gameInfos) {
        ClientModel.getInstance().setGameList(gameInfos);
        ClientModel.getInstance().setCurrentUser(user);
        UIFacade.getInstance().setAuthToken(authToken);
        ClientModel.getInstance().notifyObservers(user);
    }

    @Override
    public void registerUser(User user, String authToken) {
        ClientModel.getInstance().setCurrentUser(user);
        UIFacade.getInstance().setAuthToken(authToken);
        ClientModel.getInstance().notifyObservers(user);
    }

    @Override
    public void joinGame(Player player, GameName gameName) {
        GameInfo gameInfo = ClientModel.getInstance().getGame(gameName);
        if(!gameInfo.getPlayers().contains(player))
            gameInfo.addPlayer(player);

        if (player.getUsername().equals(ClientModel.getInstance().getCurrentUser().getUsername())) {
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
    public void startGame(GameName gameName) {
        GameInfo gameInfo = ClientModel.getInstance().getGame(gameName);
        ClientModel.getInstance().setCurrentGame(gameInfo);
    }

    @Override
    public void claimColor(String username, PlayerColor playerColor) {
        Player player = ClientModel.getInstance().getCurrentGame().getPlayer(username);
        player.setPlayerColor(playerColor);
        ClientModel.getInstance().notifyObservers(player);
    }
}
