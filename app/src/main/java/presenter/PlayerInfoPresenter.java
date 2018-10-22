package presenter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fragment.IPlayerInfoView;
import model.ClientModel;
import modelclasses.GameInfo;
import modelclasses.Player;

public class PlayerInfoPresenter implements IPlayerInfoPresenter, Observer {

    private IPlayerInfoView view;

    public PlayerInfoPresenter(IPlayerInfoView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void onSwitchView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {

        //i'm only interested in the player playing.. not everyone else!
        //getting the current game from the client model
        if(o instanceof GameInfo){
            GameInfo gameInfo = (GameInfo)o;

            //get the current player from the game info
            Player currentPlayer = gameInfo.getPlayer(ClientModel.getInstance().getCurrentUser().getUsername());

            //we don't know if the player's destination tickets changed
            //or if his train cards changed... update them all!
            view.updateTrainCards(currentPlayer.getTrainCardQuantities());
            view.updateDestinationTickets(currentPlayer.getDestinationCards());

        }

    }

}
