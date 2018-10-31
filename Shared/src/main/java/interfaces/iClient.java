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
    void selectDestinationCards(ArrayList<DestinationCard> rejections, Player player, GameInfo game);
    void selectTrainCard(TrainCard card, Player player, GameInfo game);
    void clearWilds(ArrayList<TrainCard> replacements, GameInfo game);
    void replaceTrainCard(TrainCard replacement, Integer index, GameInfo game);
    void drawTrainCard(TrainCard card, Player player, GameInfo game);
    void displayDestinationCards(ArrayList<DestinationCard> tickets, Player player, GameInfo game);
    void loginUser(User user, String authToken, ArrayList<GameInfo> gameInfos);
    void registerUser(User user, String authToken, ArrayList<GameInfo> gameInfos);
    void joinGame(Player player, GameName gameName);
    void createGame(GameInfo gameInfo);
    void startGame(GameInfo gameInfo);
    void claimColor(String username, PlayerColor playerColor);
    void addChatMessage(ChatMessage message);
}
