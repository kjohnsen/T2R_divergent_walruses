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

public class GameInfoPresenter implements IGameInfoPresenter, Observer {

    private IGameInfoView view;
    private ArrayList<Player> playersInfo;

    public GameInfoPresenter(IGameInfoView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
    }

    public void initialUpdate(){
        playersInfo = ClientModel.getInstance().getCurrentGame().getPlayers();
        ClientModel.getInstance().notifyObservers(playersInfo);
    }

    @Override
    public void onSwitchView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof Player) {
            Player player = (Player) o;
            for (int i = 0; i < playersInfo.size(); i++) {
                if (playersInfo.get(i).getUsername().equals(player.getUsername())) {
                    playersInfo.set(i, player);
                }
            }
            view.updatePlayerInfo(playersInfo);
            view.updateCurrentPlayer(player);
        } else if (o instanceof ArrayList) {
            ArrayList<Object> objects = (ArrayList<Object>) o;
            if (objects.size() == 0) {
                //to catch Travis errors
            } else {
                int destDeckSize = UIFacade.getInstance().getCurrentGame().getDestCardDeck().size();
                int trainDeckSize = UIFacade.getInstance().getCurrentGame().getTrainCardDeck().size();
                view.updateDecksInfo(destDeckSize, trainDeckSize);
            }
        } else if (o instanceof DestinationCardWrapper) {
            DestinationCardWrapper wrapper = (DestinationCardWrapper)o;
            if(wrapper.isDeck()){
                int destDeckSize = UIFacade.getInstance().getCurrentGame().getDestCardDeck().size();
                int trainDeckSize = UIFacade.getInstance().getCurrentGame().getTrainCardDeck().size();
                view.updateDecksInfo(destDeckSize, trainDeckSize);
            }
        }
    }
}
