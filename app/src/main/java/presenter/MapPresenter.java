package presenter;

import java.util.Observable;
import java.util.Observer;

import fragment.IMapView;
import model.ClientModel;
import modelclasses.Route;

public class MapPresenter implements IMapPresenter, Observer {
    IMapView mapView;
    public MapPresenter(IMapView mapView) {
        mapView = mapView;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void routeClicked(Route route) {

    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof Route) {
            mapView.updateRoute((Route) o);
        }
    }
}
