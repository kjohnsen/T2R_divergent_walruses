package DaoFactory;

import persistence.ICommandDAO;
import persistence.Result;

import data.Command;
import modelclasses.GameName;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class SQLCommandDao implements ICommandDAO {

    @Override
    public Result createCommand(Command command, GameName gameName) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

                String commandId = UUID.randomUUID().toString();

                String serializedCommand = ObjectSerializer.serializeObject(command);

                stmt.executeUpdate("insert into commands (command_id, game_name, command) " +
                        "values ('" + commandId + "', '" + gameName + "', '" + serializedCommand + "');");

            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("create command failed");
        }
        return null;
    }

    @Override
    public ArrayList<Command> readCommands(GameName gameName) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("select * from commands where game_name = '" + gameName.getName() + "'");
                ArrayList<Command> commands = new ArrayList<>();
                while (rs.next()) {
                    Command command = (Command) ObjectSerializer.deserializeObject(rs.getString("command").getBytes());
                    commands.add(command);
                }
                return commands;
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("read user failed");
        }
        return null;
    }

    @Override
    public ArrayList<Command> readAllCommands() {
        return null;
    }

    @Override
    public Result deleteCommand(GameName gameName) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = connection.createStatement();
            try {
                stmt.executeUpdate("delete from commands where game_name = '" + gameName.getName() + "';");
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("delete commands failed");
        }
        return null;
    }

}
