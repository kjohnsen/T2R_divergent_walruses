package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class TrainCard implements Serializable {
    private TrainCardColor color;

    public TrainCard(TrainCardColor color) {
        this.color = color;
    }

    public TrainCardColor getColor() { return color; }

    public static ArrayList<TrainCard> getRandomNumCards(){
        ArrayList<TrainCard> trainCards = new ArrayList<>();

        Random r = new Random();
        int max = 20;
        int min = 1;
        int randomNumCards = r.nextInt((max - min) + 1) + min;

        for(int i = 0; i < randomNumCards; i++){
            TrainCardColor color = TrainCardColor.values()[new Random().nextInt(TrainCardColor.values().length)];
            trainCards.add(new TrainCard(color));
        }
        return trainCards;
    }

}
