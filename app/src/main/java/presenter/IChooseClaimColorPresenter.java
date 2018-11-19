package presenter;

import java.util.Set;

import modelclasses.Route;
import modelclasses.TrainCardColor;

public interface IChooseClaimColorPresenter {
    void onSwitchView();
    void chooseClaimColor(TrainCardColor color);
}
