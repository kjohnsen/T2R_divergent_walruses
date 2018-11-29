package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;

/**
 * Represents a player in the game
 * Stores all of the data associated with a specific player,
 * as well has methods for updating and accessing this data
 */
public class Player implements Serializable {

    /**
     * stores the player's username
     */
    private String username;

    /**
     * stores the player's color
     */
    private PlayerColor playerColor;

    /**
     * stores a list of the train cards in the player's hand
     */
    private ArrayList<TrainCard> trainCards = new ArrayList<>();
    private ArrayList<DestinationCard> preSelectionDestCards = new ArrayList<>();

    /**
     * stores a list of the destination cards in the player's hand
     */
    private ArrayList<DestinationCard> destinationCards = new ArrayList<>();

    /**
     * stores a list of the routes a player has claimed
     */
    private ArrayList<Route> routes = new ArrayList<>();

    /**
     * stores a list of sets of connected cities, used to determine if destination cards were satisfied
     */
    private ArrayList<HashSet<City>> connectedCities = new ArrayList<>();

    /**
     * stores the player's current number of points
     */
    private Integer points = 0;

    /**
     * stores the number of trains the player has remaining
     */
    private Integer numberOfTrains = 10;

    /**
     *
     */
    private boolean startOfGame;

    /**
     * creates a Player object with given username
     * @pre username is unique and not null
     * @post Player's color is UNCHOSEN
     */
    public Player(String username) {
        this.username = username;
        playerColor = PlayerColor.UNCHOSEN;
        startOfGame = true;
    }


    /**
     * this constructor is used for testing
     * @pre username is unique and not null
     * @pre color != null
     */
    public Player(String username, PlayerColor color) {
        this.username = username;
        playerColor = color;
        startOfGame = true;
    }

    /**
     * sets Player's number of trains
     * @pre 0 <= numberOfTrains <= 45
     */
    public void setNumberOfTrains(Integer numberOfTrains) {
        this.numberOfTrains = numberOfTrains;
    }

    /**
     * @return player's current number of points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * increments the player's points by the given amount
     * @pre pointsToAdd > 0
     * @pre points != null
     * @post pointsToAdd increases
     */
    public void addPoints(int pointsToAdd) {
        points += pointsToAdd;
    }

    /**
     * @return player's current number of trains
     */
    public Integer getNumberOfTrains() {
        return numberOfTrains;
    }

    /**
     * @pre trainCardList.size() >= 0
     * @post returns a map which maps TrainCardColors to the amount of that color in the player's hand
     */
    public static Map<TrainCardColor,Integer> getTrainCardQuantities(List<TrainCard> trainCardList){
        Map<TrainCardColor, Integer> trainCard_amount = new HashMap<>();

        int red = 0, orange = 0, yellow = 0, green = 0, blue = 0, purple = 0, black = 0, white = 0, wild = 0;
        for(TrainCard card: trainCardList){
            switch(card.getColor()){
                case RED:
                    red++; break;
                case ORANGE:
                    orange++; break;
                case YELLOW:
                    yellow++; break;
                case GREEN:
                    green++; break;
                case BLUE:
                    blue++; break;
                case PURPLE:
                    purple++; break;
                case BLACK:
                    black++; break;
                case WHITE:
                    white++; break;
                case WILD:
                    wild++; break;
            }
        }
        trainCard_amount.put(TrainCardColor.RED, red);
        trainCard_amount.put(TrainCardColor.ORANGE, orange);
        trainCard_amount.put(TrainCardColor.YELLOW, yellow);
        trainCard_amount.put(TrainCardColor.GREEN, green);
        trainCard_amount.put(TrainCardColor.BLUE, blue);
        trainCard_amount.put(TrainCardColor.PURPLE,purple);
        trainCard_amount.put(TrainCardColor.WHITE, white);
        trainCard_amount.put(TrainCardColor.BLACK, black);
        trainCard_amount.put(TrainCardColor.WILD, wild);
        return trainCard_amount;
    }

    /**
     * adds a train card to the player's hand
     * @pre trainCard != null
     * @pre trainCards != null
     * @post trainCards.size() increased by 1
     */
    public void addTrainCardToHand(TrainCard trainCard) {
        trainCards.add(trainCard);
    }

