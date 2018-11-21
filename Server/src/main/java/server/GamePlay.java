package server;

import model.ServerModel;
import model.ServerState;
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

        if(ServerModel.getInstance().getState() == ServerState.TOOKONETRAINCARD) {
            ServerModel.getInstance().setState(ServerState.TOOKTWOTRAINCARDS);
        } else {
            ServerModel.getInstance().setState(ServerState.TOOKONETRAINCARD);
        }

        TrainCard card = game.drawTrainCard();
        player.addTrainCardToHand(card);
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.drawTrainCard(card, player, game);
        Results results = new Results();
        Command selectCardCommand = new Command("model.CommandFacade", "_drawTrainCard", Arrays.asList(new Object[] {card, player}));
        results.getClientCommands().add(selectCardCommand);
        results.setSuccess(true);

        if (ServerModel.getInstance().getState() == ServerState.TOOKTWOTRAINCARDS) {
            Command command = startNextTurn(game);
            results.getClientCommands().add(command);
        }

        return results;
    }

    public static Results drawDestinationCard(GameName name, String authToken) {
        ServerModel.getInstance().setState(ServerState.TOOKDESTINATIONCARDS);

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

        boolean isWild = card.getColor().equals(TrainCardColor.WILD);
        if(isWild) {
            ServerModel.getInstance().setState(ServerState.TOOKWILDTRAINCARD);
        } else {
            if(ServerModel.getInstance().getState() == ServerState.TOOKONETRAINCARD) {
                ServerModel.getInstance().setState(ServerState.TOOKTWOTRAINCARDS);
            } else {
                ServerModel.getInstance().setState(ServerState.TOOKONETRAINCARD);
            }
        }

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

        if (ServerModel.getInstance().getState() == ServerState.TOOKTWOTRAINCARDS ||
                ServerModel.getInstance().getState() == ServerState.TOOKWILDTRAINCARD) {
            Command command = startNextTurn(game);
            results.getClientCommands().add(command);
        }

        return results;
    }

    public static Results selectDestinationCards(ArrayList<DestinationCard> tickets, GameName name, String authToken) {
        ServerModel.getInstance().setState(ServerState.CHOSEDESTINATIONCARDS);

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
            if (player.isStartOfGame()) {
                player.setStartOfGameFalse();
            }
            else {
                Command command = startNextTurn(game);
                results.getClientCommands().add(command);
            }

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

    public static Results claimRoute(GameName gameName, Route route, String authToken, TrainCardColor chosenColor) {

        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);
        Player player = game.getPlayer(username);
        Results results = new Results();

        // check double route validity
        boolean fewPlayers = game.getNumPlayers() < 4;
        Route twinRoute = route.getTwinRoute();
        boolean twoRoutes = twinRoute != null;

        if (fewPlayers && twoRoutes) {
            if (!game.getUnclaimedRoutes().contains(twinRoute)) {
                results.setErrorMessage("Double route already claimed");
                results.setSuccess(false);
                return results;
            }
        }

        else if (!fewPlayers && twoRoutes) {
            if (player.getRoutes().contains(twinRoute)) {
                results.setErrorMessage("Don't be a jerk");
                results.setSuccess(false);
                return results;
            }
        }

        // verify that player can claim route
        ArrayList<TrainCard> cardsForClaimingRoute = getCardsForClaimingRoute(route, player, chosenColor);
        boolean enoughTrains = (player.getNumberOfTrains() - route.getLength()) >= 0;
        if (cardsForClaimingRoute == null || !enoughTrains) {
            results.setErrorMessage("Player unable to claim route");
            results.setSuccess(false);
            return results;
        }

        // claim the route for the specified player
        game.removeFromUnclaimedRoutes(route);
        player.addRoute(route);
        route.setPlayer(player);
        player.addPoints(Route.getPointsForRouteOfLength(route.getLength()));

        player.setNumberOfTrains(player.getNumberOfTrains() - route.getLength());
        player.removeTrainCardsFromHand(cardsForClaimingRoute);
        game.addCardsToTrainDiscarded(cardsForClaimingRoute);

        // send claimRoute command to the clients
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.claimRoute(gameName, route, username, player.getTrainCards(), player.getNumberOfTrains());

        // check if player's number of train cars initiates last round
        if (player.getNumberOfTrains() < 3 && !game.isLastRound()) {
            game.setLastRound(true);
            Command lastRoundCommand = new Command("model.CommandFacade", "_startLastRound", Arrays.asList(new Object[] {}));
            results.getClientCommands().add(lastRoundCommand);
            clientProxy.startLastRound(gameName, username);
        }

        Command command = startNextTurn(game);
        results.getClientCommands().add(command);

        Command claimRouteCommand = new Command("model.CommandFacade", "_claimRoute", Arrays.asList(new Object[] {gameName, route, username, player.getTrainCards(), player.getNumberOfTrains()}));
        results.getClientCommands().add(claimRouteCommand);

        results.setSuccess(true);
        return results;
    }

    /** returns either the end game command or the startTurnCommand **/
    private static Command startNextTurn(GameInfo game) {
        Player currPlayer = game.getCurrentPlayer();
        int currPlayerIndex = game.getCurrentPlayerIndex();
        ServerModel.getInstance().setState(ServerState.TURNSTART);

        ClientProxy clientProxy = new ClientProxy();
        boolean isLastPlayer = currPlayerIndex == game.getPlayers().size() - 1;
        if (isLastPlayer && game.isLastRound()) {
            clientProxy.endGame(game.getGameName(), currPlayer.getUsername());
            return new Command("model.CommandFacade", "_endGame", Arrays.asList(new Object[] {}));
        }
        else {
            Player nextPlayer = game.getNextPlayer();
            game.setCurrentPlayer(nextPlayer);
            clientProxy.startTurn(game.getGameName(), currPlayer.getUsername(), nextPlayer.getUsername());
            return new Command("model.CommandFacade", "_startNextTurn", Arrays.asList(new Object[] {nextPlayer.getUsername()}));
        }
    }

    private static ArrayList<TrainCard> getCardsForClaimingRoute(Route route, Player player, TrainCardColor chosenColor) {
        int routeLength = route.getLength();;
        ArrayList<TrainCard> cardsForClaimingRoute = new ArrayList<>();

        ArrayList<TrainCard> playerTrainCards = player.getTrainCards();
        ArrayList<TrainCard> wildCards = new ArrayList<>();
        for (TrainCard card : playerTrainCards) {
            if (card.getColor().equals(TrainCardColor.WILD)) {
                wildCards.add(card);
            }
            boolean matchingColor = card.getColor().equals(chosenColor);
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
