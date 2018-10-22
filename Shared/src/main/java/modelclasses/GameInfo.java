package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Arrays;

public class GameInfo implements Serializable {
    private GameName gameName;
    private ArrayList<Player> players = new ArrayList<>();
    private int numPlayers;
    private List<TrainCard> trainCardDeck = new ArrayList<>();
    private List<DestinationCard> destCardDeck = new ArrayList<>();

    public GameInfo(GameName gameName, ArrayList<Player> players, int numPlayers) {
        this.gameName = gameName;
        this.numPlayers = numPlayers;
        setPlayers(players);
        initializeTrainCardDeck();
        setDestCardDeck(new ArrayList<>(Arrays.asList(Atlas.getDestinations())));
    }

    public void addPlayer(Player player) {
        players.add(player);
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

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
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

    public List<TrainCard> getTrainCardDeck() {
        return trainCardDeck;
    }

    public void setTrainCardDeck(List<TrainCard> trainCardDeck) {
        this.trainCardDeck = trainCardDeck;
    }

    public List<DestinationCard> getDestCardDeck() {
        return destCardDeck;
    }

    public void setDestCardDeck(List<DestinationCard> destCardDeck) {
        this.destCardDeck = destCardDeck;
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
