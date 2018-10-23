package fragment;

import java.util.List;

import modelclasses.TrainCard;

public interface IDecksView {
    void replaceTrainCards(List<TrainCard> cards);
    void drawDestinationCards();
}
