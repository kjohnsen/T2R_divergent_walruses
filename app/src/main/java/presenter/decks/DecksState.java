package presenter.decks;

import java.util.List;

import model.UIFacade;
import modelclasses.TrainCard;

public class DecksState {

    public String drawDestinationCards(DecksPresenter presenter) {
        onSwitchView(presenter);
        presenter.getView().drawDestinationCards();
        //don't return anything-- there won't be an error
        return null;
    }

    public String drawTrainCard(DecksPresenter presenter) {
        return UIFacade.getInstance().drawTrainCard();
    }

    public boolean isGameStart(DecksPresenter presenter) {
        return UIFacade.getInstance().isGameStart();
    }

    public void getFaceupCards(DecksPresenter presenter) {
        List<TrainCard> cards = UIFacade.getInstance().getFaceupCards();
        if (!cards.isEmpty()) {
            presenter.getView().replaceTrainCards(cards);
        }
    }
    
    public String selectTrainCard(DecksPresenter presenter, int index) {
        return UIFacade.getInstance().selectTrainCard(index);
    }
    
    public void onSwitchView(DecksPresenter presenter) {

    }
}
