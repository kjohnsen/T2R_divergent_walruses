package modelclasses;

import java.io.Serializable;

public class DestinationCard implements Serializable {
    private int points;
    private City cities[];

    public DestinationCard(int points, City city1, City city2) {
        this.points = points;
        this.cities = new City[] {city1, city2};
    }

    public int getPoints() { return points; }
    public City[] getCities() { return cities; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DestinationCard card = (DestinationCard) o;
        return (cities[0].equals(card.getCities()[0]) &&
                cities[1].equals(card.getCities()[1])) || (cities[1].equals(card.getCities()[0]) &&
                cities[0].equals(card.getCities()[1]));
    }

    @Override
    public int hashCode() {
        return cities[0].hashCode() + cities[1].hashCode();
    }
}
