package DaoFactory;

import persistence.ICommandDAO;
import persistence.Result;

import data.Command;
import data.Serializer;
import modelclasses.GameName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Base64;

public class SQLCommandDao implements ICommandDAO {

    private Serializer serializer = new Serializer();

    @Override
    public Result createCommand(Command command, GameName gameName) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

                String commandId = UUID.randomUUID().toString();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(command);
                oos.flush();
                Base64.Encoder encoder = Base64.getEncoder();
                String serializedCommand = new String(encoder.encode(baos.toByteArray()));

                stmt.executeUpdate("insert into commands (command_id, game_name, command) " +
                        "values ('" + commandId + "', '" + gameName + "', '" + serializedCommand + "');");


            } catch (java.io.IOException ex) {
                ex.printStackTrace();
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
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] commandByteArray = decoder.decode(rs.getString("command").getBytes());
                    ByteArrayInputStream baip = new ByteArrayInputStream(commandByteArray);
                    ObjectInputStream ois = new ObjectInputStream(baip);
                    Command command = (Command) ois.readObject();
                    commands.add(command);
                }
                return commands;
            } catch (java.io.IOException ex) {
                ex.printStackTrace();
            } catch (java.lang.ClassNotFoundException ce) {
                System.out.println("class not found exception");
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
    public Result deleteCommand(GameName gameName) {
        return null;
    }

}
