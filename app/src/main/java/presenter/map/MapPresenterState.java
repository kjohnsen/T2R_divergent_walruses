package presenter.map;

import fragment.IMapView;
import model.IUIFacade;
import model.UIFacade;
import modelclasses.Route;

public class MapPresenterState {
    protected IMapPresenter presenter;
    protected IMapView view;
    protected IUIFacade uiFacade = UIFacade.getInstance();

    public void setPresenter(IMapPresenter presenter) {
        this.presenter = presenter;
    }

    public void setView(IMapView view) {
        this.view = view;
    }

    public void setUiFacade(IUIFacade uiFacade) {
        this.uiFacade = uiFacade;
    }

    public void routeClicked(Route route) {}

    public void enter() {}

    public void exit() {}
}
