package presenter;

import modelclasses.GameName;

public interface IGameLobbyPresenter {
    void chooseColor(String color, GameName gameName);
    void startGame();
}
