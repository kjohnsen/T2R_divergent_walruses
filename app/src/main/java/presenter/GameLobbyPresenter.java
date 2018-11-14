package presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import activity.IGameLobbyView;
import model.ClientModel;
import model.UIFacade;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import modelclasses.TrainCardWrapper;

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
        return UIFacade.getInstance().startGame();
    }

    @Override
    public void getGameLobbyInfo() {
        GameInfo gameInfo = UIFacade.getInstance().getCurrentGame();
        view.updatePlayerList(gameInfo.getPlayers());
        String username = UIFacade.getInstance().getUsername();
        PlayerColor color = UIFacade.getInstance().getCurrentColor();
        int position = PlayerColor.getColorIndex(gameInfo.getPlayers(), username, color);
        view.updateAvailableColors(PlayerColor.getAvailableColors(gameInfo.getPlayers(), username), position);
        view.setStartGameEnabled(UIFacade.getInstance().currentGameReady());
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof ArrayList) {
            ArrayList<Object> array = (ArrayList<Object>) o;
            if (!array.isEmpty() && array.get(0) instanceof Player) {
                ArrayList<Player> players = new ArrayList<>();
                for (Object object : array) {
                    Player player = (Player) object;
                    players.add(player);
                }
                String username = UIFacade.getInstance().getUsername();
                PlayerColor color = UIFacade.getInstance().getCurrentColor();
                int position = PlayerColor.getColorIndex(players, username, color);
                List<String> colors = PlayerColor.getAvailableColors(players, username);
                view.updatePlayerList(players);
                view.updateAvailableColors(colors, position);
                view.setStartGameEnabled(UIFacade.getInstance().currentGameReady());
                if (ClientModel.getInstance().currentGameReady()) {
                    view.setStartGameEnabled(true);
                }
            }
        } else if (o instanceof TrainCardWrapper) {
            observable.deleteObserver(this);
            view.startGame();
        }
    }
}