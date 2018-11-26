package modelclasses;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Arrays;

public class GameInfo implements Serializable {
    private GameName gameName;
    private ArrayList<Player> players = new ArrayList<>();
    private int numPlayers;
    private ArrayList<TrainCard> trainCardDeck = new ArrayList<>();
    private ArrayList<DestinationCard> destCardDeck = new ArrayList<>();
    private ArrayList<TrainCard> faceUpCards = new ArrayList<>();
    private ArrayList<TrainCard> discardedTrainCards = new ArrayList<>();
    private ArrayList<Route> unclaimedRoutes = new ArrayList<>();
    private Player currentPlayer;
    private boolean lastRound;

    public GameInfo(GameName gameName, ArrayList<Player> players, int numPlayers) {
        this.gameName = gameName;
        this.numPlayers = numPlayers;
        setPlayers(players);
        initializeTrainCardDeck();
        setDestCardDeck(new ArrayList<>(Arrays.asList(Atlas.getDestinations())));
        initializeFaceUpCards();
        initializeUnclaimedRoutes();
        lastRound = false;
    }

    public static GameInfo makeRandomGameInfo(){
        ArrayList<Player> players = new ArrayList<>();

        Random r = new Random();
        int max = 5;
        int min = 1;
        int randomNumber = r.nextInt((max - min) + 1) + min;

        for(int i = 0; i < randomNumber; i++){
            PlayerColor color = PlayerColor.values()[new Random().nextInt(PlayerColor.values().length)];
            Player testPlayer = new Player("asdf" + String.valueOf(i),color);

            testPlayer.setDestinationCards(new ArrayList<>(Arrays.asList(Atlas.getRandomDestinations(8))));
            testPlayer.setTrainCards(TrainCard.getRandomNumCards());

            players.add(testPlayer);
        }

        return new GameInfo(new GameName("game name!"),players, 5);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addTrainCardToHand(TrainCard card, Player player) {
        for (Player p : players) {
            if (p.getUsername().equals(player.getUsername())) {
                p.addTrainCardToHand(card);
                return;
            }
        }
    }

    public void addTicketsToHand(ArrayList<DestinationCard> cards, Player player) {
        destCardDeck.removeAll(cards);
        for (Player p : players) {
            if (p.getUsername().equals(player.getUsername())) {
                p.addDestinationCards(cards);
                return;
            }
        }
    }

    public GameName getGameName() {
        return gameName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String username) {
        for (Player p : players) {
            if (p.getUsername().equals(username)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<TrainCard> shuffleTrainDeck() {
        trainCardDeck.addAll(discardedTrainCards);
        discardedTrainCards = new ArrayList<>();
        return trainCardDeck;
    }

    public ArrayList<TrainCard> replaceCards(Integer index) {
        TrainCard card = drawTrainCard();
        faceUpCards.set(index, card);
        ArrayList<TrainCard> replacements = new ArrayList<>();
        while (tooManyWilds()) {
            for (int i = 0; i < 5; i++) {
                TrainCard c = drawTrainCard();
                replacements.add(c);
                discardedTrainCards.add(faceUpCards.get(i));
                faceUpCards.set(i, c);
            }
        }
        if (replacements.isEmpty()) {
            replacements.add(card);
        }
        return replacements;
    }

    private boolean tooManyWilds() {
        int check = 0;
        for (TrainCard c : faceUpCards) {
            if (c.getColor().equals(TrainCardColor.WILD)) {
                check++;
            }
        }
        return check > 2;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void changeTurn() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).equals(currentPlayer)) {
                if (i < players.size()-1) {
                    currentPlayer = players.get(i+1);
                    break;
                }
                else {
                    currentPlayer = players.get(0);
                    break;
                }
            }
        }
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public boolean ready() {
        return (numPlayers - players.size() == 0);
    }

    public boolean checkColorAvailable(PlayerColor color) {
        for(Player player: getPlayers()){
            if(player.getPlayerColor().equals(color)){
                return false;
            }
        }
        return true;
    }

    public void initializeUnclaimedRoutes() {
        MapSetup mapSetup = new MapSetup();
        unclaimedRoutes = new ArrayList<>(mapSetup.getRoutes());
    }

    public void initializeTrainCardDeck() {
        for (int i = 0; i < 12; i++) {
            TrainCard redTrainCard = new TrainCard(TrainCardColor.RED);
            TrainCard orangeTrainCard = new TrainCard(TrainCardColor.ORANGE);
            TrainCard yellowTrainCard = new TrainCard(TrainCardColor.YELLOW);
            TrainCard greenTrainCard = new TrainCard(TrainCardColor.GREEN);
            TrainCard blueTrainCard = new TrainCard(TrainCardColor.BLUE);
            TrainCard purpleTrainCard = new TrainCard(TrainCardColor.PURPLE);
            TrainCard blackTrainCard = new TrainCard(TrainCardColor.BLACK);
            TrainCard whiteTrainCard = new TrainCard(TrainCardColor.WHITE);

            trainCardDeck.add(redTrainCard);
            trainCardDeck.add(orangeTrainCard);
            trainCardDeck.add(yellowTrainCard);
            trainCardDeck.add(greenTrainCard);
            trainCardDeck.add(blueTrainCard);
            trainCardDeck.add(purpleTrainCard);
            trainCardDeck.add(blackTrainCard);
            trainCardDeck.add(whiteTrainCard);
        }

        for (int i = 0; i < 14; i++) {
            TrainCard wildCard = new TrainCard(TrainCardColor.WILD);
            trainCardDeck.add(wildCard);
        }
    }

    public void initializeFaceUpCards() {
        int wilds = 0;
        while (faceUpCards.size() < 5) {
            TrainCard card = drawTrainCard();
            if (card.getColor().equals(TrainCardColor.WILD)) {
                if (wilds >= 2) {
                    ArrayList<TrainCard> discard = new ArrayList<>();
                    discard.add(card);
                    addCardsToTrainDiscarded(discard);
                    continue;
                }
                wilds++;
            }
            faceUpCards.add(card);
        }
    }

    public TrainCard drawTrainCard() {
        int deckSize = trainCardDeck.size();
        if (deckSize > 0) {
            Random rand = new Random();
            int cardIndex = rand.nextInt(deckSize);

            TrainCard drawnCard = trainCardDeck.get(cardIndex);
            trainCardDeck.remove(cardIndex);
            return drawnCard;
        }
        return null;
    }

    public DestinationCard drawDestCard() {
        int deckSize = destCardDeck.size();
        if (deckSize > 0) {
            Random rand = new Random();
            int cardIndex = rand.nextInt(deckSize);

            DestinationCard drawnCard = destCardDeck.get(cardIndex);
            destCardDeck.remove(cardIndex);

            return drawnCard;
        }
        return null;
    }

    public void putDestCardInDeck(DestinationCard card) {
        destCardDeck.add(card);
    }

    public ArrayList<TrainCard> getPlayerInitialTrainCards() {
        ArrayList<TrainCard> cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            cards.add(drawTrainCard());
        }
        return cards;
    }

    public ArrayList<DestinationCard> getPlayerInitialDestCards() {
        ArrayList<DestinationCard> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cards.add(drawDestCard());
        }
        return cards;
    }

