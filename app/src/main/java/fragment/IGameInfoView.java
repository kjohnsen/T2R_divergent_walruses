package fragment;

import java.util.List;

import modelclasses.Player;

public interface IGameInfoView {
    void updatePlayerInfo(List<Player> players);
    void updateDecksInfo(int destDeckSize, int trainDeckSize);
}
