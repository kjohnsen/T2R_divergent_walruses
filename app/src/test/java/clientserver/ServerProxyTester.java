package clientserver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import data.Command;
import modelclasses.GameID;
import results.GameResults;
import results.LoggedInResults;
import results.Results;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServerProxyTester {

    private LoggedInResults successLoggedIn = new LoggedInResults();
    private GameResults successCreateGame = new GameResults( new GameID("test", "test"));
    private Results commandSuccessResults = new LoggedInResults();
    private ClientCommunicator clientCommunicator;

    @Before
    public void setUp() throws Exception {
        clientCommunicator = mock(ClientCommunicator.class);
        ServerProxy.getInstance().setClientCommunicator(clientCommunicator);
        successLoggedIn.setSuccess(true);
        successCreateGame.setSuccess(true);
        String[] strings = {};
        Object[] objects = {};
        commandSuccessResults.getClientCommands().add(new Command("test", "test", strings, objects));
    }

    @Test
    public void loginTest() {
        when(clientCommunicator.send(any(Command.class))).thenReturn(successLoggedIn);
        Results results = ServerProxy.getInstance().loginUser("user", "password");

        assertTrue(results instanceof LoggedInResults);
        assertTrue(results.getSuccess());
    }

    @Test
    public void registerTest() {
        when(clientCommunicator.send(any(Command.class))).thenReturn(successLoggedIn);
        Results results = ServerProxy.getInstance().registerUser("user", "password");

        assertTrue(results instanceof LoggedInResults);
        assertTrue(results.getSuccess());
    }

    @Test
    public void createGameTest() {
        when(clientCommunicator.send(any(Command.class))).thenReturn(successCreateGame);
        Results results = ServerProxy.getInstance().createGame("game", 2);

        assertTrue(results instanceof GameResults);
        assertTrue(results.getSuccess());
    }

    @Test
    public void getCommandsTest() {
        when(clientCommunicator.send(any(Command.class))).thenReturn(commandSuccessResults);
        ArrayList<Command> commands = ServerProxy.getInstance().getCommands("test");

        assertTrue(commands.size() > 0);
    }

}
