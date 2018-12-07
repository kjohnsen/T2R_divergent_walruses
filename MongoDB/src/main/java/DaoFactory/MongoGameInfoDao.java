package DaoFactory;

import com.mongodb.client.FindIterable;
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
import java.util.ArrayList;
import java.util.List;

import modelclasses.GameInfo;
import modelclasses.GameName;
import persistence.IGameInfoDAO;
import persistence.Result;

public class MongoGameInfoDao implements IGameInfoDAO {
    private final MongoCollection<Document> gameInfoCollection = MongoFactoryPlugin.getDatabase().getCollection("GameInfo");

    @Override
    public Result createGameInfo(GameInfo gameInfo) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(gameInfo);
            objectOutputStream.close();
            objectOutputStream.flush();
            byte[] gameInfoData = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            byteArrayOutputStream.flush();
            BsonBinary bsonBinary = new BsonBinary(gameInfoData);
            Document gameInfoDoc = new Document("gameName", gameInfo.getGameName().getName()).append("data", bsonBinary);
            gameInfoCollection.insertOne(gameInfoDoc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public GameInfo readGameInfo(GameName gameName) {
        Document gameInfoDoc = gameInfoCollection.find(Filters.eq("gameName", gameName.getName())).first();
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
    public List<GameInfo> readAllGameInfos() {
        FindIterable<Document> gameInfoDocs = gameInfoCollection.find();
        List<GameInfo> games = new ArrayList<>();
        for (Document gameInfoDoc : gameInfoDocs) {

            BsonBinary bsonBinary = (BsonBinary)gameInfoDoc.get("data");

            if(bsonBinary != null) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bsonBinary.getData());
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    games.add((GameInfo)objectInputStream.readObject());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return games;
    }

    @Override
    public Result updateGameInfo(GameInfo gameInfo) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(gameInfo);
            objectOutputStream.close();
            objectOutputStream.flush();
            byte[] gameInfoData = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            byteArrayOutputStream.flush();
            BsonBinary bsonBinary = new BsonBinary(gameInfoData);
            gameInfoCollection.updateOne(Filters.eq("gameName", gameInfo.getGameName()), Updates.set("data", bsonBinary));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result deleteGameInfo(GameInfo gameInfo) {
        gameInfoCollection.deleteOne(Filters.eq("gameName", gameInfo.getGameName().getName()));
        return null;
    }
}
