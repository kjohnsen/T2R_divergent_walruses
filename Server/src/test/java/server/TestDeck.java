package server;

import static org.junit.Assert.* ;

import org.junit.Before;
import org.junit.Test;

import model.ServerModel;
import modelclasses.TrainCard;

public class TestDeck {

    @Before
    public void initializeDeck() {
        ServerModel.getInstance().initializeTrainCardDeck();
    }

    @Test
    public void drawRandomCards() {
        assertEquals(110, ServerModel.getInstance().getTrainCardDeck().size());

        TrainCard drawnCard = ServerModel.getInstance().drawTrainCard();
        assertEquals(109, ServerModel.getInstance().getTrainCardDeck().size());

        for (int i = 0; i < 109; i++) {
            ServerModel.getInstance().drawTrainCard();
        }
        assertEquals(0, ServerModel.getInstance().getTrainCardDeck().size());

        TrainCard nullCard = ServerModel.getInstance().drawTrainCard();
        assertNull(nullCard);
    }

}
