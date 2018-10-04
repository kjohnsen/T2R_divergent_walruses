package modelclasses;

import java.util.ArrayList;

public enum PlayerColor {
    MAGENTA,
    BLUE,
    RED,
    GREEN,
    YELLOW,
    ORANGE,
    BLACK,
    UNCHOSEN;

    public static ArrayList<String> getColors() {
        ArrayList<String> colors = new ArrayList<>();
        for (PlayerColor c : values()) {
            colors.add(c.name());
        }
        return colors;
    }
}
