package data;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.User;

import static org.junit.Assert.*;

/**
 * Created by kylej13 on 10/8/18.
 */
public class SerializerTest {
    Serializer serializer = new Serializer();

    @Test
    public void encodeAndDecodeGameInfo() throws Exception {
        GameInfo gameInfo = new GameInfo(new GameName("game"), new ArrayList<Player>(), 4);

        Player nephi = new Player("Nephi", PlayerColor.BLUE);
        gameInfo.addPlayer(nephi);
        Player laman = new Player("Laman", PlayerColor.YELLOW);
        gameInfo.addPlayer(laman);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        serializer.encodeToStream(gameInfo, bos);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        GameInfo deserializedGameInfo = (GameInfo) serializer.decodeFromStream(bis, null);
        assertEquals(gameInfo, deserializedGameInfo);

    }

    @Test
    public void encodeAndDecodeCommand() throws Exception {
        String[] paramTypes = {"User, String, ArrayList<GameInfo>"};
        User user = new User("username", "password");
        ArrayList<Object> games = new ArrayList<>();

        Player nephi = new Player("Nephi", PlayerColor.BLUE);
        Player laman = new Player("Laman", PlayerColor.YELLOW);
        GameInfo game1 = new GameInfo(new GameName("game1"), new ArrayList<Player>(), 4);
        game1.addPlayer(nephi);
        game1.addPlayer(laman);

        GameInfo game2 = new GameInfo(new GameName("game2"), new ArrayList<Player>(), 3);
        game2.addPlayer(laman);

        games.add(game1);
        games.add(game2);

        Object[] paramValues = {user, "auth", games};

        Command command = new Command("CommandFacade", "registerUser", paramTypes, paramValues);
        //User user, String authToken, ArrayList<GameInfo> gameInfos

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        serializer.encodeToStream(command, bos);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        Command deserCommand = (Command) serializer.decodeFromStream(bis, null);
        assertEquals(command, deserCommand);

    }
}