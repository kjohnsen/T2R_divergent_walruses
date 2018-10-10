package presenter;

public interface IGameListPresenter {
    void createGameNameChanged(String gameName);
    String createGame(int numPlayers);
    String joinGame(String gameName);
    void getGameListInfo();
}
