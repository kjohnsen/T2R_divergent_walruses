package presenter;

import modelclasses.TrainCardColor;
import presenter.map.IMapPresenter;

public class ChooseClaimColorPresenter implements IChooseClaimColorPresenter {
    private IMapPresenter mapPresenter;

    public ChooseClaimColorPresenter(IMapPresenter mapPresenter) {
        this.mapPresenter = mapPresenter;
    }

    @Override
    public void selectColor(TrainCardColor color) {

    }
}
