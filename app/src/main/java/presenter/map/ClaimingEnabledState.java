package presenter.map;

import java.util.ArrayList;

import model.UIFacade;
import modelclasses.Route;

public class ClaimingEnabledState extends MapPresenterState {
    @Override
    public void routeClicked(Route route) {
        super.routeClicked(route);
        UIFacade.getInstance().claimRoute(route);
    }

    @Override
    public void enter() {
        super.enter();
        ArrayList<Route> availableRoutes = UIFacade.getInstance().getAvailableRoutes();
        this.view.emphasizeSelectRoutes(availableRoutes);
    }
}
