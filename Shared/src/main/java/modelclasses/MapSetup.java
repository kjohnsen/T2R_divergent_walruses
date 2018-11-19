package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static modelclasses.Atlas.*;
import static modelclasses.TrainCardColor.*;

public class MapSetup implements Serializable {

    private double northBound = 51.2;
    private double eastBound = -68.6;
    private double southBound = 31.0;
    private double westBound = -125;

    private ArrayList<Route> routes = new ArrayList<>();
    private Map<City, ArrayList<City>> cityConnectionMap =
            new DefaultHashMap<>(new ArrayList<City>());

    // Adds routes to list and manages map of connection between cities.
    // This map will be necessary for calculating things like longest
    // path as well as for rendering differently redundant routes
    private void addRoute(City one, City two, TrainCardColor color, int length) {
        Route route = new Route(one, two, color, length);
        //newfunction(route)
        routes.add(route);
        cityConnectionMap.get(one).add(two);
        cityConnectionMap.get(two).add(one);
    }



    public MapSetup() {
        // wild routes
        addRoute(VANCOUVER, CALGARY, WILD, 3);
        addRoute(WINNIPEG, SAULT_ST_MARIE, WILD, 6);
        addRoute(SAULT_ST_MARIE, TORONTO, WILD, 2);
        addRoute(TORONTO, MONTREAL, WILD, 3);
        addRoute(MONTREAL, BOSTON, WILD, 2);
        addRoute(MONTREAL, BOSTON, WILD, 2);
        addRoute(TORONTO, PITTSBURGH, WILD, 2);
        addRoute(PITTSBURGH, WASHINGTON, WILD, 2);
        addRoute(PITTSBURGH, RALEIGH, WILD, 2);
        addRoute(WASHINGTON, RALEIGH, WILD, 2);
        addRoute(WASHINGTON, RALEIGH, WILD, 2);
        addRoute(RALEIGH, CHARLESTON, WILD, 2);
        addRoute(ATLANTA, CHARLESTON, WILD, 2);
        addRoute(ATLANTA, RALEIGH, WILD, 2);
        addRoute(ATLANTA, RALEIGH, WILD, 2);
        addRoute(ATLANTA, NASHVILLE, WILD, 1);
        addRoute(SAINT_LOUIS, NASHVILLE, WILD, 2);
        addRoute(SAINT_LOUIS, LITTLE_ROCK, WILD, 2);
        addRoute(DALLAS, LITTLE_ROCK, WILD, 2);
        addRoute(DALLAS, HOUSTON, WILD, 1);
        addRoute(DALLAS, HOUSTON, WILD, 1);
        addRoute(NEW_ORLEANS, HOUSTON, WILD, 2);
        addRoute(DALLAS, OK_CITY, WILD, 2);
        addRoute(DALLAS, OK_CITY, WILD, 2);
        addRoute(LITTLE_ROCK, OK_CITY, WILD, 2);
        addRoute(KANSAS_CITY, OK_CITY, WILD, 2);
        addRoute(KANSAS_CITY, OK_CITY, WILD, 2);
        addRoute(KANSAS_CITY, OMAHA, WILD, 1);
        addRoute(KANSAS_CITY, OMAHA, WILD, 1);
        addRoute(DULUTH, OMAHA, WILD, 2);
        addRoute(DULUTH, OMAHA, WILD, 2);
        addRoute(DULUTH, SAULT_ST_MARIE, WILD, 3);
        addRoute(DENVER, SANTA_FE, WILD, 2);
        addRoute(EL_PASO, SANTA_FE, WILD, 2);
        addRoute(EL_PASO, PHOENIX, WILD, 3);
        addRoute(SANTA_FE, PHOENIX, WILD, 3);
        addRoute(LOS_ANGELES, PHOENIX, WILD, 3);
        addRoute(LOS_ANGELES, LAS_VEGAS, WILD, 2);
        addRoute(PORTLAND, SEATTLE, WILD, 1);
        addRoute(PORTLAND, SEATTLE, WILD, 1);
        addRoute(VANCOUVER, SEATTLE, WILD, 1);
        addRoute(VANCOUVER, SEATTLE, WILD, 1);
        addRoute(CALGARY, SEATTLE, WILD, 4);
        addRoute(CALGARY, HELENA, WILD, 4);

        // white routes
        addRoute(CALGARY, WINNIPEG, WHITE, 6);
        addRoute(CHICAGO, TORONTO, WHITE, 4);
        addRoute(PITTSBURGH, NEW_YORK, WHITE, 2);
        addRoute(LITTLE_ROCK, NASHVILLE, WHITE, 3);
        addRoute(PHOENIX, DENVER, WHITE, 5);
        addRoute(SAN_FRANCISCO, SLC, WHITE, 5);

        // black routes
        addRoute(WINNIPEG, DULUTH, BLACK, 4);
        addRoute(SAULT_ST_MARIE, MONTREAL, BLACK, 5);
        addRoute(NEW_YORK, WASHINGTON, BLACK, 2);
        addRoute(CHICAGO, PITTSBURGH, BLACK, 3);
        addRoute(NASHVILLE, RALEIGH, BLACK, 3);
        addRoute(KANSAS_CITY, DENVER, BLACK, 4);
        addRoute(LOS_ANGELES, EL_PASO, BLACK, 6);

        // yellow routes
        addRoute(SEATTLE, HELENA, YELLOW, 6);
        addRoute(BOSTON, NEW_YORK, YELLOW, 2);
        addRoute(PITTSBURGH, NASHVILLE, YELLOW, 4);
        addRoute(NEW_ORLEANS, ATLANTA, YELLOW, 4);
        addRoute(OK_CITY, EL_PASO, YELLOW, 5);
        addRoute(SLC, DENVER, YELLOW, 3);
        addRoute(LOS_ANGELES, SAN_FRANCISCO, YELLOW, 3);

        // blue routes
        addRoute(PORTLAND, SLC, BLUE, 6);
        addRoute(HELENA, WINNIPEG, BLUE, 4);
        addRoute(OMAHA, CHICAGO, BLUE, 4);
        addRoute(MONTREAL, NEW_YORK, BLUE, 3);
        addRoute(KANSAS_CITY, SAINT_LOUIS, BLUE, 2);
        addRoute(ATLANTA, MIAMI, BLUE, 5);
        addRoute(SANTA_FE, OK_CITY, BLUE, 3);

        // red routes
        addRoute(HELENA, OMAHA, RED, 5);
        addRoute(DULUTH, CHICAGO, RED, 3);
        addRoute(BOSTON, NEW_YORK, RED, 2);
        addRoute(NEW_ORLEANS, MIAMI, RED, 6);
        addRoute(DALLAS, EL_PASO, RED, 4);
        addRoute(OK_CITY, DENVER, RED, 4);
        addRoute(SLC, DENVER, RED, 3);

        // orange routs
        addRoute(HELENA, DULUTH, ORANGE, 6);
        addRoute(CHICAGO, PITTSBURGH, ORANGE, 3);
        addRoute(NEW_YORK, WASHINGTON, ORANGE, 2);
        addRoute(NEW_ORLEANS, ATLANTA, ORANGE, 4);
        addRoute(DENVER, KANSAS_CITY, ORANGE, 4);
        addRoute(LAS_VEGAS, SLC, ORANGE, 3);
        addRoute(SAN_FRANCISCO, SLC, ORANGE, 5);

        // purple routes
        addRoute(HELENA, SLC, PURPLE, 3);
        addRoute(DENVER, OMAHA, PURPLE, 4);
        addRoute(KANSAS_CITY, SAINT_LOUIS, PURPLE, 2);
        addRoute(DULUTH, TORONTO, PURPLE, 6);
        addRoute(CHARLESTON, MIAMI, PURPLE, 4);
        addRoute(LOS_ANGELES, SAN_FRANCISCO, PURPLE, 3);
        addRoute(SAN_FRANCISCO, PORTLAND, PURPLE, 5);

        // green routes
        addRoute(NEW_YORK, PITTSBURGH, GREEN, 2);
        addRoute(SAINT_LOUIS, PITTSBURGH, GREEN, 5);
        addRoute(SAINT_LOUIS, CHICAGO, GREEN, 2);
        addRoute(LITTLE_ROCK, NEW_ORLEANS, GREEN, 3);
        addRoute(EL_PASO, HOUSTON, GREEN, 6);
        addRoute(DENVER, HELENA, GREEN, 4);
        addRoute(SAN_FRANCISCO, PORTLAND, GREEN, 5);
    }

    public List<Route> getRoutes() {
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

    public Map<City, ArrayList<City>> getCityConnectionMap() {
        return cityConnectionMap;
    }
}

