package server;

import model.ServerModel;
import modelclasses.DestinationCard;
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

    public static Results drawTrainCard(GameName name, String authToken) {
        GameInfo game = ServerModel.getInstance().getGameInfo(name);
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);
        Player player = game.getPlayer(username);
        TrainCard card = game.drawTrainCard();
        player.addTrainCardToHand(card);
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.drawTrainCard(card, player, game);
        Results results = new Results();
        Command selectCardCommand = new Command("model.CommandFacade", "_drawTrainCard", Arrays.asList(new Object[] {card, player}));
        results.getClientCommands().add(selectCardCommand);
        results.setSuccess(true);
        return results;
    }

    public static Results drawDestinationCard(GameName name, String authToken) {
        GameInfo game = ServerModel.getInstance().getGameInfo(name);
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);
        Player player = game.getPlayer(username);
        ArrayList<DestinationCard> tickets = game.getPlayerInitialDestCards();
        player.setPreSelectionDestCards(tickets);
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.displayDestinationCards(tickets, player, game);
        Results results = new Results();
        Command selectCardCommand = new Command("model.CommandFacade", "_displayDestinationCards", Arrays.asList(new Object[] {tickets, player}));
        results.getClientCommands().add(selectCardCommand);
        results.setSuccess(true);
        return results;
    }

    public static Results selectTrainCard(Integer index, GameName name, String authToken) {
        GameInfo game = ServerModel.getInstance().getGameInfo(name);
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);
        Player player = game.getPlayer(username);
        TrainCard card = game.getFaceUpCards().get(index);
        player.addTrainCardToHand(card);
        ArrayList<TrainCard> replacements = game.replaceCards(index);
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.selectTrainCard(card, player, game);
        Results results = new Results();
        Command selectCardCommand = new Command("model.CommandFacade", "_selectTrainCard", Arrays.asList(new Object[] {card, player}));
        results.getClientCommands().add(selectCardCommand);
        if (replacements.size() == 1) {
            clientProxy.replaceTrainCard(replacements.get(0), index, game, username);
            Command replaceCardCommand = new Command("model.CommandFacade", "_replaceTrainCard", Arrays.asList(new Object[] {replacements.get(0), index}));
            results.getClientCommands().add(replaceCardCommand);

        } else {
            clientProxy.clearWilds(replacements, game, username);
            Command clearWildsCommand = new Command("model.CommandFacade", "_clearWilds", Arrays.asList(new Object[]{replacements}));
            results.getClientCommands().add(clearWildsCommand);
        }
        results.setSuccess(true);
        return results;
    }

    public static Results selectDestinationCards(ArrayList<DestinationCard> tickets, GameName name, String authToken) {
        if (tickets != null) {
            GameInfo game = ServerModel.getInstance().getGameInfo(name);
            String username = ServerModel.getInstance().getAuthTokens().get(authToken);
            Player player = game.getPlayer(username);
            for (DestinationCard card : tickets) {
                if (player.getPreSelectionDestCards().contains(card)) {
                    player.removeDestCardFromList(card);
                    game.putDestCardInDeck(card); // put ticket back in dest card deck
                }
                else {
                    Results results = new Results();
                    results.setSuccess(false);
                    results.setErrorMessage("Destination card not in player's list");
                    return results;
                }
            }

            // put remaining cards in player's hand
            for (DestinationCard card : player.getPreSelectionDestCards()) {
                player.addDestCardToHand(card);
            }
            player.clearPreSelectionDestCards();
            ClientProxy clientProxy = new ClientProxy();
            clientProxy.selectDestinationCards(game.getGameName(), tickets, player, game);
            Results results = new Results();
            Command selectDestCardsCommand = new Command("model.CommandFacade", "_selectDestinationCards", Arrays.asList(new Object[] {name, tickets, player}));
            results.getClientCommands().add(selectDestCardsCommand);
            results.setSuccess(true);
            return results;
        } else {
            Results results = new Results();
            results.setSuccess(false);
            results.setErrorMessage("Destination card not in player's hand");
            return results;
        }
    }

    public static Results claimRoute(GameName gameName, Route route, String username) {

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

    private static ArrayList<TrainCard> getCardsForClaimingRoute(Route route, Player player) {
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
