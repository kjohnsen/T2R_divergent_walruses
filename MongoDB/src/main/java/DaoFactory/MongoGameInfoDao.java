package DaoFactory;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.BsonBinary;
import org.bson.Document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelclasses.GameInfo;
import modelclasses.GameName;
import persistence.IGameInfoDAO;
import persistence.Result;

public class MongoGameInfoDao implements IGameInfoDAO {
    private final MongoCollection<Document> gameInfoCollection = MongoFactoryPlugin.getDatabase().getCollection("GameInfo");

    @Override
    public Result create(GameInfo gameInfo) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(gameInfo);
            byte[] gameInfoData = byteArrayOutputStream.toByteArray();
            BsonBinary bsonBinary = new BsonBinary(gameInfoData);
            Document gameInfoDoc = new Document("gameName", gameInfo.getGameName()).append("data", bsonBinary);
            gameInfoCollection.insertOne(gameInfoDoc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public GameInfo read(GameName gameName) {
        Document gameInfoDoc = gameInfoCollection.find(Filters.eq("gameName", gameName)).first();
        BsonBinary bsonBinary = (BsonBinary)gameInfoDoc.get("data");

        if(bsonBinary != null) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bsonBinary.getData());
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                return (GameInfo)objectInputStream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Result update(GameInfo gameInfo) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(gameInfo);
            byte[] gameInfoData = byteArrayOutputStream.toByteArray();
            BsonBinary bsonBinary = new BsonBinary(gameInfoData);
            gameInfoCollection.updateOne(Filters.eq("gameName", gameInfo.getGameName()), Updates.set("data", bsonBinary));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result delete(GameInfo gameInfo) {
        gameInfoCollection.deleteOne(Filters.eq("gameName", gameInfo.getGameName()));
        return null;
    }
}
