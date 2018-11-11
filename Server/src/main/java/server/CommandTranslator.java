package server;

import java.util.ArrayList;

import data.Command;
import model.ServerModel;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Route;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;

public class CommandTranslator {

    private static final String selectTrainCard = "_selectTrainCard";
    private static final String drawTrainCard = "_drawTrainCard";
    private static final String drawDestinationCards = "_drawDestinationCards";
    private static final String selectDestinationCards = "_selectDestinationCards";
    private static final String claimRoute = "_claimRoute";
    private static final String replaceTrainCard = "_replaceTrainCard";
    private static final String clearWilds = "_clearWilds";

    public static String translateCommand(Command command) {
        String commandMessage = "";
        switch(command.get_methodName()) {
            case CommandTranslator.drawDestinationCards: commandMessage = translateDrawDestinationCards(command);  break;
            case CommandTranslator.claimRoute: commandMessage = translateClaimRoute(command);  break;
            case CommandTranslator.clearWilds: commandMessage = translateClearWilds(command);  break;
            case CommandTranslator.drawTrainCard: commandMessage = translateDrawTrainCard(command);    break;
            case CommandTranslator.replaceTrainCard: commandMessage = translateReplaceTrainCard(command);  break;
            case CommandTranslator.selectDestinationCards: commandMessage = translateSelectDestinationCards(command);  break;
            case CommandTranslator.selectTrainCard: commandMessage = translateSelectTrainCard(command);    break;
            default:    break;
        }

        return commandMessage;
    }

    private static String translateDrawDestinationCards(Command command) {
        Object[] params = command.get_paramValues();
        String authToken = (String)params[1];
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);

        return username + " drew 3 Destination Cards.";
    }

    private static String translateSelectDestinationCards(Command command) {
        Object[] params = command.get_paramValues();
        ArrayList<DestinationCard> cards = (ArrayList<DestinationCard>)params[0];
        String authToken = (String)params[2];
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);
        String numCardsKept = Integer.toString(3 - cards.size());
        String cardOrCards = Integer.valueOf(numCardsKept) > 1 ? "Cards" : "Card";

        return username + " kept " + numCardsKept + " Destination " + cardOrCards + ".";
    }

    private static String translateSelectTrainCard(Command command) {
        Object[] params = command.get_paramValues();
        Integer index = (Integer)params[0];
        GameName gameName = (GameName)params[1];
        String authToken = (String)params[2];
        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        TrainCard card = game.getFaceUpCards().get(index);
        String cardColor = translateTrainCardColor(card.getColor());
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);

        return username + " took a " + cardColor + " face-up Train Card.";
    }

    private static String translateDrawTrainCard(Command command) {
        Object[] params = command.get_paramValues();
        String authToken = (String)params[1];;
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);

        return username + " drew a Train Card from the deck.";
    }

    private static String translateClaimRoute(Command command) {
        Object[] params = command.get_paramValues();
        String authToken = (String)params[2];
        Route route = (Route)params[1];
        String username = ServerModel.getInstance().getAuthTokens().get(authToken);
        String originCityName = route.getOrigin().getName();
        String destinationCityName = route.getDestination().getName();
        String routeColor = translateTrainCardColor(route.getColor());
        String routeLength = Integer.toString(route.getLength());

        return username + "claimed the " + routeColor + " Route of length " + routeLength + " from " + originCityName + " to " + destinationCityName + ".";
    }

    private static String translateReplaceTrainCard(Command command) {
        Object[] params = command.get_paramValues();
        TrainCard card = (TrainCard)params[0];
        String cardColor = translateTrainCardColor(card.getColor());

        return "A " + cardColor + " Train Card was added to the face-up cards.";
    }

    private static String translateClearWilds(Command command) {
        return "The face-up Train Cards were cleared and replaced with 5 new Train Cards.";
    }

    private static String translateTrainCardColor(TrainCardColor color) {
        switch(color) {
            case YELLOW: return "yellow";
            case ORANGE: return "orange";
            case GREEN: return "green";
            case BLACK: return "black";
            case BLUE: return "blue";
            case RED: return "red";
            case WILD: return "wild";
            case WHITE: return "white";
            case PURPLE: return "purple";
        }

        return "";
    }

}

