package presenter;

import java.util.List;

import modelclasses.DestinationCard;

public interface IChooseDestinationsPresenter {
    String getDestinationCards();
    void setCardSelected(DestinationCard card, boolean selected);
    String selectCards();
    void onSwitchView();
    boolean isGameStart();
    List<DestinationCard> getPlayerCards();
}
