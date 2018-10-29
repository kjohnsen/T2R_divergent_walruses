package fragment;

import java.util.List;

import modelclasses.DestinationCard;

public interface IChooseDestinationsView {
    void displayTickets(List<DestinationCard> tickets);
    void setSelectEnabled(boolean enabled);
}
