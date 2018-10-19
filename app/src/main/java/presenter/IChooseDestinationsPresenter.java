package presenter;

import modelclasses.DestinationCard;

public interface IChooseDestinationsPresenter {
    String getDestinationCards();
    void setCardSelected(DestinationCard card, boolean selected);
}
