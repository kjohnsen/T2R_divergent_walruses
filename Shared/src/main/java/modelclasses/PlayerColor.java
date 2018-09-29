package modelclasses;

import java.awt.Color;
import java.util.ArrayList;

public class PlayerColor{
    private static final ArrayList<Color> availableColors;
    private Color color;

    static {
        availableColors = new ArrayList<>();
        availableColors.add(Color.MAGENTA);
        availableColors.add(Color.BLACK);
        availableColors.add(Color.BLUE);
        availableColors.add(Color.RED);
        availableColors.add(Color.GREEN);
        availableColors.add(Color.YELLOW);
        availableColors.add(Color.ORANGE);
    }

    public PlayerColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static ArrayList<Color> getAvailableColors() {
        return availableColors;
    }
}
