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
import modelclasses.Route;

public interface iClient {
    void selectDestinationCards(ArrayList<DestinationCard> rejections, Player player);
    void selectTrainCard(TrainCard card, Player player);
    void clearWilds(ArrayList<TrainCard> replacements);
    void replaceTrainCard(TrainCard replacement, Integer index);
    void drawTrainCard(TrainCard card, Player player);
    void displayDestinationCards(ArrayList<DestinationCard> tickets, Player player);
    void loginUser(User user, String authToken, ArrayList<GameInfo> gameInfos);
    void registerUser(User user, String authToken, ArrayList<GameInfo> gameInfos);
    void joinGame(Player player, GameName gameName);
    void createGame(GameInfo gameInfo);
    void startGame(GameInfo gameInfo);
    void claimColor(String username, PlayerColor playerColor);
    void addChatMessage(ChatMessage message);
    void claimRoute(GameName gameName, Route route, String username, ArrayList<TrainCard> updatedHand, int playerTrainNum);
    void startNextTurn(String username);
    void startLastRound();
    void endGame();
}
