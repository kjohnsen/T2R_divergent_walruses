package presenter;

import java.util.ArrayList;
import java.util.List;

import interfaces.IServer;
import model.IUIFacade;
import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;

public class MockUIFacade implements IUIFacade {
    @Override
    public String getAuthToken() {
        return null;
    }

    @Override
    public void setAuthToken(String authToken) {

    }

    @Override
    public GameInfo getCurrentGame() {
        return null;
    }

    @Override
    public List<TrainCard> getFaceupCards() {
        return null;
    }

    @Override
    public IServer getServerProxy() {
        return null;
    }

    @Override
    public void setServerProxy(IServer serverProxy) {

    }

    @Override
    public List<DestinationCard> getStartDestinationCards() {
        return null;
    }

    @Override
    public boolean isGameStart() {
        return false;
    }

    @Override
    public void setNotGameStart() {

    }

    @Override
    public String selectTrainCard(int index) {
        return null;
    }

    @Override
    public String drawTrainCard() {
        return null;
    }

    @Override
    public String drawDestinationCards() {
        return null;
    }

    @Override
    public String selectDestinationCards(ArrayList<DestinationCard> rejected) {
        return null;
    }

    @Override
    public String loginUser(String username, String password) {
        return null;
    }

    @Override
    public String joinGame(String gameName) {
        return null;
    }

    @Override
    public String createGame(String gameName, int numPlayers) {
        return null;
    }

    @Override
    public String startGame() {
        return null;
    }

    @Override
    public String claimColor(PlayerColor playerColor) {
        return null;
    }

    @Override
    public String sendChatMessage(ChatMessage chatMessage) {
        if(chatMessage.getMessage().equals("Test Message")) {
            return null;
        } else if(chatMessage.getMessage().equals("Error")) {
            return "Error Message";
        } else {
            return "Cannot reach server";
        }
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean currentGameReady() {
        return false;
    }

    @Override
    public List<GameInfo> getGameList() {
        return null;
    }

    @Override
    public List<ChatMessage> getChatMessages() {
        return null;
    }

    @Override
    public void setHostIP(String hostIP) {

    }

    @Override
    public void setHostPort(String hostPort) {

    }
}
