package fragment;

import java.util.List;

import modelclasses.MapSetup;
import modelclasses.Player;
import modelclasses.Route;

public interface IMapView {
    public void initializeMap(MapSetup mapSetup);

    public void updateRoute(Route route);

    public void setRouteEmphasized(Route route, boolean enabled);

}
