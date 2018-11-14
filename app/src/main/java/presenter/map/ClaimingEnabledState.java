package presenter.map;

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
    }
}
