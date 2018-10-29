package model;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

import clientserver.ServerProxy;
import modelclasses.ChatMessage;
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
        assertNull(success);

        String fail = uiFacade.createGame("fail", 5);
        assertTrue(fail.equals("Game already exists"));

        String nullResults = uiFacade.createGame("null", 5);
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
        GameInfo successInfo = new GameInfo(new GameName("success"), new ArrayList<Player>(), 2);
        GameInfo failInfo = new GameInfo(new GameName("fail"), new ArrayList<Player>(), 2);
        GameInfo nullInfo = new GameInfo(new GameName("null"), new ArrayList<Player>(), 2);
        ClientModel.getInstance().setCurrentGame(successInfo);
        String success = uiFacade.startGame();
        ClientModel.getInstance().setCurrentGame(failInfo);
        String fail = uiFacade.startGame();
        ClientModel.getInstance().setCurrentGame(nullInfo);
        String nullResults = uiFacade.startGame();

        assertNull(success);
        assertTrue(fail.equals("Game cannot start"));
        assertTrue(nullResults.equals("Cannot reach server"));
    }

    @Test
    public void sendMessage() {
        ChatMessage successMessage = new ChatMessage("username", "Test Message");
        ChatMessage failMessage = new ChatMessage("username", "Error");
        ChatMessage nullMessage = new ChatMessage("username", "Null");

        String success = uiFacade.sendChatMessage(successMessage);
        String fail = uiFacade.sendChatMessage(failMessage);
        String nullString = uiFacade.sendChatMessage(nullMessage);

        assertNull(success);
        assertTrue(fail.equals("Error Message"));
        assertTrue(nullString.equals("Cannot reach server"));
    }
}
