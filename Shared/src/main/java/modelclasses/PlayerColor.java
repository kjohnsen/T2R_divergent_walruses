package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;

public enum PlayerColor implements Serializable {
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
        colors.remove("UNCHOSEN");
        return colors;
    }
}
