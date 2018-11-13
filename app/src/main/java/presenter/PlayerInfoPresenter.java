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
import modelclasses.TrainCardWrapper;

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

        if(o instanceof TrainCardWrapper) {
            TrainCardWrapper wrapper = (TrainCardWrapper)o;
            if(wrapper.getDeckType() == TrainCardWrapper.DeckType.PlayerCards)
                view.updateTrainCards(Player.getTrainCardQuantities(wrapper.getCards()));

        } else if (o instanceof DestinationCardWrapper) {
            DestinationCardWrapper wrapper = (DestinationCardWrapper)o;
            if(wrapper.getDeckType() == DestinationCardWrapper.DeckType.PlayerTickets){
                view.updateDestinationTickets(wrapper.getDestinationCards());
            }
        }
    }
}
