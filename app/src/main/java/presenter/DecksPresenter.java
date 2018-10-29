package presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fragment.IDecksView;
import model.ClientModel;
import model.UIFacade;
import modelclasses.TrainCard;

public class DecksPresenter implements IDecksPresenter, Observer {

    private IDecksView view;

    public DecksPresenter(IDecksView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
    }

    public IDecksView getView() {
        return view;
    }

    @Override
    public boolean isGameStart() { return UIFacade.getInstance().isGameStart(); }

    @Override
    public void getFaceupCards() {
        List<TrainCard> cards = UIFacade.getInstance().getFaceupCards();
        if (!cards.isEmpty()) {
            view.replaceTrainCards(cards);
        }
    }

    @Override
    public String selectTrainCard(int index) {
        return UIFacade.getInstance().selectTrainCard(index);
    }

    @Override
    public String drawTrainCard() {
        return UIFacade.getInstance().drawTrainCard();
    }

    @Override
    public String drawDestinationCards() {
        onSwitchView();
        view.drawDestinationCards();
        //this is in preparation for the stateful modification-- just ignore it for now
        return null;
    }

    @Override
    public void onSwitchView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof ArrayList) {
            ArrayList<Object> array = (ArrayList<Object>) o;
            if (array.get(0) instanceof TrainCard && array.size() == 5) {
                ArrayList<TrainCard> cards = new ArrayList<>();
                for (Object object : array) {
                    cards.add((TrainCard) object);
                }
                view.replaceTrainCards(cards);
            }
        }
    }
}
