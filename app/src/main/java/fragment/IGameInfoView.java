package fragment;

import java.util.List;

import modelclasses.Player;

public interface IGameInfoView {
    void updatePlayerInfo(List<Player> players);
    void updateTrainDeckInfo(int trainDeckSize);
    void updateDestDeckInfo(int destDeckSize);
    void updateCurrentPlayer(String player);
}
