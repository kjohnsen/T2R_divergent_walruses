package presenter.map;

import java.util.Observable;
import java.util.Observer;

import fragment.IMapView;
import model.ClientModel;
import model.UIFacade;
import modelclasses.DestinationCardWrapper;
import modelclasses.Player;
import modelclasses.Route;
import modelclasses.TrainCardWrapper;

public class MapPresenter implements IMapPresenter, Observer {
    IMapView mapView;
    MapPresenterState state;

    public MapPresenter(IMapView mapView) {
        this.mapView = mapView;
        this.setState(ClaimingDisabledState.getInstance());
        ClientModel.getInstance().addObserver(this);
    }

    public void setState(MapPresenterState state) {
        if (this.state != null) this.state.exit();
        this.state = state;
        state.setPresenter(this);
        state.setView(this.mapView);
        this.state.enter();
    }

    @Override
    public void routeClicked(Route route) {
        state.getInstance().routeClicked(route);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof Route) {
            mapView.updateRoute((Route) o);
        } else if (o instanceof Player){ // turn change
            if (((Player) o).getUsername().equals(ClientModel.getInstance().getCurrentUser().getUsername())) {
                this.setState(ClaimingEnabledState.getInstance());
            } else {
                this.setState(ClaimingDisabledState.getInstance());
            }
        } else if (o instanceof DestinationCardWrapper || o instanceof TrainCardWrapper) {
            this.setState(ClaimingDisabledState.getInstance());
        } else if (o instanceof Boolean) {
            if (UIFacade.getInstance().isLastRound()) {
                mapView.displayMessage("Starting Last Round");
            }
            else if (UIFacade.getInstance().isEndGame()) {
                mapView.moveToEndGame();
            }
        }
    }
}
