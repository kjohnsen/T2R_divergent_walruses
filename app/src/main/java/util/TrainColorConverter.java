package util;

import android.content.Context;

import com.example.emilyhales.tickettoride.R;

import modelclasses.TrainCardColor;

/**
 * Created by kylej13 on 10/29/18.
 */

public class TrainColorConverter {
    public static int convertTrainColor(TrainCardColor trainColor, Context context) {
        switch (trainColor) {
            case WILD: return context.getResources().getColor(R.color.trainPink);
            case WHITE: return context.getResources().getColor(R.color.trainWhite);
            case BLACK: return context.getResources().getColor(R.color.trainBlack);
            case RED: return context.getResources().getColor(R.color.trainRed);
            case ORANGE: return context.getResources().getColor(R.color.trainOrange);
            case YELLOW: return context.getResources().getColor(R.color.trainYellow);
            case GREEN: return context.getResources().getColor(R.color.trainGreen);
            case BLUE: return context.getResources().getColor(R.color.trainBlue);
            case PURPLE: return context.getResources().getColor(R.color.trainPurple);
        }
        return -1;
    }
}
