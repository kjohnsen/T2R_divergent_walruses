package presenter.decks;

public class DecksWaiting extends DecksState {

    private static DecksWaiting instance = new DecksWaiting();
    public static DecksWaiting getInstance() { return instance; }
    private DecksWaiting() {}

    public String drawDestinationCards(DecksPresenter presenter) {
        return "It's not your turn";
    }

    public String drawTrainCard(DecksPresenter presenter) {
        return "It's not your turn";
    }
    
    public String selectTrainCard(DecksPresenter presenter, int index) {
        return "It's not your turn";
    }
}
