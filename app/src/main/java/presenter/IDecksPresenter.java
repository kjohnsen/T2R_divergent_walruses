package presenter;

public interface IDecksPresenter {
    String drawTrainCard();
    String selectTrainCard(int index);
    String drawDestinationCards();
    void onSwitchView();
}
