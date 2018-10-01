package modelclasses;

import java.awt.Color;
import java.util.ArrayList;

public enum PlayerColor {
    MAGENTA(Color.MAGENTA),
    BLUE(Color.BLUE),
    RED(Color.RED),
    GREEN(Color.GREEN),
    YELLOW(Color.YELLOW),
    ORANGE(Color.ORANGE),
    BLACK(Color.BLACK);

    private Color color;
    private PlayerColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
