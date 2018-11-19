package presenter;

import java.util.Set;

import modelclasses.Route;
import modelclasses.TrainCardColor;

public class ChooseClaimColorPresenter implements IChooseClaimColorPresenter {
    private ChooseClaimColorCaller caller;
    private Route route;

    public ChooseClaimColorPresenter(Route route, ChooseClaimColorCaller caller) {
        this.route = route;
        this.caller = caller;
    }

    @Override
    public Set<TrainCardColor> getPossibleColors() {
        return null;
    }

    @Override
    public void onSwitchView() {

    }

    @Override
    public void chooseClaimColor(TrainCardColor color) {
        this.caller.claimColorChosen(route, color);
    }

    public interface ChooseClaimColorCaller {
        void claimColorChosen(Route route, TrainCardColor color);
    }

}
