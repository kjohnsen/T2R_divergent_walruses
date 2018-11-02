package activity;

import java.util.ArrayList;
import java.util.List;

import modelclasses.Player;

public interface IGameLobbyView {
    void updatePlayerList(List<Player> players);
    void updateAvailableColors(List<String> colors, int position);
    void setStartGameEnabled(boolean enable);
    void startGame();
}
