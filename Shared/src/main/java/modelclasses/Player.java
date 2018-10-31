package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements Serializable {
    private String username;
    private PlayerColor playerColor;
    private ArrayList<TrainCard> trainCards = new ArrayList<>();
    private ArrayList<DestinationCard> destinationCards = new ArrayList<>();
    private ArrayList<Route> routes = new ArrayList<>();
    private Integer points = 0;
    private Integer numberOfTrains = 45;

    public Player(String username) {
        this.username = username;
        playerColor = PlayerColor.UNCHOSEN;
    }
    //the following constructor is used for testing
    public Player(String username, PlayerColor color) {
        this.username = username;
        playerColor = color;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getNumberOfTrains() {
        return numberOfTrains;
    }

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

    public void addTrainCardToHand(TrainCard trainCard) {
        trainCards.add(trainCard);
    }

    public void addDestCardToHand(DestinationCard destinationCard) {
        destinationCards.add(destinationCard);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void removeDestCardFromHand(DestinationCard destinationCard) {
        destinationCards.remove(destinationCard);
    }

    public String getUsername() {
        return username;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public ArrayList<TrainCard> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(ArrayList<TrainCard> trainCards) {
        this.trainCards = trainCards;
    }

    public ArrayList<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(ArrayList<DestinationCard> destinationCards) {
        this.destinationCards = destinationCards;
    }

    public void addDestinationCards(ArrayList<DestinationCard> tickets) {
        destinationCards.addAll(tickets);
    }

    public void removeDestinationCards(ArrayList<DestinationCard> tickets) {
        destinationCards.removeAll(tickets);
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.getUsername()) &&
                playerColor.equals(player.getPlayerColor());
    }
}
