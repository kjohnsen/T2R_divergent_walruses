package DaoFactory;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;

import data.Command;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.User;
import persistence.ICommandDAO;
import persistence.IGameInfoDAO;
import persistence.IPersistencePluginFactory;
import persistence.IUserDAO;

public class MongoFactoryPlugin implements IPersistencePluginFactory {
    private IUserDAO userDAO = new MongoUserDao();
    private IGameInfoDAO gameInfoDAO = new MongoGameInfoDao();
    private ICommandDAO commandDAO = new MongoCommandDao();
    private static final MongoClient mongoClient = new MongoClient();
    private static final MongoDatabase database = mongoClient.getDatabase("TTRDatabase");

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    @Override
    public void openConnection() {}

    @Override
    public void closeConnection() {}

    @Override
    public void clearDB() {
        database.getCollection("User").deleteMany(new Document());
        database.getCollection("Command").deleteMany(new Document());
        database.getCollection("GameInfo").deleteMany(new Document());
    }

    @Override
    public void startTransaction() {}

    @Override
    public void endTransaction() {}

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

    public static void main(String[] args) {
        MongoFactoryPlugin mongoFactoryPlugin = new MongoFactoryPlugin();
        mongoFactoryPlugin.clearDB();
    }

}
