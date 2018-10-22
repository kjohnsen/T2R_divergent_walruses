package interfaces;

import java.util.ArrayList;

import modelclasses.DestinationCard;
import modelclasses.GameName;
import modelclasses.PlayerColor;
import results.Results;

public interface IServer {
    Results loginUser(String username, String password);

    Results registerUser(String username, String password);

    Results createGame(String name, Integer numPlayers, String authToken);

    Results joinGame(GameName gameName, String authToken);

    Results startGame(GameName gameName, String authToken);

    Results chooseColor(PlayerColor color, GameName gameName, String authToken);

    Results selectTrainCard(int index, GameName gameName, String authToken);

    Results drawTrainCard(GameName gameName, String authToken);

    Results drawDestinationCards(GameName gameName, String authToken);

    Results selectDestinationCards(ArrayList<DestinationCard> rejected, GameName gameName, String authToken);

    Results getCommands(String authToken);

    void setHostIP(String hostIP);

    void setHostPort(String hostPort);
}