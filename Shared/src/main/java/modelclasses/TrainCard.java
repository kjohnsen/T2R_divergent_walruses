package modelclasses;

import java.io.Serializable;

public class TrainCard implements Serializable {
    private TrainCardColor color;

    public TrainCard(TrainCardColor color) {
        this.color = color;
    }

    public TrainCardColor getColor() { return color; }
}
