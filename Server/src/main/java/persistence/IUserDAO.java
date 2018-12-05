package persistence;

import modelclasses.User;

public interface IUserDAO {
    public Result create(User user);
    public User read(String username);
}