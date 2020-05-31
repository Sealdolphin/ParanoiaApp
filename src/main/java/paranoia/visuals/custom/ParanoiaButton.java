package paranoia.visuals.custom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIDefaults;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.image.BufferedImage;

import static paranoia.services.plc.AssetManager.defaultButtonBackground;

public class ParanoiaButton extends JButton {

    private static final UIDefaults defaults = javax.swing.UIManager.getDefaults();
    private static final int DEFAULT_WIDTH = 32;
    private Color hoverBorderColor = defaults.getColor("Button.highlight");
    private Color backgroundBorderColor = defaults.getColor("Button.highlight");

    public ParanoiaButton(BufferedImage image) {
        this(image, DEFAULT_WIDTH);
    }

    public ParanoiaButton(String text){
        super(text);
        super.setContentAreaFilled(false);
    }

    public ParanoiaButton(BufferedImage image, int width) {
        super(new ImageIcon(image.getScaledInstance(width,-1, Image.SCALE_SMOOTH)));
    }

    public void resetBackgroud() {
        setBackground(defaultButtonBackground);
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g.create();
        if( getModel().isPressed() ){
            g2.setColor(getBackground().darker());
        } else if( getModel().isRollover() ) {
            g2.setPaint(getBackgroundPaint(getBackground().brighter()));
        } else {
            g2.setPaint(getBackgroundPaint(getBackground()));
        }
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }

    private Paint getBackgroundPaint(Color color) {
        return new GradientPaint(
            new Point(),
            color,
            new Point(0, getHeight()),
            color.darker()
        );
    }

    @Override
    public void setContentAreaFilled(boolean b) { }

    public void setHoverBorder(Color color) {
        hoverBorderColor = color;
    }
}
