package presenter.map;

import java.util.ArrayList;

import fragment.ChooseClaimColorFragment;
import fragment.IMapView;
import fragment.MapFragment;
import modelclasses.Atlas;
import modelclasses.MapSetup;
import modelclasses.Route;
import modelclasses.TrainCardColor;
import presenter.ChooseClaimColorPresenter;

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
    public void moveToEndGame() {

    }

    @Override
    public void queryUserForClaimColor(Route route, ChooseClaimColorPresenter.ChooseClaimColorCaller caller) {
        if (route.getDestination().equals(Atlas.LOS_ANGELES)) {
            caller.claimColorChosen(route, TrainCardColor.RED);
        }
    }
}
