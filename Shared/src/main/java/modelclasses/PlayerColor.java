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

    public static ArrayList<String> getAvailableColors(ArrayList<Player> players, String username) {
        ArrayList<String> colors = getColors();
        for (Player p : players) {
            String color = p.getPlayerColor().name();
            if (!color.equals("UNCHOSEN") && !p.getUsername().equals(username)) {
                colors.remove(p.getPlayerColor().name());
            }
        }
        return colors;
    }
}
