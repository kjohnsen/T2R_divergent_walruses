package util;

import com.example.emilyhales.tickettoride.R;

import modelclasses.PlayerColor;

public class PlayerColorConverter {
    public static int convertPlayerColor(PlayerColor playerColor) {
        switch (playerColor) {
            case RED: return R.color.trainRed;
            case BLUE: return R.color.trainBlue;
            case BLACK: return R.color.playerBlack;
            case GREEN: return R.color.trainGreen;
            case YELLOW: return R.color.trainYellow;
            default: break;
        }
        return -1;
    }
}
