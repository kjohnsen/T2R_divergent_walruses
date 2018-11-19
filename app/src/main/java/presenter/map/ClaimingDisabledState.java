package presenter.map;

import java.util.List;

import modelclasses.Route;

public class ClaimingDisabledState extends MapPresenterState {
    private static final ClaimingDisabledState ourInstance = new ClaimingDisabledState();

    public static MapPresenterState getInstance() {
        return ourInstance;
    }

    private ClaimingDisabledState() {}

    @Override
    public void enter() {
        super.enter();
        this.view.resetRouteEmphasis();
    }
}
