package model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import modelclasses.Route;

import static modelclasses.Atlas.*;
import static modelclasses.TrainCardColor.*;

public class MapSetup {

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
    public static LatLngBounds getBounds() {
        return new LatLngBounds(new LatLng(45.67, -68.6), new LatLng(26.9, -124));
    }
}
