package DaoFactory;

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

    public SQLFactoryPlugin() {}

    @Override
    public void openConnection() {
        try{
            String url = "jdbc:sqlite:SQLiteDB.sqlite";
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

    }

    @Override
    public void startTransaction() {
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("BEGIN TRANSACTION ");
            } catch (NullPointerException n) {
                System.out.println("connection not open");
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("startTransaction failed");
        }
    }

    @Override
    public void endTransaction() {
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("COMMIT TRANSACTION ");
            } catch (NullPointerException n) {
                System.out.println("connection not open");
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("endTransaction failed");
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
