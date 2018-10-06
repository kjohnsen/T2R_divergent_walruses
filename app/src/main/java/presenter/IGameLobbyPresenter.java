package presenter;

import modelclasses.GameName;

public interface IGameLobbyPresenter {
    String chooseColor(String color, GameName gameName);
    String startGame();
}
