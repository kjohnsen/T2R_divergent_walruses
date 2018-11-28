package presenter.decks;

import model.ClientModel;
import model.UIFacade;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;

public class DecksNoCardsDrawn extends DecksState {

    private static DecksNoCardsDrawn instance = new DecksNoCardsDrawn();
    public static DecksNoCardsDrawn getInstance() { return instance; }
    private DecksNoCardsDrawn() {}
    
    public String drawDestinationCards(DecksPresenter presenter) {
        if (UIFacade.getInstance().getDestDeckSize() != 0) {
            if (!isGameStart(presenter)) {
                presenter.setState(DecksWaiting.getInstance());
            } else if (!UIFacade.getInstance().isStartPlayer()) {
                presenter.setState(DecksWaiting.getInstance());
            }
            return super.drawDestinationCards(presenter);
        }
        return "no cards in destination deck";
    }
    
    public String drawTrainCard(DecksPresenter presenter) {
        String message = super.drawTrainCard(presenter);
        if (message != null) {
            return message;
        } else {
            presenter.setState(DecksOneCardDrawn.getInstance());
            return null;
        }
    }

    public String selectTrainCard(DecksPresenter presenter, int index) {
        TrainCard card = UIFacade.getInstance().getFaceupCards().get(index);
        String message = super.selectTrainCard(presenter, index);
        if (message != null) {
            return message;
        } else {
            if (card.getColor().equals(TrainCardColor.WILD)) {
                presenter.setState(DecksWaiting.getInstance());
            } else {
                presenter.setState(DecksOneCardDrawn.getInstance());
            }
            return null;
        }
    }
}
