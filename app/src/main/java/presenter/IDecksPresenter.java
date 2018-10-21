package presenter;

public interface IDecksPresenter {
    void getFaceupCards();
    String drawTrainCard();
    String selectTrainCard(int index);
    void drawDestinationCards();
    void onSwitchView();
}
