package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String username;
    private PlayerColor playerColor;
    private ArrayList<TrainCard> trainCards;

    public Player(String username) {
        this.username = username;
        playerColor = PlayerColor.UNCHOSEN;
    }
    //the following constructor is used for testing
    public Player(String username, PlayerColor color) {
        this.username = username;
        playerColor = color;
    }

    public void addTrainCardToHand(TrainCard trainCard) {
        trainCards.add(trainCard);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.getUsername()) &&
                playerColor.equals(player.getPlayerColor());
    }
}
