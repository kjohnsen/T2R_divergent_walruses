package modelclasses;

import java.util.ArrayList;
import java.util.Random;

public class Atlas {
    public static final int NUM_CITIES = 36;
    public static final City ATLANTA = new City("Atlanta", 33.75, -84.39);
    public static final City BOSTON = new City("Boston", 42.32, -71.08);
    public static final City CALGARY = new City("Calgary", 51.08, -114.08);
    public static final City CHARLESTON = new City("Charleston", 38.35, -81.63);
    public static final City CHICAGO = new City("Chicago", 41.84, -87.69);
    public static final City DALLAS = new City("Dallas", 33.92, -84.84);
    public static final City DENVER = new City("Denver", 39.76, -104.88);
    public static final City DULUTH = new City("Duluth", 46.78, -92.13);
    public static final City EL_PASO = new City("El Paso", 31.85, -106.43);
    public static final City HELENA = new City("Helena", 46.60, -112.02);
    public static final City HOUSTON = new City("Houston", 29.79, -95.39);
    public static final City KANSAS_CITY = new City("Kansas City", 39.12, -94.55);
    public static final City LAS_VEGAS = new City("Las Vegas", 36.23, -115.26);
    public static final City LITTLE_ROCK = new City("Little Rock", 34.73, -92.36);
    public static final City LOS_ANGELES = new City("Los Angeles", 34.11, -118.41);
    public static final City MIAMI = new City("Miami", 25.82, -80.21);
    public static final City MONTREAL = new City("Montreal", 45.5, -73.58);
    public static final City NASHVILLE = new City("Nashville", 36.17, -86.78);
    public static final City NEW_ORLEANS = new City("New Orleans", 30.07, -89.93);
    public static final City NEW_YORK = new City("New York", 40.69, -73.92);
    public static final City OK_CITY = new City("Oklahoma City", 35.47, -97.51);
    public static final City OMAHA = new City("Omaha", 41.26, -96.05);
    public static final City PHOENIX = new City("Phoenix", 33.57, -112.09);
    public static final City PITTSBURGH = new City("Pittsburgh", 40.44, -79.98);
    public static final City PORTLAND = new City("Portland", 45.54, -122.65);
    public static final City RALEIGH = new City("Raleigh", 35.83, -78.64);
    public static final City SAINT_LOUIS = new City("Saint Louis", 38.64, -90.25);
    public static final City SLC = new City("Salt Lake City", 40.78, -111.93);
    public static final City SAN_FRANCISCO = new City("San Francisco", 37.76, -122.443);
    public static final City SANTA_FE = new City("Santa Fe", 35.66, -105.98);
    public static final City SAULT_ST_MARIE= new City("Sault St. Marie", 46.5, -84.33);
    public static final City SEATTLE = new City("Seattle", 47.62, -122.32);
    public static final City TORONTO = new City("Toronto", 43.7, -79.42);
    public static final City VANCOUVER = new City("Vancouver", 49.27, -123.12);
    public static final City WASHINGTON = new City("Washington", 38.9, -77.02);
    public static final City WINNIPEG = new City("Winnipeg", 49.88, -97.17);

