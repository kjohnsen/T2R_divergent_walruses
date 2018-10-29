package util;

import com.example.emilyhales.tickettoride.R;

import modelclasses.TrainCardColor;

/**
 * Created by kylej13 on 10/29/18.
 */

public class TrainColorConverter {
    public static int convertTrainColor(TrainCardColor trainColor) {
        switch (trainColor) {
            case WILD: return R.color.deckGray;
            case WHITE: return R.color.trainWhite;
            case BLACK: return R.color.trainBlack;
            case RED: return R.color.trainRed;
            case ORANGE: return R.color.trainOrange;
            case YELLOW: return R.color.trainYellow;
            case GREEN: return R.color.trainGreen;
            case BLUE: return R.color.trainBlue;
            case PURPLE: return R.color.trainPurple;
        }
        return -1;
    }
}
