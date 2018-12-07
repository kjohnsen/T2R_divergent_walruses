import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import DaoFactory.SQLFactoryPlugin;

import data.Command;
import modelclasses.User;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.GameInfo;

public class DBTests {
    private SQLFactoryPlugin factoryPlugin = new SQLFactoryPlugin();

    @Before
    public void openConnection() {
        factoryPlugin.openConnection();
        createAllTables();
    }

    public void createAllTables() {
        factoryPlugin.createTables();
    }

    @Test
    public void createUser() {
        User user = new User("newuser1", "password");
        factoryPlugin.getUserDAO().createUser(user);
    }

    @Test
    public void readUser() {
        User user = new User("readme", "pass");
        factoryPlugin.getUserDAO().createUser(user);
        User readUser = factoryPlugin.getUserDAO().readUser("readme");

        assertEquals("readme", readUser.getUsername());
        assertEquals("pass", readUser.getPassword());
    }

    @Test
    public void createCommand() {
        GameName gameName = new GameName("create");
        Command command = new Command("model.CommandFacade", "_startNextTurn", Arrays.asList(new Object[] {"username"}));
        factoryPlugin.getCommandDAO().createCommand(command, gameName);
    }

    @Test
    public void readCommands() {
        GameName gameName1 = new GameName("read1");
        GameName gameName2 = new GameName("read2");
        Command command = new Command("model.CommandFacade", "_startNextTurn", Arrays.asList(new Object[] {"username"}));
        factoryPlugin.getCommandDAO().createCommand(command, gameName1);
        factoryPlugin.getCommandDAO().createCommand(command, gameName1);
        factoryPlugin.getCommandDAO().createCommand(command, gameName2);

        ArrayList<Command> game1Commands = factoryPlugin.getCommandDAO().readCommands(gameName1);
        assertEquals(2, game1Commands.size());

        ArrayList<Command> game2Commands = factoryPlugin.getCommandDAO().readCommands(gameName2);
        assertEquals(1, game2Commands.size());
    }

    @Test
    public void deleteCommands() {
        GameName gameName = new GameName("delete");
        Command command = new Command("model.CommandFacade", "_startNextTurn", Arrays.asList(new Object[] {"username"}));
        factoryPlugin.getCommandDAO().createCommand(command, gameName);
        factoryPlugin.getCommandDAO().createCommand(command, gameName);

        ArrayList<Command> commands = factoryPlugin.getCommandDAO().readCommands(gameName);
        assertEquals(2, commands.size());

        factoryPlugin.getCommandDAO().deleteCommand(gameName);
    }

    @Test
    public void createGame() {
        GameName gameName = new GameName("createGameInfo");
        ArrayList<Player> players = new ArrayList<>();
        GameInfo gameInfo = new GameInfo(gameName, players, 2);

        factoryPlugin.getGameInfoDAO().createGameInfo(gameInfo);
    }

    @Test
    public void readGame() {
        GameName gameName = new GameName("readGameInfo");
        ArrayList<Player> players = new ArrayList<>();
        GameInfo gameInfo = new GameInfo(gameName, players, 2);

        factoryPlugin.getGameInfoDAO().createGameInfo(gameInfo);
        GameInfo gameFromDB = factoryPlugin.getGameInfoDAO().readGameInfo(gameName);

        assertEquals("readGameInfo", gameFromDB.getGameName().getName());
        assertEquals(2, gameFromDB.getNumPlayers());
    }

    @Test
    public void updateGame() {
        GameName gameName = new GameName("updateGameInfo");
        ArrayList<Player> players = new ArrayList<>();
        GameInfo originalGame = new GameInfo(gameName, players, 2);

        factoryPlugin.getGameInfoDAO().createGameInfo(originalGame);
        GameInfo originalGameFromDB = factoryPlugin.getGameInfoDAO().readGameInfo(gameName);
        assertEquals("updateGameInfo", originalGameFromDB.getGameName().getName());
        assertEquals(2, originalGameFromDB.getNumPlayers());

        GameInfo newGame = new GameInfo(gameName, players, 5);
        factoryPlugin.getGameInfoDAO().updateGameInfo(newGame);
        GameInfo newGameFromDB = factoryPlugin.getGameInfoDAO().readGameInfo(gameName);
        assertEquals("updateGameInfo", newGameFromDB.getGameName().getName());
        assertEquals(5, newGameFromDB.getNumPlayers());
    }

    @Test
    public void deleteGame() {
        GameName gameName = new GameName("deleteGame");
        ArrayList<Player> players = new ArrayList<>();
        GameInfo gameInfo = new GameInfo(gameName, players, 3);

        factoryPlugin.getGameInfoDAO().createGameInfo(gameInfo);

        GameInfo gameFromDB = factoryPlugin.getGameInfoDAO().readGameInfo(gameName);
        assertEquals(3, gameFromDB.getNumPlayers());

        factoryPlugin.getGameInfoDAO().deleteGameInfo(gameInfo);
    }

    @After
    public void closeConnection() {
        factoryPlugin.closeConnection();
    }
}
