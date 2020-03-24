package paranoia.core;

import java.awt.*;

public enum SecurityClearance {
    INFRARED(Color.BLACK),
    RED(Color.RED),
    ORANGE(Color.ORANGE),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE),
    INDIGO(new Color(56, 18, 91)),
    VIOLET(new Color(175,0, 255)),
    ULTRAVIOLET(Color.WHITE);

    private Color color;
    private SecurityClearance(Color color) {
        this.color = color;
    }
    public Color getColor() { return color; }
    public int getShort() { return name().charAt(0); }

}
