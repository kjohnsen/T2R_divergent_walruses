package DaoFactory;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import org.bson.BsonBinary;
import org.bson.Document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import data.Command;
import persistence.ICommandDAO;
import persistence.Result;
import sun.corba.OutputStreamFactory;

public class MongoCommandDao implements ICommandDAO {
    private final MongoCollection<Document> commandCollection = MongoFactoryPlugin.getDatabase().getCollection("Command");

    @Override
    public Result createCommand(Command command) {
        long numCommands = commandCollection.countDocuments();
        String commandID = Long.toString(numCommands + 1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(command);
            byte[] commandData = byteArrayOutputStream.toByteArray();
            BsonBinary bsonBinary = new BsonBinary(commandData);
            Document commandDoc = new Document("commandID", commandID).append("data", bsonBinary);
            commandCollection.insertOne(commandDoc);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Command readCommand(String commandID) {
        Document commandDoc = commandCollection.find(Filters.eq("commandID", commandID)).first();
        BsonBinary bsonBinary = (BsonBinary)commandDoc.get("data");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bsonBinary.getData());

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Command)objectInputStream.readObject();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Result deleteCommand(ArrayList<Command> commands) {
        commandCollection.deleteMany(new Document());
//        long numCommands = commandCollection.countDocuments();
//        for(int i = 0; i < numCommands; ++i) {
//            String commandID = Integer.toString(i + 1);
//            commandCollection.deleteOne(Filters.eq("commandID", commandID));
//        }
        return null;
    }
}
