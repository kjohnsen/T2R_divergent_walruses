package fragment;

import java.util.List;

import modelclasses.MapSetup;
import modelclasses.Player;
import modelclasses.Route;


public interface IMapView {
    /**
     * Initializes the map, drawing the routes and cities on the view.
     * @param mapSetup a MapSetup object defining the routes of the map and their component
     *                 cities.
     */
    public void initializeMap(MapSetup mapSetup);

    /**
     * Signals that the display for a given route needs to be updated, as when claimed by a
     * player, for example.
     * @param route the Route object whose display needs to be udpated.
     */
    public void updateRoute(Route route);

    /**
     * Emphasizes or de-emphasizes a route in the view. Could be used to indicate routes which
     * are available for claiming, for example.
     * @param route the Route whose display is to be modified
     * @param enabled a boolean indicated whether emphasis or deemphasis is desired.
     */
    public void setRouteEmphasized(Route route, boolean enabled);

}
