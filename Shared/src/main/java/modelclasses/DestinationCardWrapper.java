package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;

public class DestinationCardWrapper implements Serializable {
    ArrayList<DestinationCard> destinationCards;
    DeckType deckType;
    boolean currPlayerCards;

    public enum DeckType implements Serializable {
        DrawDeck,
        DiscardDeck,
        PlayerTickets,
        PreSelectionTickets,
        FirstTimeTickets
    }

    public DestinationCardWrapper(ArrayList<DestinationCard> destinationCards, DeckType deckType) {
        this.destinationCards = destinationCards;
        this.deckType = deckType;
        this.currPlayerCards = false;
    }

    public DestinationCardWrapper(ArrayList<DestinationCard> destinationCards, DeckType deckType, boolean currPlayerCards) {
        this.destinationCards = destinationCards;
        this.deckType = deckType;
        this.currPlayerCards = currPlayerCards;
    }

    public boolean isCurrPlayerCards() {
        return currPlayerCards;
    }

    public ArrayList<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(ArrayList<DestinationCard> destinationCards) {
        this.destinationCards = destinationCards;
    }

    public DeckType getDeckType() {
        return deckType;
    }

    public void setDeckType(DeckType deckType) {
        this.deckType = deckType;
    }
}
