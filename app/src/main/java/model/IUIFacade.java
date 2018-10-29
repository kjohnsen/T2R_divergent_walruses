package model;

import java.util.ArrayList;
import java.util.List;

import interfaces.IServer;
import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import results.Results;

public interface IUIFacade {

    public String getAuthToken();
    public void setAuthToken(String authToken);
    public GameInfo getCurrentGame();
    public List<TrainCard> getFaceupCards();
    public IServer getServerProxy();
    public void setServerProxy(IServer serverProxy);
    public List<DestinationCard> getStartDestinationCards();
    public boolean isGameStart();
    public void setNotGameStart();
    public String selectTrainCard(int index);
    public String drawTrainCard();
    public String drawDestinationCards();
    public String selectDestinationCards(ArrayList<DestinationCard> rejected);
    public String loginUser(String username, String password);
    public String joinGame(String gameName);
    public String createGame(String gameName, int numPlayers);
    public String startGame();
    public String claimColor(PlayerColor playerColor);
    public String sendChatMessage(ChatMessage chatMessage);
    public String getUsername();
    public boolean currentGameReady();
    public List<GameInfo> getGameList();
    public List<ChatMessage> getChatMessages();
    public void setHostIP(String hostIP);
    public void setHostPort(String hostPort);

}
