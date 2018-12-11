package DaoFactory;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import persistence.IPersistencePluginFactory;
import persistence.ICommandDAO;
import persistence.IGameInfoDAO;
import persistence.IUserDAO;

public class SQLFactoryPlugin implements IPersistencePluginFactory{
    private IUserDAO userDAO = new SQLUserDao();
    private IGameInfoDAO gameInfoDAO = new SQLGameInfoDao();
    private ICommandDAO commandDAO = new SQLCommandDao();
    static private Connection connection = null;

    public SQLFactoryPlugin() {
        this.openConnection();
        createTables();
    }

    @Override
    public void openConnection() {
        try{
            DriverManager.registerDriver(new JDBC());
            String url = "jdbc:sqlite:SQLiteDB/data.db";
            Connection conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            connection = conn;
        }
        catch(SQLException e){
            System.out.println("openConnection failed");
        }
    }

    @Override
    public void closeConnection() {
        try{
            connection.commit();
            connection.close();
            connection = null;
        }
        catch(SQLException e){
            System.out.println("closeConnection failed");
        }
    }

    public void createTables() {
        TableCreator.createCommandTable(connection);
        TableCreator.createGameTable(connection);
        TableCreator.createUserTable(connection);
    }

    @Override
    public void clearDB() {
        String[] tableNames = {"commands", "users", "games"};
        for (String tableName : tableNames) {
            try {
                Statement stmt = null;
                try {
                    stmt = connection.createStatement();
                    stmt.executeUpdate("drop table if exists " + tableName +
                            " (command_id string primary key, " +
                            "game_name string not null, " +
                            "command string not null);");
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } catch (SQLException e) {
                System.out.println("clearDB failed");
            }
        }
    }

    @Override
    public void startTransaction() {
//        try {
//            Statement stmt = null;
//            try {
//                stmt = connection.createStatement();
//                stmt.executeUpdate("BEGIN TRANSACTION ");
//            } catch (NullPointerException n) {
//                System.out.println("connection not open");
//            } finally {
//                if (stmt != null) {
//                    stmt.close();
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("startTransaction failed");
//        }
    }

    @Override
    public void endTransaction() {
//        try {
//            Statement stmt = null;
//            try {
//                stmt = connection.createStatement();
//                stmt.executeUpdate("COMMIT TRANSACTION ");
//            } catch (NullPointerException n) {
//                System.out.println("connection not open");
//            } finally {
//                if (stmt != null) {
//                    stmt.close();
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("endTransaction failed");
//        }
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public IUserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public ICommandDAO getCommandDAO() {
        return commandDAO;
    }

    @Override
    public IGameInfoDAO getGameInfoDAO() {
        return gameInfoDAO;
    }

    public static Connection getConnection() {
        return connection;
    }

}
