package presenter.map;

import android.security.keystore.UserNotAuthenticatedException;

import java.util.Observable;
import java.util.Observer;
import fragment.IMapView;
import model.ClientModel;
import model.IUIFacade;
import model.UIFacade;
import modelclasses.DestinationCardWrapper;
import modelclasses.Route;
import modelclasses.TrainCardWrapper;

public class MapPresenter implements IMapPresenter, Observer {
    private IMapView mapView;
    private MapPresenterState state;
    private IUIFacade uiFacade = UIFacade.getInstance();

    public MapPresenter(IMapView mapView) {
        this.mapView = mapView;
        this.setState(ClaimingDisabledState.getInstance());
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void setInitialState() {
        String thisUser = ClientModel.getInstance().getCurrentUser().getUsername();
        String turnUser = ClientModel.getInstance().getCurrentGame().getCurrentPlayer().getUsername();
        if (thisUser.equals(turnUser)) {
            this.setState(ClaimingEnabledState.getInstance());
        }
    }

    public void setState(MapPresenterState state) {
        if (this.state != null) this.state.exit();
        this.state = state;
        state.setPresenter(this);
        state.setView(this.mapView);
        state.setUiFacade(this.uiFacade);
        this.state.enter();
    }

    public IMapView getMapView() {
        return mapView;
    }

    public MapPresenterState getState() {
        return state;
    }

    @Override
    public void routeClicked(Route route) {
        state.routeClicked(route);
    }

    public void setUiFacade(IUIFacade uiFacade) {
        this.uiFacade = uiFacade;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof Route) {
            mapView.updateRoute((Route) o);
        } else if (o instanceof String){ // turn change
            if (o.equals(UIFacade.getInstance().getUsername())) {
                this.setState(ClaimingEnabledState.getInstance());
            } else {
                this.setState(ClaimingDisabledState.getInstance());
            }
        } else if (o instanceof DestinationCardWrapper) {
            DestinationCardWrapper dcw = (DestinationCardWrapper) o;
            if (dcw.getDeckType().equals(DestinationCardWrapper.DeckType.PlayerTickets)) {
                this.setState(ClaimingDisabledState.getInstance());
            }
        } else if (o instanceof TrainCardWrapper) {
            this.setState(ClaimingDisabledState.getInstance());
        } else if (o instanceof Boolean) {
            if (uiFacade.isLastRound() && !uiFacade.isEndGame()) {
                mapView.displayMessage("Starting Last Round");
            }
            else if (uiFacade.isEndGame()) {
                mapView.moveToEndGame();
            }
        }
    }
}
