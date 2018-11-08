package presenter.decks;

public interface IDecksPresenter {

    /**
     * Retrieves the faceup cards for the view.
     */
    void getFaceupCards();

    /**
     * Draws a card from the train card deck.
     * @pre the train card deck is not empty
     * @post the train card deck will be one card smaller
     * @post a new train card will be in the player's hand
     * @return an error message if there is one, or null otherwise
     */
    String drawTrainCard();

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
    String selectTrainCard(int index);

    /**
     * Goes to the destination cards view.
     * @return an error message if there is one, or null otherwise
     */
    String drawDestinationCards();

    /**
     * Unregisters the DecksPresenter as an observer of the ClientModel.
     */
    void onSwitchView();

    /**
     * Queries the ClientModel to see whether to draw destination cards immediately to start the game.
     * @return whether it's the start of the game
     */
    boolean isGameStart();
}
