package modelclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum PlayerColor implements Serializable {
    UNCHOSEN,
    MAGENTA,
    BLUE,
    RED,
    GREEN,
    YELLOW,
    ORANGE,
    BLACK;

    public static List<String> getColors() {
        List<String> colors = new ArrayList<>();
        for (PlayerColor c : values()) {
            colors.add(c.name());
        }
        return colors;
    }

    public static List<String> getAvailableColors(List<Player> players, String username) {
        List<String> colors = getColors();
        for (Player p : players) {
            String color = p.getPlayerColor().name();
            if (!color.equals("UNCHOSEN") && !p.getUsername().equals(username)) {
                colors.remove(p.getPlayerColor().name());
            }
        }
        return colors;
    }
}
