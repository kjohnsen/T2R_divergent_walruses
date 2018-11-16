package presenter.map;

import fragment.IMapView;
import modelclasses.Route;

public class MapPresenterState {
    protected IMapPresenter presenter;
    protected IMapView view;

    public void setPresenter(IMapPresenter presenter) {
        this.presenter = presenter;
    }

    public void setView(IMapView view) {
        this.view = view;
    }

    public void routeClicked(Route route) {}

    public void enter() {}

    public void exit() {}
}
