package DaoFactory;

import persistence.IGameInfoDAO;
import persistence.Result;

import modelclasses.GameInfo;
import modelclasses.GameName;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLGameInfoDao implements IGameInfoDAO {

    @Override
    public Result createGameInfo(GameInfo gameInfo) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

                String serializedGame = ObjectSerializer.serializeObject(gameInfo);

                stmt.executeUpdate("insert into games (game_name, game_state) " +
                        "values ('" + gameInfo.getGameName().getName() + "', '" + serializedGame + "');");
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("create game info failed");
        }
        return null;
    }

    @Override
    public ArrayList<GameInfo> readAllGameInfos() {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = null;
            ArrayList<GameInfo> games = new ArrayList<>();
            try {
                rs = stmt.executeQuery("select * from games");
                while (rs.next()) {
                    GameInfo gameInfo = (GameInfo) ObjectSerializer.deserializeObject(rs.getString("game_state").getBytes());
                    games.add(gameInfo);
                }
                return games;
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("read all game infos failed");
        }
        return null;
    }

    @Override
    public GameInfo readGameInfo(GameName gameName) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("select * from games where game_name = '" + gameName.getName() + "'");
                while (rs.next()) {
                    return (GameInfo) ObjectSerializer.deserializeObject(rs.getString("game_state").getBytes());
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }

        } catch (SQLException e) {
            System.out.println("read game info failed");
        }
        return null;
    }

    @Override
    public Result updateGameInfo(GameInfo gameInfo) {
        Connection connection = SQLFactoryPlugin.getConnection();

        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

                String serializedGame = ObjectSerializer.serializeObject(gameInfo);

                stmt.executeUpdate("update games set game_state = '" + serializedGame +
                        "' where game_name = '" + gameInfo.getGameName().getName() + "';");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("update game info failed");
        }
        return null;
    }

    @Override
    public Result deleteGameInfo(GameInfo gameInfo) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = connection.createStatement();
            try {
                stmt.executeUpdate("delete from games where game_name = '" + gameInfo.getGameName().getName() + "';");
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("delete game info failed");
        }
        return null;
    }

}
