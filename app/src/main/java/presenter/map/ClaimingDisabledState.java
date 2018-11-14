package presenter.map;

import java.util.List;

import modelclasses.Route;

public class ClaimingDisabledState extends MapPresenterState {
    @Override
    public void enter() {
        super.enter();
        this.view.resetRouteEmphasis();
    }
}
