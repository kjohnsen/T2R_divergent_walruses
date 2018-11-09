package model;

import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import clientserver.ServerProxy;
import interfaces.IServer;
import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import results.Results;

public class UIFacade implements IUIFacade {

    private static final UIFacade ourInstance = new UIFacade();
    private IServer serverProxy = ServerProxy.getInstance();
    private String authToken = null;

    public static UIFacade getInstance() {
        return ourInstance;
    }

    private UIFacade() {}

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public GameInfo getCurrentGame() {
        return ClientModel.getInstance().getCurrentGame();
    }

    public List<TrainCard> getFaceupCards() { return ClientModel.getInstance().getFaceupCards(); }

    public IServer getServerProxy() {
        return serverProxy;
    }

    public void setServerProxy(IServer serverProxy) {
        this.serverProxy = serverProxy;
    }

    public void setGameStart(boolean start) { ClientModel.getInstance().setGameStart(start); }

    public List<DestinationCard> getStartDestinationCards() {
        return ClientModel.getInstance().getPlayerPreSelectionTickets();
    }

    //This returns the error message if there is one, or null if there isn't
    private String processResults(Results results) {
        if(results != null && results.getSuccess()) {
            for(int i = 0; i < results.getClientCommands().size(); ++i) {
                results.getClientCommands().get(i).execute();
            }
        } else {
            if (results == null) {
                return "Cannot reach server";
            } else {
                return results.getErrorMessage();
            }
        }
        return null;
    }

    public boolean isGameStart() { return ClientModel.getInstance().isGameStart(); }

    public void setNotGameStart() {
        ClientModel.getInstance().setNotGameStart();
    }

    public String selectTrainCard(int index) {
        return processResults(serverProxy.selectTrainCard(index, getCurrentGame().getGameName(), authToken));
    }

    public String drawTrainCard() {
        return processResults(serverProxy.drawTrainCard(getCurrentGame().getGameName(), authToken));
    }

    public String drawDestinationCards() {
        return processResults(serverProxy.drawDestinationCards(getCurrentGame().getGameName(), authToken));
    }

    public String selectDestinationCards(ArrayList<DestinationCard> rejected) {
        ClientModel.getInstance().setGameStart(false);
        return processResults(serverProxy.selectDestinationCards(rejected, getCurrentGame().getGameName(), authToken));
    }

    public String loginUser(String username, String password) {
        return processResults(serverProxy.loginUser(username, password));
    }

    //returns null if no error, or an error message if there is one
    public String registerUser(String username, String password) {
        return processResults(serverProxy.registerUser(username, password));
    }

    public String joinGame(String gameName) {
        return processResults(serverProxy.joinGame(new GameName(gameName), authToken));
    }

    public String createGame(String gameName, int numPlayers) {
        return processResults(serverProxy.createGame(gameName, numPlayers, authToken));
    }

    public String startGame() {
        GameInfo gameInfo = ClientModel.getInstance().getCurrentGame();
        return processResults(serverProxy.startGame(gameInfo.getGameName(), authToken));
    }

    public PlayerColor getCurrentColor() {
        return ClientModel.getInstance().getCurrentColor();
    }

    public String claimColor(PlayerColor playerColor) {
        GameName gameName = ClientModel.getInstance().getCurrentGame().getGameName();
        PlayerColor currentColor = getCurrentColor();
        if (!currentColor.equals(playerColor)) {
            return processResults(serverProxy.chooseColor(playerColor, gameName, authToken));
        } else {
            return null;
        }
    }

    public String sendChatMessage(ChatMessage chatMessage) {
        GameName gameName = null;
        if(ClientModel.getInstance().getCurrentGame() != null) {
            gameName = ClientModel.getInstance().getCurrentGame().getGameName();
        }
        Player player = ClientModel.getInstance().getCurrentGame().getPlayer(chatMessage.getUsername());
        if(player != null) {
            chatMessage.setPlayerColor(player.getPlayerColor());
        }
        return processResults(serverProxy.sendChatMessage(chatMessage, gameName));
    }

    public String getUsername() {
        if(ClientModel.getInstance().getCurrentUser() != null) {
            return ClientModel.getInstance().getCurrentUser().getUsername();
        } else {
            //For tests...
            return "username";
        }
    }

    public boolean currentGameReady() { return ClientModel.getInstance().currentGameReady(); }

    public List<GameInfo> getGameList() {
        return ClientModel.getInstance().getGameList();
    }

    public List<ChatMessage> getChatMessages() {
        return ClientModel.getInstance().getChatMessages();
    }

    public void setHostIP(String hostIP) {
        serverProxy.setHostIP(hostIP);
    }

    public void setHostPort(String hostPort) {
        serverProxy.setHostPort(hostPort);
    }


}
