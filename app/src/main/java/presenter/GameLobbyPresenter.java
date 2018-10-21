package presenter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import activity.IGameLobbyView;
import model.ClientModel;
import model.UIFacade;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;

public class GameLobbyPresenter implements IGameLobbyPresenter, Observer {

    private IGameLobbyView view;

    public GameLobbyPresenter(IGameLobbyView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public String chooseColor(String color) {
        return UIFacade.getInstance().claimColor(PlayerColor.valueOf(color));
    }

    @Override
    public String startGame() {
        view.startGame();
        return null;
    }

    @Override
    public void getGameLobbyInfo() {
        GameInfo gameInfo = UIFacade.getInstance().getCurrentGame();
        view.updatePlayerList(gameInfo.getPlayers());
        String username = UIFacade.getInstance().getUsername();
        view.updateAvailableColors(PlayerColor.getAvailableColors(gameInfo.getPlayers(), username));
        view.setStartGameEnabled(UIFacade.getInstance().currentGameReady());
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof GameInfo) {
            observable.deleteObserver(this);
            view.startGame();
        } else if (o instanceof ArrayList) {
            ArrayList<Object> array = (ArrayList<Object>) o;
            if (array.get(0) instanceof Player) {
                ArrayList<Player> players = new ArrayList<>();
                for (Object object : array) {
                    Player player = (Player) object;
                    players.add(player);
                }
                String username = UIFacade.getInstance().getUsername();
                ArrayList<String> colors = PlayerColor.getAvailableColors(players, username);
                view.updatePlayerList(players);
                view.updateAvailableColors(colors);
                view.setStartGameEnabled(UIFacade.getInstance().currentGameReady());
                if (ClientModel.getInstance().currentGameReady()) {
                    view.setStartGameEnabled(true);
                }
            }
        }
    }
}
