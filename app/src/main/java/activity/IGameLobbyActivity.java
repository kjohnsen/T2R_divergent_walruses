package activity;

import java.util.ArrayList;

import modelclasses.Player;

public interface IGameLobbyActivity {
    void updatePlayerList(ArrayList<Player> players);
    void updateAvailableColors(ArrayList<String> colors);
    void displayErrorMessage(String message);
    void setStartGameEnabled(boolean enable);
    void startGame();
}
