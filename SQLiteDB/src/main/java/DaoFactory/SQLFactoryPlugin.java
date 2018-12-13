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
        this.closeConnection();
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
                    stmt.executeUpdate("drop table if exists " + tableName);
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

    }

    @Override
    public void endTransaction() {
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

    public static void main(String[] args) {
        SQLFactoryPlugin sqlFactoryPlugin = new SQLFactoryPlugin();
        sqlFactoryPlugin.openConnection();
        sqlFactoryPlugin.clearDB();
        sqlFactoryPlugin.endTransaction();
        sqlFactoryPlugin.closeConnection();
    }

}
