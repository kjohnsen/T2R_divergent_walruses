package presenter;

import java.util.ArrayList;
import java.util.List;

import fragment.IChooseDestinationsView;
import modelclasses.DestinationCard;

public class ChooseDestinationsMock implements IChooseDestinationsView {
    private boolean select;

    public ChooseDestinationsMock() {
        select = false;
    }

    public boolean getSelect() { return select; }

    @Override
    public void setSelectEnabled(boolean enabled) {
        select = enabled;
    }

    @Override
    public void displayTickets(List<DestinationCard> tickets) {
        //just here for mocking purposes
    }
}
