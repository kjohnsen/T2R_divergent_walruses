package presenter;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.ClientModel;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;
import presenter.decks.DecksPresenter;

public class TestDecksPresenter {
    private static DecksPresenter presenter;
    private static DecksMock view = new DecksMock();
    private static TrainCard card0 = new TrainCard(TrainCardColor.RED);
    private static TrainCard card1 = new TrainCard(TrainCardColor.BLACK);
    private static TrainCard card2 = new TrainCard(TrainCardColor.WILD);
    private static TrainCard card3 = new TrainCard(TrainCardColor.YELLOW);
    private static TrainCard card4 = new TrainCard(TrainCardColor.ORANGE);
    private static TrainCard card5 = new TrainCard(TrainCardColor.PURPLE);
    private static TrainCard card6 = new TrainCard(TrainCardColor.WHITE);
    private static TrainCard card7 = new TrainCard(TrainCardColor.BLUE);
    private static TrainCard card8 = new TrainCard(TrainCardColor.GREEN);
    private static TrainCard card9 = new TrainCard(TrainCardColor.WILD);
    private static List<TrainCard> firstCards = new ArrayList<>();
    private static List<TrainCard> secondCards = new ArrayList<>();

    @BeforeClass
    public static void prep() {
        firstCards.add(card0);
        firstCards.add(card1);
        firstCards.add(card2);
        firstCards.add(card3);
        firstCards.add(card4);
        secondCards.add(card5);
        secondCards.add(card6);
        secondCards.add(card7);
        secondCards.add(card8);
        secondCards.add(card9);
        view.replaceTrainCards(firstCards);
        presenter = new DecksPresenter(view);
    }

    @Test
    public void testReplaceCards() {
        assertEquals(view.getTrainCards().size(), 5);
        assertEquals(view.getTrainCards().get(4), card4);
        ClientModel.getInstance().notifyObservers(secondCards);
        assertEquals(view.getTrainCards().size(), 5);
        assertEquals(view.getTrainCards().get(0), card5);
        assertEquals(view.getTrainCards().get(2), card7);
        ClientModel.getInstance().notifyObservers(firstCards);
        assertEquals(view.getTrainCards().get(0), card0);
        assertEquals(view.getTrainCards().get(3), card3);
    }
}