    //Tickets for the base game
    public static final DestinationCard TICKET0 = new DestinationCard(4, DENVER, EL_PASO);
    public static final DestinationCard TICKET1 = new DestinationCard(5, KANSAS_CITY, HOUSTON);
    public static final DestinationCard TICKET2 = new DestinationCard(6, NEW_YORK, ATLANTA);
    public static final DestinationCard TICKET3 = new DestinationCard(7, CHICAGO, NEW_ORLEANS);
    public static final DestinationCard TICKET4 = new DestinationCard(7, CALGARY, SLC);
    public static final DestinationCard TICKET5 = new DestinationCard(8, DULUTH, HOUSTON);
    public static final DestinationCard TICKET6 = new DestinationCard(8, SAULT_ST_MARIE, NASHVILLE);
    public static final DestinationCard TICKET7 = new DestinationCard(8, HELENA, LOS_ANGELES);
    public static final DestinationCard TICKET8 = new DestinationCard(9, MONTREAL, ATLANTA);
    public static final DestinationCard TICKET9 = new DestinationCard(9, SAULT_ST_MARIE, OK_CITY);
    public static final DestinationCard TICKET10 = new DestinationCard(9, SEATTLE, LOS_ANGELES);
    public static final DestinationCard TICKET11 = new DestinationCard(9, CHICAGO, SANTA_FE);
    public static final DestinationCard TICKET12 = new DestinationCard(10, DULUTH, EL_PASO);
    public static final DestinationCard TICKET13 = new DestinationCard(10, TORONTO, MIAMI);
    public static final DestinationCard TICKET14 = new DestinationCard(11, PORTLAND, PHOENIX);
    public static final DestinationCard TICKET15 = new DestinationCard(11, DALLAS, NEW_YORK);
    public static final DestinationCard TICKET16 = new DestinationCard(11, DENVER, PITTSBURGH);
    public static final DestinationCard TICKET17 = new DestinationCard(11, WINNIPEG, LITTLE_ROCK);
    public static final DestinationCard TICKET18 = new DestinationCard(12, WINNIPEG, HOUSTON);
    public static final DestinationCard TICKET19 = new DestinationCard(12, BOSTON, MIAMI);
    public static final DestinationCard TICKET20 = new DestinationCard(13, VANCOUVER, SANTA_FE);
    public static final DestinationCard TICKET21 = new DestinationCard(13, CALGARY, PHOENIX);
    public static final DestinationCard TICKET22 = new DestinationCard(13, MONTREAL, NEW_ORLEANS);
    public static final DestinationCard TICKET23 = new DestinationCard(16, CHICAGO, LOS_ANGELES);
    public static final DestinationCard TICKET24 = new DestinationCard(17, SAN_FRANCISCO, ATLANTA);
    public static final DestinationCard TICKET25 = new DestinationCard(17, PORTLAND, NASHVILLE);
    public static final DestinationCard TICKET26 = new DestinationCard(20, VANCOUVER, MONTREAL);
    public static final DestinationCard TICKET27 = new DestinationCard(19, LOS_ANGELES, MIAMI);
    public static final DestinationCard TICKET28 = new DestinationCard(20, NEW_YORK, LOS_ANGELES);
    public static final DestinationCard TICKET29 = new DestinationCard(22, NEW_YORK, SEATTLE);
    //Tickets for the expansion
    public static final DestinationCard TICKET30 = new DestinationCard(4, BOSTON, WASHINGTON);
    public static final DestinationCard TICKET31 = new DestinationCard(14, CALGARY, NASHVILLE);
    public static final DestinationCard TICKET32 = new DestinationCard(5, CHICAGO, ATLANTA);
    public static final DestinationCard TICKET33 = new DestinationCard(7, CHICAGO, BOSTON);
    public static final DestinationCard TICKET34 = new DestinationCard(5, CHICAGO, NEW_YORK);
    public static final DestinationCard TICKET35 = new DestinationCard(6, DENVER, SAINT_LOUIS);
    public static final DestinationCard TICKET36 = new DestinationCard(7, DULUTH, DALLAS);
    public static final DestinationCard TICKET37 = new DestinationCard(10, HOUSTON, WASHINGTON);
    public static final DestinationCard TICKET38 = new DestinationCard(11, KANSAS_CITY, BOSTON);
    public static final DestinationCard TICKET39 = new DestinationCard(19, LAS_VEGAS, NEW_YORK);
    public static final DestinationCard TICKET40 = new DestinationCard(21, LAS_VEGAS, MIAMI);
    public static final DestinationCard TICKET41 = new DestinationCard(12, CALGARY, LOS_ANGELES);
    public static final DestinationCard TICKET42 = new DestinationCard(9, LOS_ANGELES, OK_CITY);
    public static final DestinationCard TICKET43 = new DestinationCard(7, MONTREAL, CHICAGO);
    public static final DestinationCard TICKET44 = new DestinationCard(13, MONTREAL, DALLAS);
    public static final DestinationCard TICKET45 = new DestinationCard(7, MONTREAL, RALEIGH);
    public static final DestinationCard TICKET46 = new DestinationCard(6, NEW_YORK, NASHVILLE);
    public static final DestinationCard TICKET47 = new DestinationCard(10, NEW_YORK, MIAMI);
    public static final DestinationCard TICKET48 = new DestinationCard(8, OMAHA, NEW_ORLEANS);
    public static final DestinationCard TICKET49 = new DestinationCard(19, PHOENIX, BOSTON);
    public static final DestinationCard TICKET50 = new DestinationCard(8, PITTSBURGH, NEW_ORLEANS);
    public static final DestinationCard TICKET51 = new DestinationCard(16, PORTLAND, HOUSTON);
    public static final DestinationCard TICKET52 = new DestinationCard(19, PORTLAND, PITTSBURGH);
    public static final DestinationCard TICKET53 = new DestinationCard(8, MIAMI, SAINT_LOUIS);
    public static final DestinationCard TICKET54 = new DestinationCard(11, CHICAGO, SLC);
    public static final DestinationCard TICKET55 = new DestinationCard(7, KANSAS_CITY, SLC);
    public static final DestinationCard TICKET56 = new DestinationCard(17, SAN_FRANCISCO, SAULT_ST_MARIE);
    public static final DestinationCard TICKET57 = new DestinationCard(21, SAN_FRANCISCO, WASHINGTON);
    public static final DestinationCard TICKET58 = new DestinationCard(12, SAULT_ST_MARIE, MIAMI);
    public static final DestinationCard TICKET59 = new DestinationCard(10, LAS_VEGAS, SEATTLE);
    public static final DestinationCard TICKET60 = new DestinationCard(14, OK_CITY, SEATTLE);
    public static final DestinationCard TICKET61 = new DestinationCard(6, TORONTO, CHARLESTON);
    public static final DestinationCard TICKET62 = new DestinationCard(11, DENVER, VANCOUVER);
    public static final DestinationCard TICKET63 = new DestinationCard(13, DULUTH, VANCOUVER);
    public static final DestinationCard TICKET64 = new DestinationCard(2, PORTLAND, VANCOUVER);
    public static final DestinationCard TICKET65 = new DestinationCard(4, WASHINGTON, ATLANTA);
    public static final DestinationCard TICKET66 = new DestinationCard(6, WINNIPEG, OMAHA);
    public static final DestinationCard TICKET67 = new DestinationCard(10, WINNIPEG, SANTA_FE);

