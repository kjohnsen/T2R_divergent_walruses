package presenter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fragment.IChooseClaimColorView;
import model.ClientModel;
import modelclasses.Player;
import modelclasses.Route;
import modelclasses.TrainCardColor;

public class ChooseClaimColorPresenter implements IChooseClaimColorPresenter {
    private ChooseClaimColorCaller caller;
    private Route route;
    private IChooseClaimColorView view;

    public ChooseClaimColorPresenter(IChooseClaimColorView view, Route route,
                                     ChooseClaimColorCaller caller) {
        this.view = view;
        this.route = route;
        this.caller = caller;
        //view.displayPossibleColors(getPossibleColors(route));
    }

    public Set<TrainCardColor> getPossibleColors() {
        Set<TrainCardColor> result = new HashSet<>();
        int length = route.getLength();
        Map<TrainCardColor, Integer> colorQuantityMap =
                Player.getTrainCardQuantities(ClientModel.getInstance().getPlayerTrainCards());
        for (TrainCardColor color : colorQuantityMap.keySet()) {
            if (colorQuantityMap.get(color) >= length) {
                result.add(color);
            }
        }
        return result;
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
