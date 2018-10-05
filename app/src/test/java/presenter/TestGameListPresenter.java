package presenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import model.ClientModel;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.PlayerColor;

import static org.junit.Assert.*;

public class TestGameListPresenter {

    private static GameListPresenter gameListPresenter;
    private static GameListMock listMock;

    @BeforeClass
    public static void setup() {
        listMock = new GameListMock();
        gameListPresenter = new GameListPresenter(listMock);
    }
    @Before
    public void reset() {
        listMock.reset();
        ClientModel.getInstance().reset();
    }
    @Test
    public void validCreateGame() {
        gameListPresenter.createGameNameChanged("mygame");
        assertTrue(listMock.gameEnabled());
    }
    @Test
    public void emptyCreateGame() {
        gameListPresenter.createGameNameChanged("");
        assertTrue(!listMock.gameEnabled());
    }
    @Test
    public void duplicateCreateGame() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("yoyo"));
        GameInfo game = new GameInfo(new GameName("orange"),players,2);
        ArrayList<GameInfo> games = new ArrayList<>();
        games.add(game);
        ClientModel.getInstance().setGameList(games);
        gameListPresenter.createGameNameChanged("orange");
        assertTrue(!listMock.gameEnabled());
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
