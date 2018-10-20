package presenter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fragment.IChooseDestinationsView;
import model.ClientModel;
import model.UIFacade;
import modelclasses.DestinationCard;

public class ChooseDestinationsPresenter implements IChooseDestinationsPresenter, Observer {

    private IChooseDestinationsView view;
    private ArrayList<DestinationCard> selections;

    public ChooseDestinationsPresenter(IChooseDestinationsView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
        selections = new ArrayList<>();
    }

    @Override
    public String getDestinationCards() {
        return UIFacade.getInstance().drawDestinationCards();
    }

    @Override
    public void setCardSelected(DestinationCard ticket, boolean selected) {
        if (selected) {
            selections.add(ticket);
        } else {
            selections.remove(ticket);
        }
        checkButtonEnable();
    }

    private void checkButtonEnable() {
        if (UIFacade.getInstance().firstTickets()) {
            view.setSelectEnabled(selections.size() > 1);
        } else {
            view.setSelectEnabled(selections.size() > 0);
        }
    }

    @Override
    public String selectCards() {
        return UIFacade.getInstance().selectDestinationCards(selections);
    }

    @Override
    public void onSwitchView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof ArrayList) {
            ArrayList<Object> array = (ArrayList<Object>) o;
            if (array.get(0) instanceof DestinationCard) {
                ArrayList<DestinationCard> tickets = new ArrayList<>();
                for (Object object : array) {
                    tickets.add((DestinationCard) object);
                }
                view.displayTickets(tickets);
            }
        }
    }
}
