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
        routes = new ArrayList<>();
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

        // black routes
        routes.add(new Route(WINNIPEG, DULUTH, BLACK, 4));
        routes.add(new Route(SAULT_ST_MARIE, MONTREAL, BLACK, 5));
        routes.add(new Route(NEW_YORK, WASHINGTON, BLACK, 2));
        routes.add(new Route(CHICAGO, PITTSBURGH, BLACK, 3));
        routes.add(new Route(NASHVILLE, RALEIGH, BLACK, 3));
        routes.add(new Route(KANSAS_CITY, DENVER, BLACK, 4));
        routes.add(new Route(LOS_ANGELES, EL_PASO, BLACK, 6));

        // yellow routes
        routes.add(new Route(SEATTLE, HELENA, YELLOW, 6));
        routes.add(new Route(BOSTON, NEW_YORK, YELLOW, 2));
        routes.add(new Route(PITTSBURGH, NASHVILLE, YELLOW, 4));
        routes.add(new Route(NEW_ORLEANS, ATLANTA, YELLOW, 4));
        routes.add(new Route(OK_CITY, EL_PASO, YELLOW, 5));
        routes.add(new Route(SLC, DENVER, YELLOW, 3));
        routes.add(new Route(LOS_ANGELES, SAN_FRANCISCO, YELLOW, 3));

        // blue routes
        routes.add(new Route(PORTLAND, SLC, BLUE, 6));
        routes.add(new Route(HELENA, WINNIPEG, BLUE, 4));
        routes.add(new Route(OMAHA, CHICAGO, BLUE, 4));
        routes.add(new Route(MONTREAL, NEW_YORK, BLUE, 3));
        routes.add(new Route(KANSAS_CITY, SAINT_LOUIS, BLUE, 2));
        routes.add(new Route(ATLANTA, MIAMI, BLUE, 5));
        routes.add(new Route(SANTA_FE, OK_CITY, BLUE, 3));

        // red routes
        routes.add(new Route(HELENA, OMAHA, RED, 5));
        routes.add(new Route(DULUTH, CHICAGO, RED, 3));
        routes.add(new Route(BOSTON, NEW_YORK, RED, 2));
        routes.add(new Route(NEW_ORLEANS, MIAMI, RED, 6));
        routes.add(new Route(DALLAS, EL_PASO, RED, 4));
        routes.add(new Route(OK_CITY, DENVER, RED, 4));
        routes.add(new Route(SLC, DENVER, RED, 3));

        // orange routes
        routes.add(new Route(HELENA, DULUTH, ORANGE, 6));
        routes.add(new Route(CHICAGO, PITTSBURGH, ORANGE, 3));
        routes.add(new Route(NEW_YORK, WASHINGTON, ORANGE, 2));
        routes.add(new Route(NEW_ORLEANS, ATLANTA, ORANGE, 4));
        routes.add(new Route(DENVER, KANSAS_CITY, ORANGE, 4));
        routes.add(new Route(LAS_VEGAS, SLC, ORANGE, 3));
        routes.add(new Route(SAN_FRANCISCO, SLC, ORANGE, 5));

        // purple routes
        routes.add(new Route(HELENA, SLC, PURPLE, 3));
        routes.add(new Route(DENVER, OMAHA, PURPLE, 4));
        routes.add(new Route(KANSAS_CITY, SAINT_LOUIS, PURPLE, 2));
        routes.add(new Route(DULUTH, TORONTO, PURPLE, 6));
        routes.add(new Route(CHARLESTON, MIAMI, PURPLE, 4));
        routes.add(new Route(LOS_ANGELES, SAN_FRANCISCO, PURPLE, 3));
        routes.add(new Route(SAN_FRANCISCO, PORTLAND, PURPLE, 5));

        // green routes
        routes.add(new Route(NEW_YORK, PITTSBURGH, GREEN, 2));
        routes.add(new Route(SAINT_LOUIS, PITTSBURGH, GREEN, 5));
        routes.add(new Route(SAINT_LOUIS, CHICAGO, GREEN, 2));
        routes.add(new Route(LITTLE_ROCK, NEW_ORLEANS, GREEN, 3));
        routes.add(new Route(EL_PASO, HOUSTON, GREEN, 6));
        routes.add(new Route(DENVER, HELENA, GREEN, 4));
        routes.add(new Route(SAN_FRANCISCO, PORTLAND, GREEN, 5));

        createCityGraph();
    }

    private void createCityGraph() {

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
