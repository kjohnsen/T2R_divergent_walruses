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

    @Override
    public List<User> readAllUsers() {
        List<User> users = new ArrayList<>();
        FindIterable<Document> userDocs = userCollection.find();
        for (Document userDoc : userDocs) {
            BsonBinary bsonBinary = (BsonBinary)userDoc.get("data");

            if(bsonBinary != null) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bsonBinary.getData());
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    users.add((User)objectInputStream.readObject());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

        return users;
    }

}