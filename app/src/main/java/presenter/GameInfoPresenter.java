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
        if (o instanceof ArrayList) {
            ArrayList<Object> array = (ArrayList<Object>) o;
            if (!array.isEmpty() && array.get(0) instanceof Player) {
                ArrayList<Player> players = new ArrayList<>();
                for (Object object : array) {
                    players.add((Player) object);
                }
                view.updatePlayerInfo(players);
            }
        }
    }

}
