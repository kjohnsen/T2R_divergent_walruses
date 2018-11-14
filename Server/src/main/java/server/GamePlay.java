package server;

import model.ServerModel;
import modelclasses.TrainCardColor;
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
        ArrayList<TrainCard> cardsForClaimingRoute = getCardsForClaimingRoute(route, player);
        if (cardsForClaimingRoute == null) {
            results.setErrorMessage("Player unable to claim route");
            results.setSuccess(false);
            return results;
        }

        // claim the route for the specified player
        game.removeFromUnclaimedRoutes(route);
        player.addRoute(route);
        route.setPlayer(player);
        player.setNumberOfTrains(player.getNumberOfTrains() - route.getLength());
        player.removeTrainCardsFromHand(cardsForClaimingRoute);
        game.addCardsToTrainDiscarded(cardsForClaimingRoute);

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
        return results;
    }

    private ArrayList<TrainCard> getCardsForClaimingRoute(Route route, Player player) {
        int routeLength = route.getLength();
        ArrayList<TrainCard> cardsForClaimingRoute = new ArrayList<>();

        ArrayList<TrainCard> playerTrainCards = player.getTrainCards();
        ArrayList<TrainCard> wildCards = new ArrayList<>();
        for (TrainCard card : playerTrainCards) {
            if (card.getColor().equals(TrainCardColor.WILD)) {
                wildCards.add(card);
            }
            boolean matchingColor = card.getColor().equals(route.getColor());
            boolean needMoreCards = cardsForClaimingRoute.size() < routeLength;
            if (matchingColor && needMoreCards) {
                cardsForClaimingRoute.add(card);
            }
        }

        // verify that player has sufficient number of train cards
        //      and check to see if there are enough wilds to make up for it
        int neededCards = routeLength - cardsForClaimingRoute.size();

        // this is the case in which there are not enough cards of one color, and not enough wilds
        if (wildCards.size() < neededCards) {
            return null;
        }
        // this is the case in which there are not enough cards of one color, but there ARE enough wilds
        else if (neededCards > 0) {
            for (int i = 0; i < neededCards; i++) {
                cardsForClaimingRoute.add(wildCards.get(0));
            }
        }

        // verify that player has enough trains
        if (player.getNumberOfTrains() < routeLength) {
            return null;
        }

        return cardsForClaimingRoute;
    }
}
