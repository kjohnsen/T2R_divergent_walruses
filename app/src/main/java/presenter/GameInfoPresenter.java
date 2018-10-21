package presenter;

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

    @Override
    public void onSwitchView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {

        if(o instanceof GameInfo){
            GameInfo gameInfo = (GameInfo)o;
            view.updatePlayerInfo(gameInfo.getPlayers());
        }

    }

}
