package modelclasses;

import java.util.ArrayList;

public class DestinationCardWrapper {
    ArrayList<DestinationCard> destinationCards;
    Boolean isDeck;

    DestinationCardWrapper(){
        destinationCards = new ArrayList<>();
        isDeck = false;
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
