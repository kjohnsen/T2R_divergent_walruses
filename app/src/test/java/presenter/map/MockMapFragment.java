package presenter.map;

import java.util.ArrayList;

import fragment.IMapView;
import modelclasses.MapSetup;
import modelclasses.Route;

public class MockMapFragment implements IMapView {
    @Override
    public void initializeMap(MapSetup mapSetup) {

    }

    @Override
    public void updateRoute(Route route) {

    }

    @Override
    public void setRouteEmphasized(Route route, boolean enabled) {

    }

    @Override
    public void emphasizeSelectRoutes(ArrayList<Route> routes) {

    }

    @Override
    public void resetRouteEmphasis() {

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void queryUserForClaimColor(Route route) {

    }
}
