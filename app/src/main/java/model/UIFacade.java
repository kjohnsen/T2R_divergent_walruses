package model;

import java.util.ArrayList;
import java.util.List;

import clientserver.ServerProxy;
import interfaces.IServer;
import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import results.Results;

public class UIFacade {

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

    public ArrayList<TrainCard> getFaceupCards() { return ClientModel.getInstance().getFaceupCards(); }

    public IServer getServerProxy() {
        return serverProxy;
    }

    public void setServerProxy(IServer serverProxy) {
        this.serverProxy = serverProxy;
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

    public String claimColor(PlayerColor playerColor) {
        GameName gameName = ClientModel.getInstance().getCurrentGame().getGameName();
        return processResults(serverProxy.chooseColor(playerColor, gameName, authToken));
    }

    public String sendChatMessage(ChatMessage chatMessage) {
        GameName gameName = ClientModel.getInstance().getCurrentGame().getGameName();
        return processResults(serverProxy.sendChatMessage(chatMessage, gameName));
    }

    public String getUsername() { return ClientModel.getInstance().getCurrentUser().getUsername(); }

    public boolean currentGameReady() { return ClientModel.getInstance().currentGameReady(); }

    public ArrayList<GameInfo> getGameList() {
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
