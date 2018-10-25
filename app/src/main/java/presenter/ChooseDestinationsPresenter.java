package presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fragment.IChooseDestinationsView;
import model.ClientModel;
import model.UIFacade;
import modelclasses.DestinationCard;

public class ChooseDestinationsPresenter implements IChooseDestinationsPresenter, Observer {

    private IChooseDestinationsView view;
    private ArrayList<DestinationCard> rejections;

    public ChooseDestinationsPresenter(IChooseDestinationsView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
        rejections = new ArrayList<>();
    }

    @Override
    public String getDestinationCards() {
        return UIFacade.getInstance().drawDestinationCards();
    }

    @Override
    public void setCardSelected(DestinationCard ticket, boolean selected) {
        if (selected) {
            rejections.remove(ticket);
        } else {
            rejections.add(ticket);
        }
        checkButtonEnable();
    }

    private void checkButtonEnable() {
        if (UIFacade.getInstance().isGameStart()) {
            view.setSelectEnabled(rejections.size() < 2);
        } else {
            view.setSelectEnabled(rejections.size() < 3);
        }
    }

    @Override
    public String selectCards() {
        return UIFacade.getInstance().selectDestinationCards(rejections);
    }

    @Override
    public boolean isGameStart() {
        return UIFacade.getInstance().isGameStart();
    }

    @Override
    public List<DestinationCard> getPlayerCards() {
        return UIFacade.getInstance().getStartDestinationCards();
    }

    @Override
    public void onSwitchView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof List) {
            List<Object> array = (ArrayList<Object>) o;
            if (array.get(0) instanceof DestinationCard) {
                List<DestinationCard> tickets = new ArrayList<>();
                for (Object object : array) {
                    tickets.add((DestinationCard) object);
                }
                view.displayTickets(tickets);
            }
        }
    }
}
