package presenter;

import java.util.Set;

import modelclasses.Route;
import modelclasses.TrainCardColor;

public interface IChooseClaimColorPresenter {
    Set<TrainCardColor> getPossibleColors();
    void onSwitchView();
    void chooseClaimColor(TrainCardColor color);
}
