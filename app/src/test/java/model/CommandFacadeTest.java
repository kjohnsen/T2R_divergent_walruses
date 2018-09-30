package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.User;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class CommandFacadeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void loginUser() {
        User user = new User("bobby");
        CommandFacade.getInstance().loginUser(user, "auth");
        assertEquals(ClientModel.getInstance().getCurrentUser(), user);
    }

    @Test
    public void registerUser() {
        User user = new User("bobby");
        CommandFacade.getInstance().registerUser(user, "auth");
        assertEquals(ClientModel.getInstance().getCurrentUser(), user);
    }

    @Test
    public void joinGame() {
        Player player = new Player("bobby");
        GameID gameID = new GameID("game", "id");
        GameInfo gameInfo = new GameInfo(gameID, new ArrayList<Player>(), 5);
        CommandFacade.getInstance().joinGame(player, gameID);
        assertThat(ClientModel.getInstance().getCurrentGame().getPlayers(), hasItem(player));
    }

    @Test
    public void createGame() {
        GameID gameID = new GameID("game", "id");
        GameInfo gameInfo = new GameInfo(gameID, new ArrayList<Player>(), 5);
        CommandFacade.getInstance().createGame(gameInfo);
        assertThat(ClientModel.getInstance().getGameList(), hasItem(gameInfo));
    }

    @Test
    public void startGame() {
        GameID gameID = new GameID("game", "id");
        ArrayList<GameInfo> gameList = new ArrayList<>();
        GameInfo gameInfo = new GameInfo(gameID, new ArrayList<Player>(), 5);
        gameList.add(gameInfo);
        ClientModel.getInstance().setGameList(gameList);
        assertThat(ClientModel.getInstance().getGameList(), hasItem(gameInfo));
    }

    @Test
    public void claimColor() {
    }
}