package server;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.CommandManager;
import model.ServerModel;
import modelclasses.ChatMessage;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;

import static org.junit.Assert.*;

public class TestClientProxy {
    @Test
    public void testTest() {
        assertEquals(4, 2+2);
//        assertTrue(false);
    }

    @Test
    public void addChatMessage() {
        GameName gameName = new GameName("game");
        Player player1 = new Player("User");
        Player player2 = new Player("Not User");
        Player player3 = new Player("Other User");
        Player player4 = new Player("Fake User");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        GameInfo gameInfo = new GameInfo(gameName, players, 4);
        Map<GameName, GameInfo> games = new HashMap<>();
        games.put(gameName, gameInfo);
        ServerModel.getInstance().setGames(games);
        CommandManager.getInstance().addClient(player1.getUsername());
        CommandManager.getInstance().addClient(player2.getUsername());
        CommandManager.getInstance().addClient(player3.getUsername());
        CommandManager.getInstance().addClient(player4.getUsername());


        ChatMessage chatMessage = new ChatMessage("User", "New Chat");
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.addChatMessage(gameName, chatMessage);

        assertTrue(CommandManager.getInstance().getCommands(player2.getUsername()).size() == 1);
        assertTrue(CommandManager.getInstance().getCommands(player3.getUsername()).size() == 1);
        assertTrue(CommandManager.getInstance().getCommands(player4.getUsername()).size() == 1);
        assertTrue(CommandManager.getInstance().getCommands(player1.getUsername()).size() == 0);
    }
}
