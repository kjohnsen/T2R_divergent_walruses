package presenter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fragment.IPlayerInfoView;
import model.ClientModel;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.TrainCard;

public class PlayerInfoPresenter implements IPlayerInfoPresenter, Observer {

    private IPlayerInfoView view;

    public PlayerInfoPresenter(IPlayerInfoView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);

        ClientModel.getInstance().notifyObservers(ClientModel.getInstance().getPlayerTickets());
        ClientModel.getInstance().notifyObservers(ClientModel.getInstance().getPlayerTrainCards());

    }

    @Override
    public void onSwitchView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {

        if (o instanceof ArrayList) {
            ArrayList<Object> array = (ArrayList<Object>) o;

            if (array.get(0) instanceof TrainCard) {
                ArrayList<TrainCard> trainCards = new ArrayList<>();
                for (Object object : array) {
                    trainCards.add((TrainCard) object);
                }
                view.updateTrainCards(Player.getTrainCardQuantities(trainCards));

            } else if (array.get(0) instanceof DestinationCard) {
                ArrayList<DestinationCard> destCards = new ArrayList<>();
                for (Object object : array) {
                    destCards.add((DestinationCard) object);
                }
                view.updateDestinationTickets(destCards);
            }

        }

    }

}
