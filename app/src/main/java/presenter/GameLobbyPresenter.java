package presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import activity.IGameLobbyActivity;
import model.ClientModel;
import model.UIFacade;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.PlayerColor;

public class GameLobbyPresenter implements IGameLobbyPresenter, Observer {

    private IGameLobbyActivity activity;
    private ArrayList<String> availableColors;

    public GameLobbyPresenter(IGameLobbyActivity activity) {
        this.activity = activity;
        availableColors = PlayerColor.getColors();
        ClientModel.getInstance().addObserver(this);
    }

    @Override
<<<<<<< HEAD
    public void chooseColor(String color, GameName gameName) {
        UIFacade.getInstance().claimColor(PlayerColor.valueOf(color), gameName);
=======
    public void chooseColor(String color) {
        String message = UIFacade.getInstance().claimColor(PlayerColor.valueOf(color));
        if (message != null) {
            activity.displayErrorMessage(message);
        }
>>>>>>> ea4d6f8ed7acd49b349f645d2b21171ce74ff619
    }

    @Override
    public void startGame() {
        String message = UIFacade.getInstance().startGame();
        if (message != null) {
            activity.displayErrorMessage(message);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof GameInfo) {
            observable.deleteObserver(this);
            activity.startGame();
        } else {
            ArrayList<Object> array = (ArrayList<Object>) o;
            if (array.get(0) instanceof Player) {
                ArrayList<Player> players = new ArrayList<>();
                for (Object object : array) {
                    Player player = (Player) object;
                    players.add(player);
                    availableColors.remove(player.getPlayerColor().name());
                }
                activity.updatePlayerList(players);
                activity.updateAvailableColors(availableColors);
                if (ClientModel.getInstance().currentGameReady()) {
                    activity.setStartGameEnabled(true);
                }
            }
        }
    }
}
