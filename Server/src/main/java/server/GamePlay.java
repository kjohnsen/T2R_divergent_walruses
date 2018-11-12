package server;

import model.ServerModel;
import results.Results;
import modelclasses.GameName;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.Route;
import modelclasses.TrainCard;
import data.Command;

import java.util.ArrayList;
import java.util.Arrays;

public class GamePlay {

    public GamePlay() {}

    public Results claimRoute(GameName gameName, Route route, String username) {

        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        Player player = game.getPlayer(username);
        Results results = new Results();

        // verify that player can claim route
        if (!playerCanClaimRoute(route, player)) {
            results.setErrorMessage("Player unable to claim route");
            results.setSuccess(false);
            return results;
        }

        // claim the route for the specified player
        game.removeFromUnclaimedRoutes(route);
        player.addRoute(route);

        // send claimRoute command to the clients
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.claimRoute(gameName, route, username);

        // check if player's number of train cars initiates last round
        if (player.getNumberOfTrains() < 3) {
            game.setLastRound(true);
            Command lastRoundCommand = new Command("model.CommandFacade", "_startLastRound", Arrays.asList(new Object[] {}));
            results.getClientCommands().add(lastRoundCommand);
            clientProxy.startLastRound(gameName, username);
        }

        Command claimRouteCommand = new Command("model.CommandFacade", "_claimRoute", Arrays.asList(new Object[] {gameName, route, username}));
        results.getClientCommands().add(claimRouteCommand);
        results.setSuccess(true);
        return null;
    }

    private boolean playerCanClaimRoute(Route route, Player player) {
        // verify that player has correct color and number of train cards
        int routeLength = route.getLength();
        int cardCount = 0;

        ArrayList<TrainCard> playerTrainCards = player.getTrainCards();
        for (TrainCard card : playerTrainCards) {
            if (card.getColor().equals(route.getColor())) {
                cardCount++;
            }
        }

        if (cardCount < routeLength) {
            return false;
        }

        // verify that player has enough trains
        if (player.getNumberOfTrains() < routeLength) {
            return false;
        }

        return true;
    }
}
