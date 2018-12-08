package DaoFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import persistence.IUserDAO;
import persistence.Result;

import modelclasses.User;

public class SQLUserDao implements IUserDAO {

    @Override
    public Result createUser(User user) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("insert into users (username, password) " +
                        "values ('" + user.getUsername() + "', '" + user.getPassword() + "');");
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("create user failed");
        }
        return null;
    }

    @Override
    public User readUser(String username) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("select * from users where username = '" + username + "'");
                while (rs.next()) {
                    String password = rs.getString("password");
                    return new User(username, password);
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
            System.out.println("read user failed");
        }
        return null;
    }

    @Override
    public ArrayList<User> readAllUsers() {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("select * from users");
                ArrayList<User> allUsers = new ArrayList<>();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    User user = new User(username, password);
                    allUsers.add(user);
                }
                return allUsers;
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("read all users failed");
        }
        return null;
    }

    /** Used for testing */
    public void delete(String username) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("delete from users where username = '" + username + "';");
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("delete user failed");
        }
    }

}
