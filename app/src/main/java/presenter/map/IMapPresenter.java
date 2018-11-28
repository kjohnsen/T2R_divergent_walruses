package presenter.map;

import model.IUIFacade;
import modelclasses.Route;
import presenter.IChooseClaimColorPresenter;

public interface IMapPresenter {
    void routeClicked(Route route);
    void setUiFacade(IUIFacade uiFacade);
    void setInitialState();
}
