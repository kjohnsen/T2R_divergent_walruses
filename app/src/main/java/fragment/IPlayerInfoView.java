package fragment;

import java.util.List;
import java.util.Map;

import modelclasses.DestinationCard;
import modelclasses.Player;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;

public interface IPlayerInfoView {
    void updateTrainCards(Map<TrainCardColor, Integer> cards_amount);
    void updateDestinationTickets(List<DestinationCard> destinationCards);
}