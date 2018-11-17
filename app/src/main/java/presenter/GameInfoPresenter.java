package presenter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fragment.IGameInfoView;
import model.ClientModel;
import model.UIFacade;
import modelclasses.DestinationCard;
import modelclasses.DestinationCardWrapper;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.TrainCard;
import modelclasses.TrainCardWrapper;

public class GameInfoPresenter implements IGameInfoPresenter, Observer {

    private IGameInfoView view;
    private ArrayList<Player> playersInfo;

    public GameInfoPresenter(IGameInfoView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
    }

    public void initialUpdate(){
        int trainDeckSize = UIFacade.getInstance().getTrainDeckSize();
        int destDeckSize = UIFacade.getInstance().getDestDeckSize();
        playersInfo = UIFacade.getInstance().getCurrentGame().getPlayers();


        view.updateTrainDeckInfo(trainDeckSize);
        view.updateDestDeckInfo(destDeckSize);
        view.updatePlayerInfo(playersInfo);
        view.updateCurrentPlayer(UIFacade.getInstance().getCurrentGame().getCurrentPlayer());

    }

    @Override
    public void onSwitchView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {

        //TODO: change to username... and query the model for the player info
        if (o instanceof Player) {
            Player player = (Player) o;
            for (int i = 0; i < playersInfo.size(); i++) {
                if (playersInfo.get(i).getUsername().equals(player.getUsername())) {
                    playersInfo.set(i, player);
                }
            }
            view.updatePlayerInfo(playersInfo);
            view.updateCurrentPlayer(player);

        } else if (o instanceof DestinationCardWrapper) {
            DestinationCardWrapper wrapper = (DestinationCardWrapper)o;
            if(wrapper.getDeckType() == DestinationCardWrapper.DeckType.DrawDeck)
                view.updateDestDeckInfo(wrapper.getDestinationCards().size());

        } else if (o instanceof TrainCardWrapper) {
            TrainCardWrapper wrapper = (TrainCardWrapper) o;
            if(wrapper.getDeckType() == TrainCardWrapper.DeckType.DrawDeck)
                view.updateTrainDeckInfo(wrapper.getCards().size());
        }
    }
}
