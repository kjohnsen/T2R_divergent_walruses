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

public class DBTests {
    private SQLFactoryPlugin factoryPlugin = new SQLFactoryPlugin();

    @Before
    public void openConnection() {
        factoryPlugin.openConnection();
    }

    @Test
    public void createAllTables() {
        factoryPlugin.createTables();
    }

    @Test
    public void createUser() {
        User user = new User("newuser1", "password");
        factoryPlugin.getUserDAO().create(user);
    }

    @Test
    public void readUser() {
        User user = new User("readme", "pass");
        factoryPlugin.getUserDAO().create(user);
        User readUser = factoryPlugin.getUserDAO().read("readme");

        assertEquals("readme", readUser.getUsername());
        assertEquals("pass", readUser.getPassword());
    }

    @Test
    public void createCommand() {
        GameName gameName = new GameName("create");
        Command command = new Command("model.CommandFacade", "_startNextTurn", Arrays.asList(new Object[] {"username"}));
        factoryPlugin.getCommandDAO().create(gameName, command);
    }

    @Test
    public void readCommands() {
        GameName gameName1 = new GameName("read1");
        GameName gameName2 = new GameName("read2");
        Command command = new Command("model.CommandFacade", "_startNextTurn", Arrays.asList(new Object[] {"username"}));
        factoryPlugin.getCommandDAO().create(gameName1, command);
        factoryPlugin.getCommandDAO().create(gameName1, command);
        factoryPlugin.getCommandDAO().create(gameName2, command);

        ArrayList<Command> game1Commands = factoryPlugin.getCommandDAO().read(gameName1);
        assertEquals(2, game1Commands.size());

        ArrayList<Command> game2Commands = factoryPlugin.getCommandDAO().read(gameName2);
        assertEquals(1, game2Commands.size());
    }

    @After
    public void closeConnection() {
        factoryPlugin.closeConnection();
    }
}
