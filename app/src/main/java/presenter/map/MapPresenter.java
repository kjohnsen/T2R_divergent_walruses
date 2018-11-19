package presenter.map;

import java.util.Observable;
import java.util.Observer;

import fragment.IMapView;
import model.ClientModel;
import model.IUIFacade;
import model.UIFacade;
import modelclasses.DestinationCardWrapper;
import modelclasses.Player;
import modelclasses.Route;
import modelclasses.TrainCardWrapper;

public class MapPresenter implements IMapPresenter, Observer {
    private IMapView mapView;
    private MapPresenterState state;
    private IUIFacade uiFacade;

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
        } else if (o instanceof Player){ // turn change
            if (((Player) o).getUsername().equals(ClientModel.getInstance().getCurrentUser().getUsername())) {
                this.setState(ClaimingEnabledState.getInstance());
            } else {
                this.setState(ClaimingDisabledState.getInstance());
            }
        } else if (o instanceof DestinationCardWrapper || o instanceof TrainCardWrapper) {
            this.setState(ClaimingDisabledState.getInstance());
        }
    }
}
