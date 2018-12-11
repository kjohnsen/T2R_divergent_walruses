package DaoFactory;

import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import org.bson.BsonBinary;
import org.bson.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import modelclasses.GameInfo;
import modelclasses.User;
import persistence.IUserDAO;
import persistence.Result;

public class MongoUserDao implements IUserDAO {
    private final MongoCollection<Document> userCollection = MongoFactoryPlugin.getDatabase().getCollection("User");

    @Override
    public Result createUser(User user) {
        Document document = new Document("username", user.getUsername())
                .append("password", user.getPassword())
                .append("authToken", user.getAuthToken());
        userCollection.insertOne(document);
        return null;
    }

    @Override
    public User readUser(String username) {
        Document userDoc = userCollection.find(Filters.eq("username", username)).first();
        if(userDoc != null) {
            String password = (String)userDoc.get("password");
            String authToken = (String)userDoc.get("authToken");
            User user = new User(username, password);
            user.setAuthToken(authToken);
            return user;
        }

        return null;
    }

    @Override
    public ArrayList<User> readAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        FindIterable<Document> userDocs = userCollection.find();
        for (Document userDoc : userDocs) {
            String username = (String)userDoc.get("username");
            String password = (String)userDoc.get("password");
            String authToken = (String)userDoc.get("authToken");
            User user = new User(username, password);
            user.setAuthToken(authToken);
            users.add(user);
        }

        return users;
    }

}