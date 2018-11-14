package presenter.map;

import java.util.Observable;
import java.util.Observer;

import fragment.IMapView;
import model.ClientModel;
import modelclasses.Route;

public class MapPresenter implements IMapPresenter, Observer {
    IMapView mapView;
    MapPresenterState state;

    public MapPresenter(IMapView mapView) {
        this.mapView = mapView;
        this.setState(ClaimingDisabledState.getInstance());
        ClientModel.getInstance().addObserver(this);
    }

    public void setState(MapPresenterState state) {
        this.state.exit();
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
        }
    }
}
