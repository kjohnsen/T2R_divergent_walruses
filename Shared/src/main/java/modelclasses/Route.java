package modelclasses;

import java.io.Serializable;

public class Route implements Serializable {
    private City origin;
    private City destination;
    private TrainCardColor color;
    private Integer length;
    private Player player;

    private boolean isTwin = false;

    private Route twinRoute;

    public Route(City origin, City destination, TrainCardColor color, Integer length) {
        this.origin = origin;
        this.destination = destination;
        this.color = color;
        this.length = length;
        twinRoute = null;
        player = null;
    }

    public Route getTwinRoute() {
        return twinRoute;
    }

    public void setTwinRoute(Route twinRoute) {
        this.twinRoute = twinRoute;
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

    public void setColor(TrainCardColor color) {
        this.color = color;
    }

    public boolean isTwin() {
        return isTwin;
    }

    public void setTwin(boolean twin) {
        isTwin = twin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (isTwin() != route.isTwin()) return false;
        if (!getOrigin().equals(route.getOrigin())) return false;
        if (!getDestination().equals(route.getDestination())) return false;
        if (getColor() != route.getColor()) return false;
        return getLength().equals(route.getLength());
    }

    @Override
    public int hashCode() {
        int result = getOrigin().hashCode();
        result = 31 * result + getDestination().hashCode();
        result = 31 * result + getColor().hashCode();
        result = 31 * result + getLength().hashCode();
        result = 31 * result + (isTwin() ? 1 : 0);
        return result;
    }

    public static int getPointsForRouteOfLength(int length) {
        if(length < 1 || length > 6) {
            return 0;
        }

        switch(length) {
            case 1: return 1;
            case 2: return 2;
            case 3: return 4;
            case 4: return 7;
            case 5: return 10;
            case 6: return 15;
            default: break;
        }

        return 0;
    }
}
