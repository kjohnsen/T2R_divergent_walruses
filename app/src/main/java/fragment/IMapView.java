package fragment;

import java.util.List;

import modelclasses.MapSetup;
import modelclasses.Player;
import modelclasses.Route;

public interface IMapView {
    public void initializeMap(MapSetup mapSetup);

    public void updateClaimedRoute(Player player, Route route);

    public void highlightRoutes(List<Route> routes);

}