    public void removeTrainCardsFromHand(ArrayList<TrainCard> cards) {
        for (TrainCard cardToRemove : cards) {
            trainCards.remove(cardToRemove);
        }
    }

    /**
     * adds a destination card to the player's hand
     * @pre destinationCard != null
     * @pre destinationCards != null
     * @post destinationCards.size() increased by 1
     */
    public void addDestCardToHand(DestinationCard destinationCard) {
        destinationCards.add(destinationCard);
    }

    /**
     * adds a route to the list of player's claimed routes
     * @pre route != null
     * @pre route is not claimed by another player
     * @pre routes != null
     * @ost routes.size() increased by 1
     */
    public void addRoute(Route route) {
        routes.add(route);
        addToConnectedCities(route);
    }

    public void addToConnectedCities(Route route) {
        HashSet unionedSet = null;
        boolean removeUnionedSet = false;
        for (HashSet currCities : connectedCities) {
            boolean connected = currCities.contains(route.getOrigin()) || currCities.contains(route.getDestination());
            // if you find a set that contains one of the cities, add the cities to that set
            if (connected && unionedSet == null) {
                currCities.add(route.getOrigin());
                currCities.add(route.getDestination());
                unionedSet = currCities;
            }
            // if you find another set that contains one of the cities, union the two sets together
            else if (connected && unionedSet != null) {
                currCities.addAll(unionedSet);
                removeUnionedSet = true;
            }
        }
        // if none of the current sets contain either city, add a new set with the route's two cities
        if (unionedSet == null) {
            HashSet<City> newSet = new HashSet<>();
            newSet.add(route.getOrigin());
            newSet.add(route.getDestination());
            connectedCities.add(newSet);
        }
        // since we added unionedSet to another set, we can remove it from the list
        else if (removeUnionedSet){
            connectedCities.remove(unionedSet);
        }
    }

    public String getUsername() {
        return username;
    }

    /**
     * @pre playerColor != null
     * @post returns the player's color
     */
    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    /**
     * sets the player's color to the given color
     * @pre playerColor != null
     * @pre playerColor != UNCHOSEN
     */
    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * @post returns list of player's train cards
     */
    public ArrayList<TrainCard> getTrainCards() {
        return trainCards;
    }

    /**
     * sets the player's train cards to the given list of train cards
     * @pre trainCards != null
     * @pre trainCards.size() < 5
     */
    public void setTrainCards(ArrayList<TrainCard> trainCards) {
        this.trainCards = trainCards;
    }

    public ArrayList<DestinationCard> getPreSelectionDestCards() {
        return preSelectionDestCards;
    }

    public void setPreSelectionDestCards(ArrayList<DestinationCard> preSelectionDestCards) {
        this.preSelectionDestCards = preSelectionDestCards;
    }

    public void removeDestCardFromList(DestinationCard destinationCard) {
        preSelectionDestCards.remove(destinationCard);
    }

    public void clearPreSelectionDestCards() {
        preSelectionDestCards.clear();
    }

    /**
     * @post returns list of player's destination cards
     */
    public ArrayList<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    /**
     * sets the player's destination cards to the given list of destination cards
     * @pre destinationCards != null
     */
    public void setDestinationCards(ArrayList<DestinationCard> destinationCards) {
        this.destinationCards = destinationCards;
    }

    /**
     * adds a list of destination cards to the player's current list of destination cards
     * @pre tickets != null
     * @pre destinationCards != null
     */
    public void addDestinationCards(ArrayList<DestinationCard> tickets) {
        destinationCards.addAll(tickets);
    }

    /**
     * removes a list of destination cards from the player's current list of destination cards
     * @pre tickets != null
     * @pre tickets.size() <= destinationCards.size()
     * @pre every ticket in tickets is in destinationCards
     * @post destinationCards.size() decreased by tickets.size()
     */
    public void removeDestinationCards(ArrayList<DestinationCard> tickets) {
        destinationCards.removeAll(tickets);
    }

    /**
     * @post returns a list of player's claimed routes
     */
    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public ArrayList<HashSet<City>> getConnectedCities() {
        return connectedCities;
    }

    public boolean isStartOfGame() {
        return startOfGame;
    }

    public void setStartOfGameFalse() {
        startOfGame = false;
    }

    /**
     * @post returns true if objects are equal, returns false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.getUsername()) &&
                playerColor.equals(player.getPlayerColor());
    }
}
