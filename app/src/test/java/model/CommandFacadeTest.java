package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.User;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class CommandFacadeTest {

    @Before
    public void setup() {
        ClientModel.getInstance().reset();
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
    public void singletonNotPersistingBetweenTests() {
        assertNull(ClientModel.getInstance().getCurrentUser());
    }

    @Test
    public void joinGame() {
        Player player = new Player("bobby");
        GameID gameID = new GameID("game", "id");
        ArrayList<GameInfo> gameList = new ArrayList<>();
        GameInfo gameInfo = new GameInfo(gameID, new ArrayList<Player>(), 5);
        gameList.add(gameInfo);
        ClientModel.getInstance().setGameList(gameList);

        CommandFacade.getInstance().joinGame(player, gameID);
        assertEquals(ClientModel.getInstance().getGame(gameID).getPlayer(player.getUsername()), player);
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

        CommandFacade.getInstance().startGame(gameID);
        assertEquals(ClientModel.getInstance().getCurrentGame(), gameInfo);
    }

    @Test
    public void claimColor() {
        Player player = new Player("bobby");
        GameID gameID = new GameID("game", "id");
        GameInfo gameInfo = new GameInfo(gameID, new ArrayList<Player>(), 5);
        ClientModel.getInstance().setCurrentGame(gameInfo);
        ClientModel.getInstance().getCurrentGame().addPlayer(player);
        CommandFacade.getInstance().claimColor("bobby", PlayerColor.BLUE);
        assertEquals(ClientModel.getInstance().getCurrentGame().getPlayer("bobby").getPlayerColor(),
                PlayerColor.BLUE);
    }
}