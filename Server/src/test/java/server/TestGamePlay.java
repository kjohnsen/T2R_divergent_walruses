package server;

import static modelclasses.Atlas.HELENA;
import static modelclasses.Atlas.PORTLAND;
import static modelclasses.Atlas.SAN_FRANCISCO;
import static modelclasses.Atlas.SLC;
import static modelclasses.Atlas.WINNIPEG;
import static modelclasses.Atlas.DULUTH;
import static modelclasses.Atlas.CALGARY;
import static modelclasses.Atlas.VANCOUVER;
import static modelclasses.TrainCardColor.BLUE;
import static modelclasses.TrainCardColor.PURPLE;
import static modelclasses.TrainCardColor.ORANGE;
import static modelclasses.TrainCardColor.WHITE;
import static modelclasses.TrainCardColor.WILD;

import static org.junit.Assert.* ;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import model.ServerModel;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import modelclasses.GameName;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.TrainCardColor;
import modelclasses.Route;
import modelclasses.City;
import results.Results;

public class TestGamePlay {

    private GameName GAMENAME = new GameName("Claim Routes Game");
    private String PLAYER_USERNAME = "player1";

    @Before
    public void setUp() {
        // create players
        Player player1 = new Player("player1", PlayerColor.BLACK);
        Player player2 = new Player("player2", PlayerColor.BLUE);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // create game
        GameInfo game = new GameInfo(GAMENAME, players, 2);
        ServerModel.getInstance().getGames().put(GAMENAME, game);
    }

    @Test
    public void unsuccessfulClaimRoute() {
        Player player = ServerModel.getInstance().getGameInfo(GAMENAME).getPlayer(PLAYER_USERNAME);
        ArrayList<TrainCard> playerCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerCards.add(new TrainCard(TrainCardColor.BLUE));
        }
        player.setTrainCards(playerCards);

        GameName gameName = new GameName("Claim Routes Game");
        Route route = new Route(PORTLAND, SLC, BLUE, 6);
        Results results = ServerFacade.getInstance().claimRoute(gameName, route, PLAYER_USERNAME);
        assertFalse(results.getSuccess());
        assertEquals("Player unable to claim route", results.getErrorMessage());
    }

    @Test
    public void claimRoute() {
        GameInfo game = ServerModel.getInstance().getGameInfo(GAMENAME);
        Player player = game.getPlayer(PLAYER_USERNAME);
        int initialTrainCarNum = player.getNumberOfTrains();
        int initialDiscardDeckSize = game.getDiscardedTrainCards().size();
        Route route = new Route(HELENA, WINNIPEG, BLUE, 4);

        ArrayList<TrainCard> playerCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerCards.add(new TrainCard(TrainCardColor.BLUE));
        }
        player.setTrainCards(playerCards);

        Results results = ServerFacade.getInstance().claimRoute(GAMENAME, route, PLAYER_USERNAME);
        assertTrue(results.getSuccess());
        assertEquals(results.getClientCommands().size(), 1);

        int trainCarDiff = initialTrainCarNum - player.getNumberOfTrains();
        assertEquals(4, trainCarDiff);
        assertEquals(0, player.getTrainCards().size());
        assertEquals(4, game.getDiscardedTrainCards().size() - initialDiscardDeckSize);
    }

    @Test
    public void claimRouteWithWilds() {
        GameInfo game = ServerModel.getInstance().getGameInfo(GAMENAME);
        Player player = game.getPlayer(PLAYER_USERNAME);
        int initialTrainCarNum = player.getNumberOfTrains();
        int initialDiscardDeckSize = game.getDiscardedTrainCards().size();
        Route route = new Route(SAN_FRANCISCO, PORTLAND, PURPLE, 5);

        ArrayList<TrainCard> trainCardsToAdd = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            trainCardsToAdd.add(new TrainCard(TrainCardColor.PURPLE));
        }
        for (int i = 0; i < 3; i++) {
            trainCardsToAdd.add(new TrainCard(TrainCardColor.WILD));
        }
        player.setTrainCards(trainCardsToAdd);

        Results results = ServerFacade.getInstance().claimRoute(GAMENAME, route, PLAYER_USERNAME);
        assertTrue(results.getSuccess());
        assertEquals(results.getClientCommands().size(), 1);

        int trainCarDiff = initialTrainCarNum - player.getNumberOfTrains();
        assertEquals(5, trainCarDiff);
        assertEquals(1, player.getTrainCards().size());
        assertTrue(player.getTrainCards().contains(new TrainCard(TrainCardColor.WILD)));
        assertEquals(5, game.getDiscardedTrainCards().size() - initialDiscardDeckSize);
    }

    @Test
    public void claimRouteStartLastRound() {
        GameName gameName = new GameName("Claim Routes Game");
        GameInfo game = ServerModel.getInstance().getGameInfo(gameName);
        Player player = game.getPlayer(PLAYER_USERNAME);
        int initialTrainCarNum = 6;
        player.setNumberOfTrains(initialTrainCarNum);
        int initialDiscardDeckSize = game.getDiscardedTrainCards().size();
        Route route = new Route(HELENA, WINNIPEG, BLUE, 4);

        ArrayList<TrainCard> playerCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerCards.add(new TrainCard(TrainCardColor.BLUE));
        }
        player.setTrainCards(playerCards);

        Results results = ServerFacade.getInstance().claimRoute(gameName, route, PLAYER_USERNAME);
        assertTrue(results.getSuccess());
        assertTrue(player.getNumberOfTrains() == 2);
        assertEquals(0, player.getTrainCards().size());
        assertEquals(results.getClientCommands().size(), 2);
        assertEquals(4, game.getDiscardedTrainCards().size() - initialDiscardDeckSize);
    }

    @Test
    public void addToConnectedCities() {
        Player p = new Player("connectedCitiesPlayer");
        Route helenaToDuluth = new Route(HELENA, DULUTH, ORANGE, 6);
        Route calgaryToWinnipeg = new Route(CALGARY, WINNIPEG, WHITE, 6);
        Route vancouverToCalgary = new Route(VANCOUVER, CALGARY, WILD, 3);
        Route helenaToWinnipeg = new Route(HELENA, WINNIPEG, BLUE, 4);

        p.addToConnectedCities(helenaToDuluth);
        assertEquals(1, p.getConnectedCities().size());
        HashSet<City> cities1 = p.getConnectedCities().get(0);
        assertEquals(2, cities1.size());
        assertTrue(cities1.contains(HELENA));
        assertTrue(cities1.contains(DULUTH));

        p.addToConnectedCities(calgaryToWinnipeg);
        assertEquals(2, p.getConnectedCities().size());
        HashSet<City> cities2 = p.getConnectedCities().get(1);
        assertEquals(2, cities2.size());
        assertTrue(cities2.contains(CALGARY));
        assertTrue(cities2.contains(WINNIPEG));

        p.addToConnectedCities(vancouverToCalgary);
        assertEquals(2, p.getConnectedCities().size());
        HashSet<City> cities3 = p.getConnectedCities().get(1);
        assertEquals(3, cities3.size());
        assertTrue(cities3.contains(VANCOUVER));

        p.addToConnectedCities(helenaToWinnipeg);
        assertEquals(1, p.getConnectedCities().size());
        HashSet<City> finalCities = p.getConnectedCities().get(0);
        assertEquals(5, finalCities.size());
        assertTrue(finalCities.contains(HELENA));
        assertTrue(finalCities.contains(DULUTH));
        assertTrue(finalCities.contains(CALGARY));
        assertTrue(finalCities.contains(VANCOUVER));
        assertTrue(finalCities.contains(WINNIPEG));
    }

}
