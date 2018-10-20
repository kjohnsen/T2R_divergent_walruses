package modelclasses;

public class DestinationCard {
    private int points;
    private City cities[];

    public DestinationCard(int points, City[] cities) {
        this.points = points;
        this.cities = cities;
    }

    public int getPoints() { return points; }
    public City[] getCities() { return cities; }
}
