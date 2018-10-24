package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player implements Serializable {
    private String username;
    private PlayerColor playerColor;
    private ArrayList<TrainCard> trainCards;
    private ArrayList<DestinationCard> destinationCards;
    private Integer points;
    private Integer numberOfTrains;

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

    public Map<TrainCardColor,Integer> getTrainCardQuantities(){
        Map<TrainCardColor, Integer> trainCard_amount = new HashMap<>();

        int red = 0, orange = 0, yellow = 0, green = 0, blue = 0, purple = 0, black = 0, white = 0, wild = 0;
        for(TrainCard card: trainCards){
            switch(card.getColor()){
                case RED:
                    red++;
                case ORANGE:
                    orange++;
                case YELLOW:
                    yellow++;
                case GREEN:
                    green++;
                case BLUE:
                    blue++;
                case PURPLE:
                    purple++;
                case BLACK:
                    black++;
                case WHITE:
                    white++;
                case WILD:
                    wild++;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.getUsername()) &&
                playerColor.equals(player.getPlayerColor());
    }
}
