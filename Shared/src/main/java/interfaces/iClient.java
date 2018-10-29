package interfaces;

import java.util.ArrayList;
import java.util.List;

import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.GameName;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import modelclasses.User;

public interface iClient {
    void replaceTrainCard(TrainCard replacement, int index, Player player);
    void drawTrainCard(TrainCard card, Player player);
    void displayDestinationCards(List<DestinationCard> tickets);
    void loginUser(User user, String authToken, List<GameInfo> gameInfos);
    void registerUser(User user, String authToken, List<GameInfo> gameInfos);
    void joinGame(Player player, GameName gameName);
    void createGame(GameInfo gameInfo);
    void startGame(GameInfo gameInfo);
    void claimColor(String username, PlayerColor playerColor);
    void addChatMessage(ChatMessage message);
}
