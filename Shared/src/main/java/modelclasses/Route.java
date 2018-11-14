package modelclasses;

import java.io.Serializable;

public class Route implements Serializable {
    private City origin;
    private City destination;
    private TrainCardColor color;
    private Integer length;
    private Player player;

    public Route(City origin, City destination, TrainCardColor color, Integer length) {
        this.origin = origin;
        this.destination = destination;
        this.color = color;
        this.length = length;
    }

    public City getOrigin() {
        return origin;
    }

    public City getDestination() {
        return destination;
    }

    public Player getPlayer() {
        return player;
    }

    public TrainCardColor getColor() {
        return color;
    }

    public Integer getLength() {
        return length;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