    //method to build cities array
    public static City[] getCities() {
        return new City[] { ATLANTA, BOSTON, CALGARY, CHARLESTON, CHICAGO, DALLAS, DENVER, DULUTH, EL_PASO,
        HELENA, HOUSTON, KANSAS_CITY, LAS_VEGAS, LITTLE_ROCK, LOS_ANGELES, MIAMI, MONTREAL, NASHVILLE,
        NEW_ORLEANS, NEW_YORK, OK_CITY, OMAHA, PHOENIX, PITTSBURGH, PORTLAND, RALEIGH, SAINT_LOUIS,
        SAN_FRANCISCO, SANTA_FE, SAULT_ST_MARIE, SLC, SEATTLE, TORONTO, VANCOUVER, WASHINGTON, WINNIPEG };
    }

    //method to get standard destinations
    public static DestinationCard[] getDestinations() {
        return new DestinationCard[] { TICKET0, TICKET1, TICKET2, TICKET3, TICKET4, TICKET5, TICKET6,
                TICKET7, TICKET8, TICKET9, TICKET10, TICKET11, TICKET12, TICKET13, TICKET14, TICKET15,
                TICKET16, TICKET17, TICKET18, TICKET19, TICKET20, TICKET21, TICKET22, TICKET23, TICKET24,
                TICKET25, TICKET26, TICKET27, TICKET28, TICKET29 };
    }
    //method to get standard and expanded destinations
    public static DestinationCard[] getExpandedDestinations() {
        return new DestinationCard[] { TICKET0, TICKET1, TICKET2, TICKET3, TICKET4, TICKET5, TICKET6,
                TICKET7, TICKET8, TICKET9, TICKET10, TICKET11, TICKET12, TICKET13, TICKET14, TICKET15,
                TICKET16, TICKET17, TICKET18, TICKET19, TICKET20, TICKET21, TICKET22, TICKET23, TICKET24,
                TICKET25, TICKET26, TICKET27, TICKET28, TICKET29, TICKET30, TICKET31, TICKET32, TICKET33,
                TICKET34, TICKET35, TICKET36, TICKET37, TICKET38, TICKET39, TICKET40, TICKET41, TICKET42,
                TICKET43, TICKET44, TICKET45, TICKET46, TICKET47, TICKET48, TICKET49, TICKET50, TICKET51,
                TICKET52, TICKET53, TICKET54, TICKET55, TICKET56, TICKET57, TICKET58, TICKET59, TICKET60,
                TICKET61, TICKET62, TICKET63, TICKET64, TICKET65, TICKET66, TICKET67};
    }
    //method to random generate numCards number of destinations
    public static DestinationCard[] getRandomDestinations(int numCards) {
        ArrayList<DestinationCard> cards = new ArrayList<>();
        City cities[] = getCities();
        for (int i = 0; i < numCards; i++) {
            Random rand = new Random(System.currentTimeMillis());
            int city1 = rand.nextInt(NUM_CITIES);
            int city2 = rand.nextInt(NUM_CITIES);
            //make sure they're not the same city
            while (city2 == city1) {
                city2 = rand.nextInt(NUM_CITIES);
            }
            //TODO: use graph algorithm to generate correct number of points
            int points = rand.nextInt(18) + 4;
            DestinationCard card = new DestinationCard(points, cities[city1], cities[city2]);
            //only add the card if it's not already in the deck
            if (!cards.contains(card)) {
                cards.add(card);
            } else {
                i--;
            }
        }
        return cards.toArray(new DestinationCard[0]);
    }
}
