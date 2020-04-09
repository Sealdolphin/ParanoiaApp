package paranoia.core;

import java.awt.Color;

public enum SecurityClearance {
    INFRARED(new Color(0,0,0), new Color(64, 64, 64), new Color(255, 255, 255)),
    RED(new Color(217,0,0), new Color(255,128,128), new Color(255,255,255)),
    ORANGE(new Color(217,108,0), new Color(255,191,128), new Color(0,0,0)),
    YELLOW(new Color(217,217,0), new Color(255,255,128), new Color(0,0,0)),
    GREEN(new Color(0,217,0), new Color(128,255,128), new Color(0,0,0)),
    BLUE(new Color(0,36,217), new Color(128,151,255), new Color(255,255,255)),
    INDIGO(new Color(126,0,217), new Color(200,128,255), new Color(255,255,255)),
    VIOLET(new Color(217,0,217), new Color(255,128,255), new Color(255,255,255)),
    ULTRAVIOLET(new Color(255,255,255), new Color(191,191,191), new Color(0,0,0));

    private final Color color;
    private final Color backgroundColor;
    private final Color fontColor;

    SecurityClearance(Color color, Color bgColor, Color fontColor) {
        this.color = color;
        this.backgroundColor = bgColor;
        this.fontColor = fontColor;
    }
    public Color getColor() { return color; }
    public Color getFontColor() {return fontColor; }
    public Color getBackgroundColor() { return backgroundColor; }
    public String getShort() { return name().substring(0,1); }

}