    public ArrayList<TrainCard> getTrainCardDeck() {
        return trainCardDeck;
    }

    public void setTrainCardDeck(ArrayList<TrainCard> trainCardDeck) {
        this.trainCardDeck = trainCardDeck;
    }

    public ArrayList<DestinationCard> getDestCardDeck() {
        return destCardDeck;
    }

    public void setDestCardDeck(ArrayList<DestinationCard> destCardDeck) {
        this.destCardDeck = destCardDeck;
    }

    public ArrayList<TrainCard> getFaceUpCards() {
        return faceUpCards;
    }

    public void setFaceUpCards(ArrayList<TrainCard> faceUpCards) {
        this.faceUpCards = faceUpCards;
    }

    public ArrayList<TrainCard> getDiscardedTrainCards() {
        return discardedTrainCards;
    }

    public void setDiscardedTrainCards(ArrayList<TrainCard> discardedTrainCards) {
        this.discardedTrainCards = discardedTrainCards;
    }

    public void addCardsToTrainDiscarded(ArrayList<TrainCard> cardsToDiscard) {
        discardedTrainCards.addAll(cardsToDiscard);
    }

    public ArrayList<Route> getUnclaimedRoutes() {
        return unclaimedRoutes;
    }

    public void setUnclaimedRoutes(ArrayList<Route> unclaimedRoutes) {
        this.unclaimedRoutes = unclaimedRoutes;
    }

    public void removeFromUnclaimedRoutes(Route route) {
        unclaimedRoutes.remove(route);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentPlayerIndex() {
        int currPlayerIndex = 0;
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            if (currentPlayer.equals(p)) {
                currPlayerIndex = i;
            }
        }
        return currPlayerIndex;
    }

    public Player getNextPlayer() {
        int currPlayerIndex = getCurrentPlayerIndex();
        int nextPlayerIndex = currPlayerIndex + 1;
        if (nextPlayerIndex == players.size()) {
            nextPlayerIndex = 0;
        }
        return players.get(nextPlayerIndex);
    }

    public boolean isLastRound() {
        return lastRound;
    }

    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfo gameInfo = (GameInfo) o;
        return numPlayers == gameInfo.numPlayers &&
                gameName.equals(gameInfo.gameName) &&
                Objects.equals(players, gameInfo.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName, players, numPlayers);
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "gameName=" + gameName.toString() +
                ", numPlayers=" + numPlayers +
                '}';
    }
}
