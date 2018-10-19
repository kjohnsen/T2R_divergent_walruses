package presenter;

public interface IDecksPresenter {
    String drawTrainCard();
    String selectTrainCard(int index);
    void drawDestinationCards();
    void onSwitchView();
}
