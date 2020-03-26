package paranoia.core;

import java.awt.Color;

public enum SecurityClearance {
    INFRARED(new Color(0,0,0), new Color(38, 38, 38), new Color(255, 255, 255)),
    RED(new Color(217,0,0), new Color(191,48,48), new Color(255,255,255)),
    ORANGE(new Color(217,108,0), new Color(191,120,48), new Color(0,0,0)),
    YELLOW(new Color(217,217,0), new Color(191,191,48), new Color(0,0,0)),
    GREEN(new Color(0,217,0), new Color(48,191,48), new Color(0,0,0)),
    BLUE(new Color(0,36,217), new Color(48,72,191), new Color(255,255,255)),
    INDIGO(new Color(126,0,217), new Color(131,48,191), new Color(255,255,255)),
    VIOLET(new Color(217,0,217), new Color(191,48,191), new Color(255,255,255)),
    ULTRAVIOLET(new Color(255,255,255), new Color(191,191,191), new Color(0,0,0));

    private Color color;
    private Color backgroundColor;
    private Color fontColor;

    SecurityClearance(Color color, Color bgColor, Color fontColor) {
        this.color = color;
        this.backgroundColor = bgColor;
        this.fontColor = fontColor;
    }
    public Color getColor() { return color; }
    public Color getFontColor() {return fontColor; }
    public Color getBackgroundColor() { return backgroundColor; }
    public int getShort() { return name().charAt(0); }

}
