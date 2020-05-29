package paranoia.services.plc;

import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

public abstract class AssetManager {

    public static Font getFont(String name, int size) {
        return getFont(name, size, false, false, false);
    }

    public static Font getFont(int size) {
        return getFont("Segoe", size, false, false, false);
    }

    public static Font getBoldFont(int size) {
        return getFont("Segoe", size, true, false, false);
    }

    public static Font getItalicFont(int size) {
        return getFont("Segoe", size, false, true, false);
    }

    public static Font getUnderlineFont(int size) {
        return getFont("Segoe", size, false, false, true);
    }

    public static Font getFont(int size, boolean bold, boolean italic, boolean underline) {
        return getFont("Segoe", size, bold, italic, underline);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Font getFont(String name, int size, boolean bold, boolean italic, boolean underline) {
        int style = Font.PLAIN;
        if(bold) style += Font.BOLD;
        if(italic) style += Font.ITALIC;
        Font font = new Font(name, style, size);
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

    public static class ParanoiaArrow extends BasicArrowButton {

        private Dimension maxSize = new Dimension(50,50);
        private Dimension prefSize = new Dimension(60, 28);

        public ParanoiaArrow(int direction) {
            super(direction);
        }

        @Override
        public void setPreferredSize(Dimension preferredSize) {
            prefSize = preferredSize;
        }

        @Override
        public Dimension getPreferredSize() {
            return prefSize;
        }

        @Override
        public void setMaximumSize(Dimension maximumSize) {
            maxSize = maximumSize;
        }

        @Override
        public Dimension getMaximumSize() {
            return maxSize;
        }
    }

}
