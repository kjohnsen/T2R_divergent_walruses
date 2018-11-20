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
        ChooseClaimColorTask chooseClaimColorTask = new ChooseClaimColorTask();
        chooseClaimColorTask.execute(route);
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
            return uiFacade.claimRoute(routes[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null) {
                view.displayMessage(s);
            }
        }
    }

    private class ChooseClaimColorTask extends AsyncTask<Route, Void, String> {

        @Override
        protected String doInBackground(Route... routes) {
            return uiFacade.claimRoute(routes[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null) {
                view.displayMessage(s);
            }
        }
    }
}
