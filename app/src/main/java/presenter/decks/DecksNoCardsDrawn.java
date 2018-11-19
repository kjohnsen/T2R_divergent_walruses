package presenter.decks;

import model.UIFacade;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;

public class DecksNoCardsDrawn extends DecksState {

    private static DecksNoCardsDrawn instance = new DecksNoCardsDrawn();
    public static DecksNoCardsDrawn getInstance() { return instance; }
    private DecksNoCardsDrawn() {}
    
    public String drawDestinationCards(DecksPresenter presenter) {
        if (!isGameStart(presenter)) {
            presenter.setState(DecksWaiting.getInstance());
        } else if (!UIFacade.getInstance().isStartPlayer()) {
            presenter.setState(DecksWaiting.getInstance());
        }
        return super.drawDestinationCards(presenter);
    }
    
    public String drawTrainCard(DecksPresenter presenter) {
        presenter.setState(DecksOneCardDrawn.getInstance());
        return super.drawTrainCard(presenter);
    }

    public String selectTrainCard(DecksPresenter presenter, int index) {
        TrainCard card = UIFacade.getInstance().getFaceupCards().get(index);
        if (card.getColor().equals(TrainCardColor.WILD)) {
            presenter.setState(DecksWaiting.getInstance());
        } else {
            presenter.setState(DecksOneCardDrawn.getInstance());
        }
        return super.selectTrainCard(presenter, index);
    }
}
