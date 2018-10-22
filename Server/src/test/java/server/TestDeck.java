package server;

import static org.junit.Assert.* ;

import org.junit.BeforeClass;
import org.junit.Test;

import model.ServerModel;
import modelclasses.DestinationCard;
import modelclasses.TrainCard;
import modelclasses.GameName;
import modelclasses.GameInfo;

public class TestDeck {

    @BeforeClass
    public static void initializeDeck() {
        GameName gameName = new GameName("new game");
        GameInfo game = new GameInfo(gameName, null, 3);
        ServerModel.getInstance().getGames().put(gameName, game);
        game.initializeTrainCardDeck();
        game.initializeDestCardDeck();
    }

    @Test
    public void drawRandomTrainCards() {
        GameName gameName = new GameName("new game");
        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        assertEquals(110, game.getTrainCardDeck().size());

        TrainCard drawnCard = game.drawTrainCard();
        assertEquals(109, game.getTrainCardDeck().size());

        for (int i = 0; i < 109; i++) {
            game.drawTrainCard();
        }
        assertEquals(0, game.getTrainCardDeck().size());

        TrainCard nullCard = game.drawTrainCard();
        assertNull(nullCard);
    }

    @Test
    public void drawRandomDestCards() {
        GameName gameName = new GameName("new game");
        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        assertEquals(30, game.getDestCardDeck().size());

        DestinationCard drawnCard = game.drawDestCard();
        assertEquals(29, game.getDestCardDeck().size());

        for (int i = 0; i < 29; i++) {
            game.drawDestCard();
        }
        assertEquals(0, game.getDestCardDeck().size());

        DestinationCard nullCard = game.drawDestCard();
        assertNull(nullCard);
    }

}
