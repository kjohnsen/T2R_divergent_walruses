package presenter.decks;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fragment.IDecksView;
import model.ClientModel;
import modelclasses.TrainCard;
import presenter.IDecksPresenter;

public class DecksPresenter implements IDecksPresenter, Observer {

    private IDecksView view;
    private DecksState state;

    public DecksPresenter(IDecksView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
        setState(DecksNoCardsDrawn.getInstance());
    }

    public IDecksView getView() {
        return view;
    }

    public void setState(DecksState newState) {
        state = newState;
    }

    @Override
    public boolean isGameStart() { return state.isGameStart(this); }

    @Override
    public void getFaceupCards() { state.getFaceupCards(this); }

    @Override
    public String selectTrainCard(int index) {
        return state.selectTrainCard(this, index); }

    @Override
    public String drawTrainCard() { return state.drawTrainCard(this); }

    @Override
    public String drawDestinationCards() { return state.drawDestinationCards(this); }

    @Override
    public void onSwitchView() { state.onSwitchView(this); }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof ArrayList) {
            ArrayList<Object> array = (ArrayList<Object>) o;
            if (array.size() == 5 && array.get(0) instanceof TrainCard) {
                ArrayList<TrainCard> cards = new ArrayList<>();
                for (Object object : array) {
                    cards.add((TrainCard) object);
                }
                view.replaceTrainCards(cards);
            }
        }
    }
}
