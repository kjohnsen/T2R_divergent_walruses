package presenter;

public interface IGameListPresenter {
    void createGameNameChanged(String gameName);
    void createGame(int numPlayers);
    void joinGame(String gameName);
}
