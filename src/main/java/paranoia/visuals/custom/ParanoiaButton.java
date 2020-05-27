package paranoia.visuals.custom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIDefaults;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ParanoiaButton extends JButton {

    private static final UIDefaults defaults = javax.swing.UIManager.getDefaults();
    private static final int DEFAULT_WIDTH = 32;
    private Color hoverBackgroundColor = defaults.getColor("Button.highlight");
    private Color pressedBackgroundColor = defaults.getColor("Button.select");
    private Color hoverBorderColor = defaults.getColor("Button.highlight");
    private Color backgroundBorderColor = defaults.getColor("Button.highlight");

    public ParanoiaButton() {
        this((BufferedImage) null);
    }

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
        setBackground(defaults.getColor("Button.background"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if( getModel().isPressed() ){
            g.setColor(pressedBackgroundColor);
        } else if( getModel().isRollover() ) {
            g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) { }

    public void setHoverBG(Color color) {
        hoverBackgroundColor = color;
    }

    public void setPressedBG(Color color) {
        pressedBackgroundColor = color;
    }

    public void setHoverBorder(Color color) {
        hoverBorderColor = color;
    }
}
