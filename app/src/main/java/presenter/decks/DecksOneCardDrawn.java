package presenter.decks;

import model.UIFacade;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;

public class DecksOneCardDrawn extends DecksState {

    private static DecksOneCardDrawn instance = new DecksOneCardDrawn();
    public static DecksOneCardDrawn getInstance() { return instance; }
    private DecksOneCardDrawn() {}
    
    public String drawDestinationCards(DecksPresenter presenter) {
        return "You've already drawn a train card-- draw another one to finish your turn";
    }

    @Override
    public String selectTrainCard(DecksPresenter presenter, int index) {
        TrainCard card = UIFacade.getInstance().getFaceupCards().get(index);
        if (card.getColor().equals(TrainCardColor.WILD)) {
            return "You've already drawn one card-- you can't pick a wild";
        } else {
            presenter.setState(DecksWaiting.getInstance());
            return super.selectTrainCard(presenter, index);
        }
    }
}