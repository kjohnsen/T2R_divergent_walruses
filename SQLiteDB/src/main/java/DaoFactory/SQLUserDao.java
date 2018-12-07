package DaoFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import persistence.IUserDAO;
import persistence.Result;

import modelclasses.User;

public class SQLUserDao implements IUserDAO {

    @Override
    public Result create(User user) {
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
    public User read(String username) {
        Connection connection = SQLFactoryPlugin.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("select * from users where username = '" + username + "'");
                while (rs.next()) {
                    String password = rs.getString("password");

                    User user = new User(username, password);
                    return user;
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
