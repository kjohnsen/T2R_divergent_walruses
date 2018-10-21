package model;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import modelclasses.ChatMessage;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.User;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;

public class ServerModel {

    private static ServerModel instance = null;

    private Map<String, String> authTokens = new HashMap<>(); // maps authTokens to usernames
    private Map<String, User> users = new HashMap<>(); // maps usernames to users
    private Map<GameName, GameInfo> games = new HashMap<>();
    private List<TrainCard> trainCardDeck = new ArrayList<>();
    private Map<GameName,List<ChatMessage>> chatMessages = new HashMap<>();

    private ServerModel() { }

    public static ServerModel getInstance(){
        if(instance == null) {
            instance = new ServerModel();
        }
        return instance;
    }

    public Map<GameName, List<ChatMessage>> getChatMessages() {
        return chatMessages;
    }

    public Boolean checkUserExists(String username) {
        return getUsers().containsKey(username);
    }

    public Boolean checkPassword(String username, String password) {
        return getUsers().get(username).getPassword().equals(password);
    }

    public GameInfo getGameInfo(GameName gameName) {
        return games.get(gameName);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public ArrayList<GameInfo> getGameList() {
        Object[] gameList = games.values().toArray();
        ArrayList<GameInfo> gameListToReturn = new ArrayList<>();
        for (Object gameObj : gameList) {
            GameInfo game = (GameInfo) gameObj;
            gameListToReturn.add(game);
        }
        return gameListToReturn;
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

    public ArrayList<TrainCard> getPlayerInitialCards() {
        ArrayList<TrainCard> cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            cards.add(drawTrainCard());
        }
        return cards;
    }

    // ********** getters and setters ***********
    public Map<String, String> getAuthTokens() {
        return authTokens;
    }

    public void setAuthTokens(Map<String, String> authTokens) {
        this.authTokens = authTokens;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public Map<GameName, GameInfo> getGames() {
        return games;
    }

    public void setGames(Map<GameName, GameInfo> games) {
        this.games = games;
    }

    public List<TrainCard> getTrainCardDeck() {
        return trainCardDeck;
    }

    public void setTrainCardDeck(List<TrainCard> trainCardDeck) {
        this.trainCardDeck = trainCardDeck;
    }

    //********************************************
}
