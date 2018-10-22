package modelclasses;

public class Route {
    private City origin;
    private City destination;
    private TrainCardColor color;
    private Integer length;

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

    public TrainCardColor getColor() {
        return color;
    }

    public Integer getLength() {
        return length;
    }
}
