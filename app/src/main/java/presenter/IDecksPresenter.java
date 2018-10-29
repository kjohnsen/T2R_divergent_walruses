package presenter;

public interface IDecksPresenter {
    void getFaceupCards();
    String drawTrainCard();
    String selectTrainCard(int index);
    String drawDestinationCards();
    void onSwitchView();
    boolean isGameStart();
}
