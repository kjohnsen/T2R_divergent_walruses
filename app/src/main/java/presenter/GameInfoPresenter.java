package presenter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fragment.IGameInfoView;
import model.ClientModel;
import modelclasses.GameInfo;
import modelclasses.Player;

public class GameInfoPresenter implements IGameInfoPresenter, Observer {

    private IGameInfoView view;

    public GameInfoPresenter(IGameInfoView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
    }

    public void initialUpdate(){
        ClientModel.getInstance().notifyObservers(ClientModel.getInstance().getCurrentGame().getPlayers());
    }

    @Override
    public void onSwitchView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof GameInfo) {
            GameInfo g = (GameInfo) o;
            view.updatePlayerInfo(g.getPlayers());
            view.updateDecksInfo(g.getDestCardDeck().size(), g.getTrainCardDeck().size());
            view.updateCurrentPlayer(g.getCurrentPlayer());
        }
    }
}
