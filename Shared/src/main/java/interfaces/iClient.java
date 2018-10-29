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
    void selectDestinationCards(ArrayList<DestinationCard> rejections);
    void selectTrainCard(TrainCard card);
    void clearWilds(ArrayList<TrainCard> replacements);
    void replaceTrainCard(TrainCard replacement, Integer index, Player player);
    void drawTrainCard(TrainCard card, Player player);
    void displayDestinationCards(ArrayList<DestinationCard> tickets);
    void loginUser(User user, String authToken, ArrayList<GameInfo> gameInfos);
    void registerUser(User user, String authToken, ArrayList<GameInfo> gameInfos);
    void joinGame(Player player, GameName gameName);
    void createGame(GameInfo gameInfo);
    void startGame(GameName gameName, ArrayList<TrainCard> trainCards, ArrayList<DestinationCard> destCards, ArrayList<TrainCard> faceUpCards);
    void claimColor(String username, PlayerColor playerColor);
    void addChatMessage(ChatMessage message);
}
