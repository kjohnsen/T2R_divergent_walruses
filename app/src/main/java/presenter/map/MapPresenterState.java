package presenter.map;

import fragment.IMapView;
import modelclasses.Route;

public class MapPresenterState {
    private static final MapPresenterState ourInstance = new MapPresenterState();
    protected IMapPresenter presenter;
    protected IMapView view;

    public void setPresenter(IMapPresenter presenter) {
        this.presenter = presenter;
    }

    public void setView(IMapView view) {
        this.view = view;
    }

    public static MapPresenterState getInstance() {
        return ourInstance;
    }

    protected MapPresenterState() {
    }

    public void routeClicked(Route route) {}

    public void enter() {}

    public void exit() {}
}
