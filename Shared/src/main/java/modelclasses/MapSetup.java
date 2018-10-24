package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modelclasses.Route;

import static modelclasses.Atlas.*;
import static modelclasses.TrainCardColor.*;

public class MapSetup implements Serializable {

    private double northBound = 45.67;
    private double eastBound = -68.6;
    private double southBound = 26.9;
    private double westBound = -125;

    private static ArrayList<Route> routes;

    public MapSetup() {
        ArrayList<Route> routes = new ArrayList<>();
        // wild routes
        routes.add(new Route(VANCOUVER, CALGARY, WILD, 3));
        routes.add(new Route(WINNIPEG, SAULT_ST_MARIE, WILD, 6));
        routes.add(new Route(SAULT_ST_MARIE, TORONTO, WILD, 2));
        routes.add(new Route(TORONTO, MONTREAL, WILD, 3));
        routes.add(new Route(MONTREAL, BOSTON, WILD, 2));
        routes.add(new Route(MONTREAL, BOSTON, WILD, 2));
        routes.add(new Route(TORONTO, PITTSBURGH, WILD, 2));
        routes.add(new Route(PITTSBURGH, WASHINGTON, WILD, 2));
        routes.add(new Route(PITTSBURGH, RALEIGH, WILD, 2));
        routes.add(new Route(WASHINGTON, RALEIGH, WILD, 2));
        routes.add(new Route(WASHINGTON, RALEIGH, WILD, 2));
        routes.add(new Route(RALEIGH, CHARLESTON, WILD, 2));
        routes.add(new Route(ATLANTA, CHARLESTON, WILD, 2));
        routes.add(new Route(ATLANTA, RALEIGH, WILD, 2));
        routes.add(new Route(ATLANTA, RALEIGH, WILD, 2));
        routes.add(new Route(ATLANTA, NASHVILLE, WILD, 1));
        routes.add(new Route(SAINT_LOUIS, NASHVILLE, WILD, 2));
        routes.add(new Route(SAINT_LOUIS, LITTLE_ROCK, WILD, 2));
        routes.add(new Route(DALLAS, LITTLE_ROCK, WILD, 2));
        routes.add(new Route(DALLAS, HOUSTON, WILD, 1));
        routes.add(new Route(DALLAS, HOUSTON, WILD, 1));
        routes.add(new Route(NEW_ORLEANS, HOUSTON, WILD, 2));
        routes.add(new Route(DALLAS, OK_CITY, WILD, 2));
        routes.add(new Route(DALLAS, OK_CITY, WILD, 2));
        routes.add(new Route(LITTLE_ROCK, OK_CITY, WILD, 2));
        routes.add(new Route(KANSAS_CITY, OK_CITY, WILD, 2));
        routes.add(new Route(KANSAS_CITY, OK_CITY, WILD, 2));
        routes.add(new Route(KANSAS_CITY, OMAHA, WILD, 1));
        routes.add(new Route(KANSAS_CITY, OMAHA, WILD, 1));
        routes.add(new Route(DULUTH, OMAHA, WILD, 2));
        routes.add(new Route(DULUTH, OMAHA, WILD, 2));
        routes.add(new Route(DULUTH, SAULT_ST_MARIE, WILD, 3));
        routes.add(new Route(DENVER, SANTA_FE, WILD, 2));
        routes.add(new Route(EL_PASO, SANTA_FE, WILD, 2));
        routes.add(new Route(EL_PASO, PHOENIX, WILD, 3));
        routes.add(new Route(SANTA_FE, PHOENIX, WILD, 3));
        routes.add(new Route(LOS_ANGELES, PHOENIX, WILD, 3));
        routes.add(new Route(LOS_ANGELES, LAS_VEGAS, WILD, 2));
        routes.add(new Route(PORTLAND, SEATTLE, WILD, 1));
        routes.add(new Route(PORTLAND, SEATTLE, WILD, 1));
        routes.add(new Route(VANCOUVER, SEATTLE, WILD, 1));
        routes.add(new Route(VANCOUVER, SEATTLE, WILD, 1));
        routes.add(new Route(CALGARY, SEATTLE, WILD, 4));
        routes.add(new Route(CALGARY, HELENA, WILD, 4));

        // white routes
        routes.add(new Route(CALGARY, WINNIPEG, WHITE, 6));
        routes.add(new Route(CHICAGO, TORONTO, WHITE, 4));
        routes.add(new Route(PITTSBURGH, NEW_YORK, WHITE, 2));
        routes.add(new Route(LITTLE_ROCK, NASHVILLE, WHITE, 3));
        routes.add(new Route(PHOENIX, DENVER, WHITE, 5));
        routes.add(new Route(SAN_FRANCISCO, SLC, WHITE, 5));
    }

    public static List<Route> getRoutes() {
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
