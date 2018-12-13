package persistence;

import java.util.ArrayList;

import modelclasses.User;

public interface IUserDAO {
    Result createUser(User user);
    ArrayList<User> readAllUsers();
    User readUser(String username);
}