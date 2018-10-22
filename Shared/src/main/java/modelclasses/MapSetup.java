package modelclasses;

import java.util.ArrayList;
import java.util.List;

import modelclasses.Route;

import static modelclasses.Atlas.*;
import static modelclasses.TrainCardColor.*;

public class MapSetup {

    private double northBound = 45.67;
    private double eastBound = -68.6;
    private double southBound = 26.9;
    private double westBound = -124;

    public static List<Route> getRoutes() {
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));

        return routes;
    }

    public double getNorthBound() {
        return northBound;
    }

    public double getEastBound() {
        return eastBound;
    }

    public double getSouthBound() {
        return southBound;
    }

    public double getWestBound() {
        return westBound;
    }
}
