package presenter;

import model.UIFacade;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;

public class DecksNoCardsDrawn extends DecksState {

    private static DecksNoCardsDrawn instance = new DecksNoCardsDrawn();
    public static DecksNoCardsDrawn getInstance() { return instance; }
    private DecksNoCardsDrawn() {}
    
    public String drawDestinationCards(DecksPresenter presenter) {
        //presenter.setState(DecksWaiting.getInstance());
        return super.drawDestinationCards(presenter);
    }
    
    public String drawTrainCard(DecksPresenter presenter) {
        //presenter.setState(DecksOneCardDrawn.getInstance());
        return super.drawTrainCard(presenter);
    }
    
    public boolean isGameStart(DecksPresenter presenter) {
        return super.isGameStart(presenter);
    }
    
    public void getFaceupCards(DecksPresenter presenter) {
        super.getFaceupCards(presenter);
    }
    
    public String selectTrainCard(DecksPresenter presenter, int index) {
        TrainCard card = UIFacade.getInstance().getFaceupCards().get(index);
//        if (card.getColor().equals(TrainCardColor.WILD)) {
//            presenter.setState(DecksWaiting.getInstance());
//        } else {
//            presenter.setState(DecksOneCardDrawn.getInstance());
//        }
        return super.selectTrainCard(presenter, index);
    }
    
    public void onSwitchView(DecksPresenter presenter) {
        super.onSwitchView(presenter);
    }
}
