package presenter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fragment.IPlayerInfoView;
import model.ClientModel;
import model.UIFacade;
import modelclasses.DestinationCard;
import modelclasses.DestinationCardWrapper;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.TrainCard;

public class PlayerInfoPresenter implements IPlayerInfoPresenter, Observer {

    private IPlayerInfoView view;

    public PlayerInfoPresenter(IPlayerInfoView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
    }

    public void initialUpdate(){
        view.updateDestinationTickets(UIFacade.getInstance().getPlayerTickets());
        view.updateTrainCards(Player.getTrainCardQuantities(UIFacade.getInstance().getPlayerTrainCards()));
    }

    @Override
    public void onSwitchView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof ArrayList) {
            ArrayList<Object> array = (ArrayList<Object>) o;
            if (array.isEmpty()) {
                //do nothing-- this is an error with Travis
            } else if (array.get(0) instanceof TrainCard) {
                ArrayList<TrainCard> trainCards = UIFacade.getInstance().getPlayerTrainCards();
                view.updateTrainCards(Player.getTrainCardQuantities(trainCards));
            }
        } else if (o instanceof DestinationCardWrapper) {
            DestinationCardWrapper wrapper = (DestinationCardWrapper)o;
            if (!wrapper.isDeck()) {
                view.updateDestinationTickets(wrapper.getDestinationCards());
            }
        }
    }
}
