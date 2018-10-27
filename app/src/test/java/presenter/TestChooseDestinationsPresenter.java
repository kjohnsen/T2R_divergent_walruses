package presenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import model.UIFacade;
import modelclasses.Atlas;
import modelclasses.DestinationCard;

public class TestChooseDestinationsPresenter {
    private static ChooseDestinationsPresenter presenter;
    private static ChooseDestinationsMock view = new ChooseDestinationsMock();
    private static UIFacade uiFacade = UIFacade.getInstance();
    private static DestinationCard card1 = new DestinationCard(0, Atlas.WINNIPEG, Atlas.SANTA_FE);
    private static DestinationCard card2 = new DestinationCard(5, Atlas.WASHINGTON, Atlas.CHARLESTON);
    private static DestinationCard card3 = new DestinationCard(4, Atlas.DENVER, Atlas.WASHINGTON);
    private static ArrayList<DestinationCard> rejections = new ArrayList<>();

    @BeforeClass
    public static void prep() {
        presenter = new ChooseDestinationsPresenter(view);
    }

    @Before
    public void prepOther() {
        rejections = new ArrayList<>();
    }

    @Test
    public void checkRejectionsStart() {
        uiFacade.setGameStart(true);
        presenter.setCardSelected(card1, false);
        presenter.setCardSelected(card3, false);
        assertTrue(!view.getSelect());
        presenter.setCardSelected(card1, true);
        assertTrue(view.getSelect());
        presenter.setCardSelected(card2, false);
        assertTrue(!view.getSelect());
        presenter.setCardSelected(card3, true);
        presenter.setCardSelected(card2, true);
        assertTrue(view.getSelect());
    }

    @Test
    public void checkRejectionsNotStart() {
        uiFacade.setGameStart(false);
        presenter.setCardSelected(card1, false);
        presenter.setCardSelected(card2, false);
        presenter.setCardSelected(card3, false);
        assertTrue(!view.getSelect());
        presenter.setCardSelected(card1, true);
        assertTrue(view.getSelect());
        presenter.setCardSelected(card3, true);
        presenter.setCardSelected(card2, true);
        assertTrue(view.getSelect());
    }
}
