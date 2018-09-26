package presenter;

public interface IGameListPresenter {
    void createGame(String gameName, int numPlayers);
    void joinGame(String gameID);
}
