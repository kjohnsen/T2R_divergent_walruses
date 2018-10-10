package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;

public enum PlayerColor implements Serializable {
    UNCHOSEN,
    MAGENTA,
    BLUE,
    RED,
    GREEN,
    YELLOW,
    ORANGE,
    BLACK;

    public static ArrayList<String> getColors() {
        ArrayList<String> colors = new ArrayList<>();
        for (PlayerColor c : values()) {
            colors.add(c.name());
        }
        return colors;
    }
}
