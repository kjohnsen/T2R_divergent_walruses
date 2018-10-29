package presenter;

import java.util.ArrayList;
import java.util.List;

import fragment.IDecksView;
import modelclasses.TrainCard;

public class DecksMock implements IDecksView {
    private List<TrainCard> trainCards;
    private boolean called;

    public DecksMock() {
        trainCards = new ArrayList<>();
        called = false;
    }

    public List<TrainCard> getTrainCards() { return trainCards; }

    public boolean getCalled() { return called; }

    @Override
    public void replaceTrainCards(List<TrainCard> cards) {
        trainCards = cards;
    }

    @Override
    public void drawDestinationCards() {
        called = true;
    }
}
