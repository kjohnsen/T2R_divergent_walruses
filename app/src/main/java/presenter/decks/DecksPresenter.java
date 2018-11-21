package presenter.decks;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fragment.IDecksView;
import model.ClientModel;
import model.UIFacade;
import modelclasses.Route;
import modelclasses.TrainCard;
import modelclasses.TrainCardWrapper;

public class DecksPresenter implements IDecksPresenter, Observer {

    private IDecksView view;
    private DecksState state;

    /**
     * Register as an observer of the ClientModel, and set the view.
     * @param view the view connected to the presenter
     */
    public DecksPresenter(IDecksView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
        if (UIFacade.getInstance().isCurrentPlayer()) {
            setState(DecksNoCardsDrawn.getInstance());
        } else {
            setState(DecksWaiting.getInstance());
        }
    }

    public IDecksView getView() {
        return view;
    }

    public void setState(DecksState newState) {
        state = newState;
    }

    /**
     * Queries the ClientModel to see whether to draw destination cards immediately to start the game.
     * @return whether it's the start of the game
     */
    @Override
    public boolean isGameStart() { return state.isGameStart(this); }

    /**
     * Retrieves the faceup cards for the view.
     */
    @Override
    public void getFaceupCards() { state.getFaceupCards(this); }

    /**
     * Select a faceup card to put in the player's hand.
     * @pre there must be faceup cards to select
     * @pre index must be between 0 and 4
     * @post there will be a new faceup card in place of the old one
     * @post the train card deck will be one card smaller
     * @post a new train card will be in the player's hand
     * @param index of the card to get from the faceup cards
     * @return an error message if there is one, or null otherwise
     */
    @Override
    public String selectTrainCard(int index) {
        return state.selectTrainCard(this, index); }

    /**
     * Draws a card from the train card deck.
     * @pre the train card deck is not empty
     * @post the train card deck will be one card smaller
     * @post a new train card will be in the player's hand
     * @return an error message if there is one, or null otherwise
     */
    @Override
    public String drawTrainCard() { return state.drawTrainCard(this); }

    /**
     * Goes to the destination cards view.
     */
    @Override
    public String drawDestinationCards() { return state.drawDestinationCards(this); }

    /**
     * Unregisters the DecksPresenter as an observer of the ClientModel.
     */
    @Override
    public void onSwitchView() { state.onSwitchView(this); }

    /**
     * Checks to see if the update the ClientModel sent out concerns the decks view. The only
     * updates we care about (at least for now) are whether the faceup cards changed, so we check
     * to see if the object is a TrainCardWrapper of type FaceUp; if it is, we need to update
     * the view with the new cards.
     * @param observable the ClientModel
     * @param o the object the ClientModel passed
     */
    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof TrainCardWrapper) {
            TrainCardWrapper wrapper = (TrainCardWrapper) o;
            if (wrapper.getDeckType().equals(TrainCardWrapper.DeckType.FaceUp)) {
                view.replaceTrainCards(wrapper.getCards());
            }
        } else if (o instanceof String) {
            if (UIFacade.getInstance().getUsername().equals(o)) {
                setState(DecksNoCardsDrawn.getInstance());
            }
        } else if(o instanceof Route) {
            setState(DecksWaiting.getInstance());
        }
    }
}
