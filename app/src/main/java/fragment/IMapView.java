package fragment;

import java.util.ArrayList;

import modelclasses.MapSetup;
import modelclasses.Route;

/**
 * IMapView is the interface for a view that displays the Ticket to Ride map/gameboard.
 * It should display the map and the given cities/routes, as well as update the display
 * in response to changing route information.
 */
public interface IMapView {
    /**
     * Initializes the map, drawing the routes and cities on the view.
     * @param mapSetup a MapSetup object defining the routes of the map and their component
     *                 cities.
     * @pre mapSetup != null
     * @pre mapSetup.getNorthBound() != null
     * @pre mapSetup.getEastBound() != null
     * @pre mapSetup.getSouthBound() != null
     * @pre mapSetup.getWestBound() != null
     * @pre mapSetup.getNorthBound() >= mapSetup.getSouthBound()
     * @pre mapSetup.getEastBound() >= mapSetup.getWestBound()
     *
     * @post IMapView displays area and routes specified by mapSetup
     */
    public void initializeMap(MapSetup mapSetup);

    /**
     * Signals that the display for a given route needs to be updated, as when claimed by a
     * player, for example.
     * @param route the Route object whose display needs to be updated.
     * @pre route != null
     * @pre IMapView has been initialized with MapSetup object containing route
     * @post IMapView has updated its display of the given route to reflect its current state
     */
    public void updateRoute(Route route);

    /**
     * Emphasizes or de-emphasizes a route in the view. Could be used to indicate routes which
     * are available for claiming, for example.
     *
     * @param route   the Route whose display is to be modified
     * @param enabled a boolean indicated whether emphasis or deemphasis is desired.
     * @pre route != null
     * @pre IMapView has been initialized with MapSetup object containing route
     * @post route is (de)emphasized visually
     */
    public void setRouteEmphasized(Route route, boolean enabled);

    public void emphasizeSelectRoutes(ArrayList<Route> routes);

    public void resetRouteEmphasis();

    public void displayMessage(String message);

    public void moveToEndGame();

}
