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
    private ArrayList<DestinationCard> selected;

    public ChooseDestinationsPresenter(IChooseDestinationsView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public String getDestinationCards() {
        return UIFacade.getInstance().drawDestinationCards();
    }

    @Override
    public String selectDestinationCards(ArrayList<Integer> indexes) {
        //TODO: Implement this function
        return null;
    }

    @Override
    public void update(Observable observable, Object o) {
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
