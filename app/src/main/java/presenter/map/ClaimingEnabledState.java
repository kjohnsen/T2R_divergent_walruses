package presenter.map;

import java.util.ArrayList;

import model.UIFacade;
import modelclasses.Route;
import modelclasses.TrainCardColor;
import presenter.ChooseClaimColorPresenter;

public class ClaimingEnabledState extends MapPresenterState
        implements ChooseClaimColorPresenter.ChooseClaimColorCaller {
    private static final ClaimingEnabledState ourInstance = new ClaimingEnabledState();

    public static ClaimingEnabledState getInstance() {
        return ourInstance;
    }

    private ClaimingEnabledState() {}

    @Override
    public void routeClicked(Route route) {
        super.routeClicked(route);
        if (route.getColor().equals(TrainCardColor.WILD)) {
            view.queryUserForClaimColor(route, this);
        } else {
            String result = this.uiFacade.claimRoute(route);
            if (result != null) {
                this.view.displayMessage(result);
            }
        }
    }

    @Override
    public void claimColorChosen(Route route, TrainCardColor color) {
        route.setColor(color);
        String result = this.uiFacade.claimRoute(route);
        if (result != null) {
            this.view.displayMessage(result);
        }
    }

    @Override
    public void enter() {
        super.enter();
        ArrayList<Route> availableRoutes = this.uiFacade.getAvailableRoutes();
        this.view.emphasizeSelectRoutes(availableRoutes);
    }
}
