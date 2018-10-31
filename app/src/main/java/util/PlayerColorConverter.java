package util;

import android.content.Context;

import com.example.emilyhales.tickettoride.R;

import modelclasses.PlayerColor;

public class PlayerColorConverter {
    public static int convertPlayerColor(PlayerColor playerColor, Context context) {
        switch (playerColor) {
            case RED: return context.getResources().getColor(R.color.trainRed);
            case BLUE: return context.getResources().getColor(R.color.trainBlue);
            case BLACK: return context.getResources().getColor(R.color.playerBlack);
            case GREEN: return context.getResources().getColor(R.color.trainGreen);
            case YELLOW: return context.getResources().getColor(R.color.trainYellow);
            default: break;
        }
        return -1;
    }
}
