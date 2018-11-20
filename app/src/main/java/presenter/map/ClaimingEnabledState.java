package presenter.map;

import android.os.AsyncTask;

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

    private ClaimingEnabledState() {
        this.uiFacade = UIFacade.getInstance();
    }

    @Override
    public void routeClicked(Route route) {
        super.routeClicked(route);
        if (route.getColor().equals(TrainCardColor.WILD)) {
            view.queryUserForClaimColor(route, this);
        } else {
            ClaimRouteTask claimRouteTask = new ClaimRouteTask();
            claimRouteTask.execute(route);
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
        ArrayList<Route> availableRoutes = UIFacade.getInstance().getAvailableRoutes();
        this.view.emphasizeSelectRoutes(availableRoutes);
    }

    private class ClaimRouteTask extends AsyncTask<Route, Void, String> {

        @Override
        protected String doInBackground(Route... routes) {
            return UIFacade.getInstance().claimRoute(routes[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null) {
                System.out.println(s);
            }
        }
    }
}
