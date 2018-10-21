package fragment;

import java.util.ArrayList;

import modelclasses.DestinationCard;
import modelclasses.TrainCard;

public interface IDecksView {
    void replaceTrainCards(ArrayList<TrainCard> cards);
    void drawDestinationCards();
}
