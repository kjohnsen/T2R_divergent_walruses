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

    public GameLobbyPresenter(IGameLobbyActivity activity) {
        this.activity = activity;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public String chooseColor(String color) {
        return UIFacade.getInstance().claimColor(PlayerColor.valueOf(color));
    }

    @Override
    public String startGame() {
        return UIFacade.getInstance().startGame();
    }

    @Override
    public void getGameLobbyInfo() {
        GameInfo gameInfo = UIFacade.getInstance().getCurrentGame();
        activity.updatePlayerList(gameInfo.getPlayers());
        activity.updateAvailableColors(PlayerColor.getColors());
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof GameInfo) {
            observable.deleteObserver(this);
            activity.startGame();
        } else if (o instanceof ArrayList){
            ArrayList<Object> array = (ArrayList<Object>) o;
            if (array.get(0) instanceof Player) {
                ArrayList<Player> players = new ArrayList<>();
                ArrayList<String> colors = PlayerColor.getColors();
                for (Object object : array) {
                    Player player = (Player) object;
                    players.add(player);
                    if (!player.getPlayerColor().name().equals("UNCHOSEN")) {
                        colors.remove(player.getPlayerColor().name());
                    }
                }
                activity.updatePlayerList(players);
                activity.updateAvailableColors(colors);
                if (ClientModel.getInstance().currentGameReady()) {
                    activity.setStartGameEnabled(true);
                }
            }
        }
    }
}
