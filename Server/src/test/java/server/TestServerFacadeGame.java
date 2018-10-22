package server;

import static org.junit.Assert.* ;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import data.Command;
import data.CommandManager;
import model.ServerModel;
import modelclasses.DestinationCard;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import results.Results;
import modelclasses.User;
import modelclasses.GameInfo;
import modelclasses.GameName;

public class TestServerFacadeGame {

    @Before
    public void setUp(){
        // make three test users & give them authTokens
        ServerModel.getInstance().getUsers().put("user1", new User("user1", "password"));
        ServerModel.getInstance().getUsers().put("user2", new User("user2", "password"));
        ServerModel.getInstance().getUsers().put("user3", new User("user3", "password"));
        ServerModel.getInstance().getAuthTokens().put("auth1", "user1");
        ServerModel.getInstance().getAuthTokens().put("auth2", "user2");
        ServerModel.getInstance().getAuthTokens().put("auth3", "user3");

        // make them players
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("user1", PlayerColor.GREEN));
        players.add(new Player("user2", PlayerColor.BLUE));
        players.add(new Player("user3", PlayerColor.MAGENTA));

        // put them in the CommandManager
        CommandManager.getInstance().addClient("user1");
        CommandManager.getInstance().addClient("user2");
        CommandManager.getInstance().addClient("user3");

        // put them in a game together
        GameName gameName = new GameName("my game");
        GameInfo game = new GameInfo(gameName, players, 3);
        ServerModel.getInstance().getGames().put(gameName, game);
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
        Results results = ServerFacade.getInstance().createGame("newly created game", 4, "auth1");
        assertTrue(results.getSuccess());
        assertEquals(2, results.getClientCommands().size());

        // check that game is in the games map
        GameName name = new GameName("newly created game");
        Map<GameName, GameInfo> games = ServerModel.getInstance().getGames();
        assertTrue(games.containsKey(name));
    }

    @Test
    public void createRepeatGame() {
        Results results = ServerFacade.getInstance().createGame("test game", 5, "auth1");
        assertFalse(results.getSuccess());
        assertEquals("Game name already exists", results.getErrorMessage());
    }

    @Test
    public void createGameTooManyPlayers() {
        Results results = ServerFacade.getInstance().createGame("100 player game", 100, "auth1");
        assertFalse(results.getSuccess());
        assertEquals("Invalid player number", results.getErrorMessage());
    }

    @Test
    public void createGameTooFewPlayers() {
        Results results = ServerFacade.getInstance().createGame("0 player game", 0, "auth1");
        assertFalse(results.getSuccess());
        assertEquals("Invalid player number", results.getErrorMessage());
    }

    @Test
    public void joinGame() {
        GameName gameName = new GameName("test game");

        // find game before additional player is added
        GameInfo originalGameInfo = ServerModel.getInstance().getGameInfo(gameName);
        int originalNumPlayers = originalGameInfo.getPlayers().size();

        Results results = ServerFacade.getInstance().joinGame(gameName, "auth1");
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
        Results results = ServerFacade.getInstance().joinGame(name, "auth1");
        assertFalse(results.getSuccess());
        assertEquals("Game is full", results.getErrorMessage());
    }

    @Test
    public void joinNonexistentGame() {
        GameName name = new GameName("this game does not exist");
        Results results = ServerFacade.getInstance().joinGame(name,"auth1");
        assertFalse(results.getSuccess());
        assertEquals("Game does not exist", results.getErrorMessage());
    }

    @Test
    public void startGame() {
        GameName name = new GameName("my game");
        String clientAuthToken = "auth1";

        Results results = ServerFacade.getInstance().startGame(name, clientAuthToken);

        assertTrue(results.getSuccess());

        GameInfo game = ServerModel.getInstance().getGameInfo(name);
        assertEquals(98, game.getTrainCardDeck().size());
        assertEquals(21, game.getDestCardDeck().size());

        // check player hands
        for (Player p : game.getPlayers()) {
            ArrayList<TrainCard> playerTrainCards = p.getTrainCards();
            assertEquals(4, playerTrainCards.size());

            ArrayList<DestinationCard> playerDestCards = p.getDestinationCards();
            assertEquals(3, playerDestCards.size());
        }

        // check player commands
        assertEquals(1, results.getClientCommands().size());
        for (Player p : game.getPlayers()) {
            ArrayList<Command> userCommands = CommandManager.getInstance().getCommands(p.getUsername());
            if (p.getUsername().equals("user1")) {
                assertEquals(0, userCommands.size());
            }
            else {
                assertEquals(1, userCommands.size());
            }
        }
    }
}
