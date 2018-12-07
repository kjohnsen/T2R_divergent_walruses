package DaoFactory;

import persistence.ICommandDAO;
import persistence.Result;

import data.Command;
import modelclasses.GameName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.UUID;

public class SQLCommandDao implements ICommandDAO {

    @Override
    public Result create(GameName gameName, Command command) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            PreparedStatement stmt = null;
//            Statement stmt = null;
            try {
//                stmt = connection.createStatement();
                String sql = "insert into commands (command_id, game_name, command) values (?, ?, ?)";
                stmt = connection.prepareStatement(sql);

                String commandId = UUID.randomUUID().toString();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(command);
                byte[] commandAsBytes = baos.toByteArray();
//                stmt.executeUpdate("insert into commands (command_id, game_name, command) " +
//                        "values ('" + commandId + "', '" + gameName + "', '" + commandAsBytes + "');");

                Blob blob = connection.createBlob();
                blob.setBytes(1, commandAsBytes);

                stmt.setString(1, commandId);
                stmt.setString(2, gameName.getName());
                stmt.setBlob(3, blob);

                stmt.executeUpdate();

            } catch (java.io.IOException ex) {
                System.out.println("io exception");
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

//    Statement stmt = dbConn.createStatement();
//    ResultSet rs = stmt.executeQuery("SELECT emp FROM Employee");
//    while (rs.next()) {
//        byte[] st = (byte[]) rs.getObject(1);
//        ByteArrayInputStream baip = new ByteArrayInputStream(st);
//        ObjectInputStream ois = new ObjectInputStream(baip);
//        Employee emp = (Employee) ois.readObject();
//    }
//    stmt.close();
//    rs.close();
//    dbConn.close();

    @Override
    public ArrayList<Command> read(GameName gameName) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("select * from commands where game_name = '" + gameName.getName() + "'");
                ArrayList<Command> commands = new ArrayList<>();
                while (rs.next()) {
                    System.out.println(rs.getObject("command").toString());
                    byte[] commandByteArray = (byte[]) rs.getObject(3);
                    ByteArrayInputStream baip = new ByteArrayInputStream(commandByteArray);
                    ObjectInputStream ois = new ObjectInputStream(baip);
                    Command command = (Command) ois.readObject();
                    commands.add(command);
                }
                return commands;
            } catch (java.io.IOException ex) {
                System.out.println("io exception");
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
    public Result delete(ArrayList<Command> commands) {
        return null;
    }

}
