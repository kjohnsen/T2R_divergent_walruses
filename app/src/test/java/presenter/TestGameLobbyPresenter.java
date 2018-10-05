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

public class TestGameLobbyPresenter {
    private static GameLobbyPresenter gameLobbyPresenter;
    private static GameLobbyMock lobbyMock;

    @BeforeClass
    public static void setup() {
        lobbyMock = new GameLobbyMock();
        gameLobbyPresenter = new GameLobbyPresenter(lobbyMock);
    }
    @Before
    public void reset() {
        lobbyMock.reset();
        ClientModel.getInstance().reset();
    }
    @Test
    public void updateColorsAndPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Alvin", PlayerColor.BLACK));
        players.add(new Player("Calvin", PlayerColor.BLUE));
        GameInfo game = new GameInfo(new GameName("Balvin"),players,3);
        ClientModel.getInstance().setCurrentGame(game);
        ClientModel.getInstance().addObserver(gameLobbyPresenter);
        ClientModel.getInstance().setCurrentGamePlayers(players);
        assertArrayEquals(players.toArray(), lobbyMock.getPlayers().toArray());
        assertEquals(5, lobbyMock.getColors().size());
        players.add(new Player("Ike", PlayerColor.UNCHOSEN));
        ClientModel.getInstance().setCurrentGamePlayers(players);
        assertArrayEquals(players.toArray(), lobbyMock.getPlayers().toArray());
        assertEquals(5, lobbyMock.getColors().size());
    }
}
