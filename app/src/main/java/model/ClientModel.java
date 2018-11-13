package model;

import java.util.ArrayList;
import java.util.Observable;

import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.DestinationCardWrapper;
import modelclasses.GameName;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import modelclasses.User;

/* The ClientModel is the only Observable object. Each of its setter methods will call setChanged()
and notifyObservers(), but if anything gets changed that isn't related to a setter, you have to do
it manually */
public class ClientModel extends Observable {
    private User currentUser;

    private ArrayList<GameInfo> gameList = new ArrayList<>();
    private GameInfo currentGame;
    private ArrayList<TrainCard> faceupCards;
    private ArrayList<TrainCard> playerTrainCards;
    private ArrayList<DestinationCard> playerTickets;
    private ArrayList<DestinationCard> playerPreSelectionTickets;
    private ArrayList<ChatMessage> chatMessages;
    private boolean startGame;

    private static final ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {
        faceupCards = new ArrayList<>();
        playerTrainCards = new ArrayList<>();
        playerTickets = new ArrayList<>();
        chatMessages = new ArrayList<>();
        startGame = true;
    }

    public void reset() {
        currentGame = null;
        currentUser = null;
        gameList = new ArrayList<>();
        faceupCards = new ArrayList<>();
    }

    public PlayerColor getCurrentColor() {
        return currentGame.getPlayer(currentUser.getUsername()).getPlayerColor();
    }

    //for testing purposes
    public void setGameStart(boolean start) { startGame = start; }

    public void rejectTickets(ArrayList<DestinationCard> rejections, Player player) {
        for (DestinationCard c : rejections) {
            currentGame.putDestCardInDeck(c);
            if (currentUser.getUsername().equals(player.getUsername())) {
                playerTickets.remove(c);
            }
        }
        if (currentUser.getUsername().equals(player.getUsername())) {
            notifyObservers(new DestinationCardWrapper(playerTickets, false));
        }
        notifyObservers(player);
        notifyObservers(new DestinationCardWrapper(currentGame.getDestCardDeck(), true));
    }

    public void selectTrainCardToHand(TrainCard card, Player player) {
        currentGame.addTrainCardToHand(card, player);
        if (currentUser.getUsername().equals(player.getUsername())) {
            playerTrainCards.add(card);
            notifyObservers(card);
        }
        notifyObservers(player);
        if (currentGame.getTrainCardDeck().size() != 5) {
            notifyObservers(currentGame.getTrainCardDeck());
        }
    }

    public void drawTrainCardToHand(TrainCard card, Player player) {
        currentGame.addTrainCardToHand(card, player);
        // we don't actually care about the deck, just the size of it, so just remove a random
        //card for ease
        currentGame.getTrainCardDeck().remove(0);
        if (currentUser.getUsername().equals(player.getUsername())) {
            playerTrainCards.add(card);
            notifyObservers(card);
        }
        notifyObservers(player);
        if (currentGame.getTrainCardDeck().size() != 5) {
            notifyObservers(currentGame.getTrainCardDeck());
        }
    }

    public void addTickets(ArrayList<DestinationCard> cards, Player player) {
        currentGame.addTicketsToHand(cards, player);
        if (currentUser.getUsername().equals(player.getUsername())) {
            playerTickets.addAll(cards);
            notifyObservers(new DestinationCardWrapper(cards, false));
        }
        notifyObservers(player);
        notifyObservers(new DestinationCardWrapper(currentGame.getDestCardDeck(),true));
    }

    public ArrayList<DestinationCard> getPlayerTickets() {
        return playerTickets;
    }
    
    public ArrayList<DestinationCard> getPlayerPreSelectionTickets() {
        return playerPreSelectionTickets;
    }

    public void replaceFaceupCard(TrainCard replacement, int selected) {
        faceupCards.set(selected, replacement);
        //again, just remove a random card
        currentGame.getTrainCardDeck().remove(0);
        notifyObservers(faceupCards);
        if (currentGame.getTrainCardDeck().size() != 5) {
            notifyObservers(currentGame.getTrainCardDeck());
        }
    }

    public void setFaceupCards(ArrayList<TrainCard> cards) {
        faceupCards = cards;
        //just remove six random cards
        for (int i = 0; i < 6; i++) {
            currentGame.getTrainCardDeck().remove(0);
        }
        notifyObservers(faceupCards);
        if (currentGame.getTrainCardDeck().size() != 5) {
            notifyObservers(currentGame.getTrainCardDeck());
        }
    }

    public ArrayList<TrainCard> getPlayerTrainCards() {
        return playerTrainCards;
    }

    public void setPlayerTrainCards(ArrayList<TrainCard> cards) {
        playerTrainCards = cards;
        this.notifyObservers(cards);
    }

    public void setPlayerPreSelectionTickets(ArrayList<DestinationCard> preSelectionTickets) {
        playerPreSelectionTickets = preSelectionTickets;
        this.notifyObservers(new DestinationCardWrapper(preSelectionTickets, false));
    }

    public void setPlayerTickets(ArrayList<DestinationCard> tickets) {
        playerTickets = tickets;
        this.notifyObservers(new DestinationCardWrapper(tickets, false));
    }

    public ArrayList<TrainCard> getFaceupCards() {
        return faceupCards;
    }

    public boolean isGameStart() { return startGame; }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        this.notifyObservers();
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    /* this is so the model won't notify the GameListPresenter or the GameLobbyPresenter during unit
    tests, which throws an error */
    public void setCurrentUserTest(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setGameList(ArrayList<GameInfo> gameList) {
        this.gameList = gameList;
        this.notifyObservers(gameList);
    }

    public void setCurrentGame(GameInfo currentGame) {
        this.currentGame = currentGame;
        this.notifyObservers(currentGame);
    }

    public void setCurrentGamePlayers(ArrayList<Player> players) {
        currentGame.setPlayers(players);
        this.notifyObservers(players);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setNotGameStart() { startGame = false; }

    public ArrayList<GameInfo> getGameList() {
        return gameList;
    }
    public GameInfo getGame(GameName gameName) {
        for (GameInfo gameInfo : gameList) {
            if (gameInfo.getGameName().equals(gameName)) {
                return gameInfo;
            }
        }
        return null;
    }

    @Override
    public void notifyObservers() {
        this.setChanged();
        super.notifyObservers();
    }

    @Override
    public void notifyObservers(Object arg) {
        this.setChanged();
        super.notifyObservers(arg);
    }

    public GameInfo getCurrentGame() {
        return currentGame;
    }

    public boolean currentGameReady() {
        if(currentGame != null) {
            return currentGame.ready();
        } else {
            return false;
        }

    }
}
