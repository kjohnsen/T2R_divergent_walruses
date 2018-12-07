package DaoFactory;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import org.bson.BsonBinary;
import org.bson.Document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;

import data.Command;
import modelclasses.GameName;
import persistence.ICommandDAO;
import persistence.Result;
import sun.corba.OutputStreamFactory;

public class MongoCommandDao implements ICommandDAO {
    private final MongoCollection<Document> commandCollection = MongoFactoryPlugin.getDatabase().getCollection("Command");

    @Override
    public Result createCommand(Command command, GameName gameName) {
        long numCommands = commandCollection.countDocuments();
        String commandID = Long.toString(numCommands + 1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(command);
            objectOutputStream.close();
            objectOutputStream.flush();
            byte[] commandData = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            byteArrayOutputStream.flush();
            BsonBinary bsonBinary = new BsonBinary(commandData);
            Document commandDoc = new Document("commandID", commandID)
                    .append("data", bsonBinary)
                    .append("gameName", gameName.getName());
            commandCollection.insertOne(commandDoc);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Command> readCommands(GameName gameName) {
        FindIterable<Document> foundCommands = commandCollection.find(Filters.eq("gameName", gameName.getName()));
        MongoCursor<Document> cursor = foundCommands.iterator();
        int commandID = 1;
        ArrayList<Command> commands = new ArrayList<>();

        while(cursor.hasNext()) {
            Document commandDoc = foundCommands.filter(Filters.eq("commandID", Integer.toString(commandID))).first();
            BsonBinary bsonBinary = (BsonBinary)commandDoc.get("commandID");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bsonBinary.getData());
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                commands.add((Command)objectInputStream.readObject());
            } catch(IOException e) {
                e.printStackTrace();
                cursor.close();
                return null;
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
                cursor.close();
                return null;
            }
            cursor.next();
            commandID++;
        }
        cursor.close();
        return commands;
    }

    @Override
    public ArrayList<Command> readAllCommands() {
        ArrayList<Command> commands = new ArrayList<>();

        FindIterable<Document> foundCommands = commandCollection.find();
        for (Document commandDoc : foundCommands) {
            BsonBinary bsonBinary = (BsonBinary)commandDoc.get("data");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bsonBinary.getData());

            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                commands.add((Command)objectInputStream.readObject());
            } catch(IOException e) {
                e.printStackTrace();
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return commands;
    }

    @Override
    public Result deleteCommand(GameName gameName) {
        commandCollection.deleteMany(Filters.eq("gameName", gameName.getName()));
//        long numCommands = commandCollection.countDocuments();
//        for(int i = 0; i < numCommands; ++i) {
//            String commandID = Integer.toString(i + 1);
//            commandCollection.deleteOne(Filters.eq("commandID", commandID));
//        }
        return null;
    }
}
