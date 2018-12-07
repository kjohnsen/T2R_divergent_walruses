package DaoFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {

    public static void createCommandTable(Connection conn) {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("drop table if exists commands");
                stmt.executeUpdate("create table commands " +
                        "(command_id string primary key, " +
                        "game_name string not null, " +
                        "command string not null);");
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("createCommandTable failed");
        }
    }

    public static void createGameTable(Connection conn) {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("drop table if exists games");
                stmt.executeUpdate("create table games " +
                        "(game_name string primary key, " +
                        "game_state string not null);");
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("createGameTable failed");
        }
    }

    public static void createUserTable(Connection conn) {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("drop table if exists users");
                stmt.executeUpdate("create table users " +
                        "(username text primary key, " +
                        "password text not null);");
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("createUserTable failed");
        }
    }

}
