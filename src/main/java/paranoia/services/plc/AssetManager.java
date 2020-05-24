package paranoia.services.plc;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

public abstract class AssetManager {

    public static Font getFont(int size) {
        return getFont(size, false, false, false);
    }

    public static Font getBoldFont(int size) {
        return getFont(size, true, false, false);
    }

    public static Font getItalicFont(int size) {
        return getFont(size, false, true, false);
    }

    public static Font getUnderlineFont(int size) {
        return getFont(size, false, false, true);
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Font getFont(int size, boolean bold, boolean italic, boolean underline) {
        int style = Font.PLAIN;
        if(bold) style += Font.BOLD;
        if(italic) style += Font.ITALIC;
        Font font = new Font("Segoe", style, size);
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.SIZE, (float) size);
        attributes.put(TextAttribute.WEIGHT,
            bold ? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR);
        attributes.put(TextAttribute.POSTURE,
            italic ? TextAttribute.POSTURE_OBLIQUE : TextAttribute.POSTURE_REGULAR);
        attributes.put(TextAttribute.UNDERLINE,
            underline ? TextAttribute.UNDERLINE_ON : null);
        font.deriveFont(attributes);
        return font;
    }

}
