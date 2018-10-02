package model;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

import clientserver.ServerProxy;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import results.Results;

public class UIFacadeTest {

    private UIFacade uiFacade = UIFacade.getInstance();

    @Before
    public void setup() {
        uiFacade.setServerProxy(new MockServerProxy());
    }

    @Test
    public void loginUser() {
        String successResults = uiFacade.loginUser("success", "password");
        String failResults = uiFacade.loginUser("fail", "password");
        String nullResults = uiFacade.loginUser("null", "password");

        assertNull(successResults);
        assertTrue(failResults.equals("Password does not match"));
        assertTrue(nullResults.equals("Cannot reach server"));
    }

    @Test
    public void registerUser() {
        String successResults = uiFacade.registerUser("success", "password");
        String failResults = uiFacade.registerUser("fail", "password");
        String nullResults = uiFacade.registerUser("null", "password");

        assertNull(successResults);
        assertTrue(failResults.equals("User already exists"));
        assertTrue(nullResults.equals("Cannot reach server"));
    }

    @Test
    public void createGame() {
        String success = uiFacade.createGame("success", 5);
        String fail = uiFacade.createGame("fail", 5);
        String nullResults = uiFacade.createGame("null", 5);

        assertNull(success);
        assertTrue(fail.equals("Game already exists"));
        assertTrue(nullResults.equals("Cannot reach server"));
    }

    @Test
    public void joinGame() {
        String success = uiFacade.joinGame("success");
        String fail = uiFacade.joinGame("fail");
        String nullResults = uiFacade.joinGame("null");

        assertNull(success);
        assertTrue(fail.equals("Game is full"));
        assertTrue(nullResults.equals("Cannot reach server"));
    }

    @Test
    public void startGame() {
        String success = uiFacade.startGame(new GameInfo(new GameName("success"), new ArrayList<Player>(), 0));
        String fail = uiFacade.startGame(new GameInfo(new GameName("fail"), new ArrayList<Player>(), 0));
        String nullResults = uiFacade.startGame(new GameInfo(new GameName("null"), new ArrayList<Player>(), 0));

        assertNull(success);
        assertTrue(fail.equals("Game cannot start"));
        assertTrue(nullResults.equals("Cannot reach server"));
    }
}
