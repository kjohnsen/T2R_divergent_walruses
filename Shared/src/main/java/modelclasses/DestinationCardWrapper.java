package modelclasses;

import java.util.ArrayList;

public class DestinationCardWrapper {
    ArrayList<DestinationCard> destinationCards;
    Boolean isDeck;

    public DestinationCardWrapper(ArrayList<DestinationCard> _tickets, Boolean _isDeck){
        isDeck = _isDeck;
        destinationCards = _tickets;
    }

    public ArrayList<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(ArrayList<DestinationCard> destinationCards) {
        this.destinationCards = destinationCards;
    }

    public Boolean getDeck() {
        return isDeck;
    }

    public void setDeck(Boolean deck) {
        isDeck = deck;
    }
}
