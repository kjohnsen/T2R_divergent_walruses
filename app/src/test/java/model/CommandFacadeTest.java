package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import modelclasses.GameName;
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
        User user = new User("bobby", "password");

        ArrayList<GameInfo> gameList = new ArrayList<>();
        CommandFacade.getInstance().loginUser(user, "auth", gameList);
        assertEquals(ClientModel.getInstance().getCurrentUser(), user);
    }

    @Test
    public void registerUser() {
        User user = new User("bobby", "password");

        CommandFacade.getInstance().registerUser(user, "auth", null);
        assertEquals(ClientModel.getInstance().getCurrentUser(), user);
    }

    @Test
    public void singletonNotPersistingBetweenTests() {
        assertNull(ClientModel.getInstance().getCurrentUser());
    }

    @Test
    public void joinGame() {
        User user = new User("bobby", "password");
        ClientModel.getInstance().setCurrentUser(user);
        Player player = new Player("bobby");
        GameName gameName = new GameName("game");
        ArrayList<GameInfo> gameList = new ArrayList<>();
        GameInfo gameInfo = new GameInfo(gameName, new ArrayList<Player>(), 5);
        gameList.add(gameInfo);
        ClientModel.getInstance().setGameList(gameList);

        CommandFacade.getInstance().joinGame(player, gameName);
        assertEquals(ClientModel.getInstance().getGame(gameName).getPlayer(player.getUsername()), player);
    }

    @Test
    public void createGame() {
        GameName gameName = new GameName("game");
        GameInfo gameInfo = new GameInfo(gameName, new ArrayList<Player>(), 5);

        CommandFacade.getInstance().createGame(gameInfo);
        assertThat(ClientModel.getInstance().getGameList(), hasItem(gameInfo));
    }

    @Test
    public void claimColor() {
        Player player = new Player("bobby");
        GameName gameName = new GameName("game");
        GameInfo gameInfo = new GameInfo(gameName, new ArrayList<Player>(), 5);
        ClientModel.getInstance().setCurrentGame(gameInfo);
        ClientModel.getInstance().getCurrentGame().addPlayer(player);
        CommandFacade.getInstance().claimColor("bobby", PlayerColor.BLUE);
        assertEquals(ClientModel.getInstance().getCurrentGame().getPlayer("bobby").getPlayerColor(),
                PlayerColor.BLUE);
    }
}