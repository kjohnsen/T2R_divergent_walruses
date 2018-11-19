package fragment;

import java.util.Set;

import modelclasses.TrainCardColor;

public interface IChooseClaimColorView {
    void displayPossibleColors(Set<TrainCardColor> colors);
}
