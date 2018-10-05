package server;

import static org.junit.Assert.* ;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import model.ServerModel;
import modelclasses.Player;
import results.Results;
import modelclasses.User;
import modelclasses.GameInfo;
import modelclasses.GameName;

public class TestServerFacadeGame {

    private ServerFacade serverFacade;

    @Before
    public void setUp(){
        serverFacade = new ServerFacade();
    }

    @Before
    public void enterUser() {
        User user = new User("user1", "pass");
        ServerModel.getInstance().getUsers().put("user1", user);
        ServerModel.getInstance().getAuthTokens().put("auth1", "user1");
    }

    @Before
    public void enterGame() {
        GameName gameName = new GameName("test game");
        int numPlayers = 3;
        ArrayList<Player> players = new ArrayList<>();
        GameInfo gameInfo = new GameInfo(gameName, players, numPlayers);
        ServerModel.getInstance().getGames().put(gameName, gameInfo);
    }

    @Before
    public void enterFullGame() {
        GameName gameName = new GameName("full game");
        int numPlayers = 2;
        ArrayList<Player> players = new ArrayList<>();

        Player player1 = new Player("u1");
        Player player2 = new Player("u2");
        players.add(player1);
        players.add(player2);

        GameInfo gameInfo = new GameInfo(gameName, players, numPlayers);
        ServerModel.getInstance().getGames().put(gameName, gameInfo);
    }

    @Test
    public void createGame() {
        Results results = serverFacade.createGame("new game", 4, "auth1");
        assertTrue(results.getSuccess());
        assertEquals(2, results.getClientCommands().size());

        // check that game is in the games map
        GameName name = new GameName("new game");
        Map<GameName, GameInfo> games = ServerModel.getInstance().getGames();
        assertTrue(games.containsKey(name));

        // check that the game has the user in it
        GameInfo game = games.get(name);
        assertTrue(game.getPlayers().size() > 0);
        Player p = game.getPlayer("user1");
        assertNotNull(p);

        // check that the user has the game in their game list
        User u = ServerModel.getInstance().getUser("user1");
        ArrayList<GameName> userGames = u.getGameNameList();
        assertTrue(userGames.size() > 0);
        boolean hasGame = false;
        for (GameName gameName : userGames) {
            if (gameName.equals(name)) {
                hasGame = true;
            }
        }
        assertTrue(hasGame);
    }

    @Test
    public void createRepeatGame() {
        Results results = serverFacade.createGame("test game", 5, "auth1");
        assertFalse(results.getSuccess());
        assertEquals("Game name already exists", results.getErrorMessage());
    }

    @Test
    public void createGameTooManyPlayers() {
        Results results = serverFacade.createGame("100 player game", 100, "auth1");
        assertFalse(results.getSuccess());
        assertEquals("Invalid player number", results.getErrorMessage());
    }

    @Test
    public void createGameTooFewPlayers() {
        Results results = serverFacade.createGame("0 player game", 0, "auth1");
        assertFalse(results.getSuccess());
        assertEquals("Invalid player number", results.getErrorMessage());
    }

    @Test
    public void joinGame() {
        GameName gameName = new GameName("test game");

        // find game before additional player is added
        GameInfo originalGameInfo = ServerModel.getInstance().getGameInfo(gameName);
        int originalNumPlayers = originalGameInfo.getPlayers().size();

        Results results = serverFacade.joinGame(gameName, "auth1");
        assertTrue(results.getSuccess());

        // check that game has one more player than before
        GameInfo newGameInfo = ServerModel.getInstance().getGameInfo(gameName);
        int newNumPlayers = newGameInfo.getPlayers().size();
        assertEquals(originalNumPlayers + 1, newNumPlayers);

        // check that the user is in the game
        Player p = newGameInfo.getPlayer("user1");
        assertNotNull(p);

        // check that the game is in the user's game list
        User u = ServerModel.getInstance().getUser("user1");
        assertTrue(u.gameInList(gameName));
    }

    @Test
    public void joinFullGame() {
        GameName name = new GameName("full game");
        Results results = serverFacade.joinGame(name, "auth1");
        assertFalse(results.getSuccess());
        assertEquals("Game is full", results.getErrorMessage());
    }

    @Test
    public void joinNonexistentGame() {
        GameName name = new GameName("this game does not exist");
        Results results = serverFacade.joinGame(name,"auth1");
        assertFalse(results.getSuccess());
        assertEquals("Game does not exist", results.getErrorMessage());
    }


}
