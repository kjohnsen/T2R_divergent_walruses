package DaoFactory;

import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import modelclasses.User;
import persistence.IUserDAO;
import persistence.Result;

public class MongoUserDao implements IUserDAO {
    private final MongoCollection<Document> userCollection = MongoFactoryPlugin.getDatabase().getCollection("User");

    @Override
    public Result createUser(User user) {
        Document document = new Document("username", user.getUsername()).append("password", user.getPassword());
        userCollection.insertOne(document);
        return null;
    }

    @Override
    public User readUser(String username) {
        Document userDoc = userCollection.find(Filters.eq("username", username)).first();
        if(userDoc != null) {
            String password = (String)userDoc.get("password");
            return new User(username, password);
        }

        return null;
    }
}