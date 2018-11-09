package server;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;

import data.Command;
import data.CommandManager;
import model.ServerModel;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import modelclasses.User;
import results.Results;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGameSetup {

    @Before
    public void setUp() {
        // make three test users & give them authTokens
        ServerModel.getInstance().getUsers().put("u1", new User("u1", "password"));
        ServerModel.getInstance().getUsers().put("u2", new User("u2", "password"));
        ServerModel.getInstance().getUsers().put("u3", new User("u3", "password"));
        ServerModel.getInstance().getUsers().put("a", new User("a", "password"));
        ServerModel.getInstance().getUsers().put("b", new User("b", "password"));
        ServerModel.getInstance().getAuthTokens().put("a1", "u1");
        ServerModel.getInstance().getAuthTokens().put("a2", "u2");
        ServerModel.getInstance().getAuthTokens().put("a3", "u3");
        ServerModel.getInstance().getAuthTokens().put("a4", "a");
        ServerModel.getInstance().getAuthTokens().put("a5", "b");

        // make them players
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("u1", PlayerColor.GREEN));
        players.add(new Player("u2", PlayerColor.BLUE));
        players.add(new Player("u3", PlayerColor.MAGENTA));

        // put them in the CommandManager
        CommandManager.getInstance().addClient("u1");
        CommandManager.getInstance().addClient("u2");
        CommandManager.getInstance().addClient("u3");

        // put them in a game together
        GameName gameName = new GameName("my game");
        GameInfo game = new GameInfo(gameName, players, 3);
        ServerModel.getInstance().getGames().put(gameName, game);
    }

    @Test
    public void startGame() {
        GameName name = new GameName("my game");
        String clientAuthToken = "a1";

        Results results = ServerFacade.getInstance().startGame(name, clientAuthToken);

        assertTrue(results.getSuccess());

        GameInfo game = ServerModel.getInstance().getGameInfo(name);
        assertEquals(93, game.getTrainCardDeck().size());
        assertEquals(21, game.getDestCardDeck().size());

        // check player hands
        for (Player p : game.getPlayers()) {
            ArrayList<TrainCard> playerTrainCards = p.getTrainCards();
            assertEquals(4, playerTrainCards.size());

            ArrayList<DestinationCard> playerDestCards = p.getDestinationCards();
            assertEquals(0, playerDestCards.size());

            ArrayList<DestinationCard> preSelectionDestCards = p.getPreSelectionDestCards();
            assertEquals(3, preSelectionDestCards.size());
        }

        // check player commands
        assertEquals(1, results.getClientCommands().size());
        for (Player p : game.getPlayers()) {
            ArrayList<Command> userCommands = CommandManager.getInstance().getCommands(p.getUsername());
            if (p.getUsername().equals("u1")) {
                assertEquals(0, userCommands.size());
            }
            else {
                assertEquals(1, userCommands.size());
            }
        }
    }

    @Test
    public void selectDestinationCards(){
        GameName name = new GameName("dest card game");
        Player p1 = new Player("a", PlayerColor.BLACK);
        Player p2 = new Player("b", PlayerColor.YELLOW);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        GameInfo game = new GameInfo(name, players, 2);
        ServerModel.getInstance().getGames().put(name, game);

        ArrayList<DestinationCard> p1DestCards = new ArrayList<>();
        DestinationCard card1 = game.drawDestCard();
        DestinationCard card2 = game.drawDestCard();
        DestinationCard card3 = game.drawDestCard();
        p1DestCards.add(card1);
        p1DestCards.add(card2);
        p1DestCards.add(card3);
        p1.setDestinationCards(p1DestCards);

        ArrayList<DestinationCard> ticketsToReturn = new ArrayList<>();
        ticketsToReturn.add(card1);

        ServerFacade._selectDestinationCards(ticketsToReturn, name, "a4");
    }
}
