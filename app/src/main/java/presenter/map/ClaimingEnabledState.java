package presenter.map;

import java.util.ArrayList;

import model.UIFacade;
import modelclasses.Route;

public class ClaimingEnabledState extends MapPresenterState {
    private static final ClaimingEnabledState ourInstance = new ClaimingEnabledState();

    public static ClaimingEnabledState getInstance() {
        return ourInstance;
    }

    private ClaimingEnabledState() {}

    @Override
    public void routeClicked(Route route) {
        super.routeClicked(route);
        String result = UIFacade.getInstance().claimRoute(route);
        if (result != null) {
            this.view.displayMessage(result);
        }
    }

    @Override
    public void enter() {
        super.enter();
        ArrayList<Route> availableRoutes = UIFacade.getInstance().getAvailableRoutes();
        this.view.emphasizeSelectRoutes(availableRoutes);
    }
}
