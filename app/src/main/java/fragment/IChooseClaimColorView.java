package fragment;

import java.util.Set;

import modelclasses.TrainCardColor;
import presenter.IChooseClaimColorPresenter;

public interface IChooseClaimColorView {
    void setPresenter(IChooseClaimColorPresenter presenter);
    void displayPossibleColors(Set<TrainCardColor> colors);
}
