package presenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import model.ClientModel;
import model.MockServerProxy;
import model.UIFacade;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.User;

import static org.junit.Assert.*;

public class TestGameListPresenter {

    private static GameListPresenter gameListPresenter;
    private static GameListMock listMock;

    private static UIFacade uiFacade = UIFacade.getInstance();

    @BeforeClass
    public static void setup() {
        listMock = new GameListMock();
        gameListPresenter = new GameListPresenter(listMock);
        uiFacade.setServerProxy(new MockServerProxy());
    }
    @Before
    public void reset() {
        listMock.reset();
        ClientModel.getInstance().reset();
        ClientModel.getInstance().addObserver(gameListPresenter);
    }
    @Test
    public void validCreateGameNameChange() {
        gameListPresenter.createGameNameChanged("success"); //must be "success" for mock to work
        assertTrue(listMock.gameEnabled());
    }
    @Test
    public void emptyCreateGameNameChange() {
        gameListPresenter.createGameNameChanged("");
        assertTrue(!listMock.gameEnabled());
    }

    @Test
    public void duplicateCreateGameNameChange() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("yoyo"));
        GameInfo game = new GameInfo(new GameName("orange"),players,2);
        ArrayList<GameInfo> games = new ArrayList<>();
        games.add(game);
        ClientModel.getInstance().setGameList(games);
        gameListPresenter.createGameNameChanged("orange");
        assertTrue(!listMock.gameEnabled());
    }
    @Test
    public void createGameSuccess() {
        ClientModel.getInstance().setCurrentUser(new User("billy", "pw"));
        gameListPresenter.createGameNameChanged("success");
        gameListPresenter.createGame(5);
        GameInfo gameInfo = ClientModel.getInstance().getCurrentGame();
        assertNotNull(gameInfo);
        assertEquals("success", gameInfo.getGameName().getName());
    }
    @Test
    public void createGameFail() {
        ClientModel.getInstance().setCurrentUser(new User("billy", "pw"));
        gameListPresenter.createGameNameChanged("fail");
        gameListPresenter.createGame(2);
        GameInfo gameInfo = ClientModel.getInstance().getCurrentGame();
        assertNull(gameInfo);
    }

    /*tests for adding a list with one game, then updating it to two, then adding another game with
    no empty spots and makes sure it didn't get added
     */
    @Test
    public void updateGameList() {
        ArrayList<GameInfo> games = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Joe",PlayerColor.BLUE));
        players.add(new Player("Kirk",PlayerColor.GREEN));
        games.add(new GameInfo(new GameName("What"),players,4));
        ClientModel.getInstance().setGameList(games);
        assertArrayEquals(games.toArray(),listMock.getGames().toArray());
        players.add(new Player("Wally",PlayerColor.RED));
        games.add(new GameInfo(new GameName("Why"),players,4));
        ClientModel.getInstance().setGameList(games);
        assertArrayEquals(games.toArray(),listMock.getGames().toArray());
        games.add(new GameInfo(new GameName("Where"),players,3));
        ClientModel.getInstance().setGameList(games);
        assertEquals(2,listMock.getGames().size());
    }
}
