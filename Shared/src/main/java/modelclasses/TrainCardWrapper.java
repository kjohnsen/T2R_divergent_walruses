package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;

public class TrainCardWrapper implements Serializable{

    public enum DeckType implements Serializable {
        FaceUp,
        DrawDeck,
        DiscardDeck,
        PlayerCards,
    }

    ArrayList<TrainCard> cards;
    DeckType deckType;

    public TrainCardWrapper(ArrayList<TrainCard> cards, DeckType deckType) {
        this.cards = cards;
        this.deckType = deckType;
    }

    public ArrayList<TrainCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<TrainCard> cards) {
        this.cards = cards;
    }

    public DeckType getDeckType() {
        return deckType;
    }

    public void setDeckType(DeckType deckType) {
        this.deckType = deckType;
    }
}
