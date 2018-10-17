package activity;

import java.util.ArrayList;

import modelclasses.Player;

public interface IGameLobbyView {
    void updatePlayerList(ArrayList<Player> players);
    void updateAvailableColors(ArrayList<String> colors);
    void setStartGameEnabled(boolean enable);
    void startGame();
}
