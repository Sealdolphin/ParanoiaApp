package paranoia.visuals.custom;

import javax.swing.JButton;
import javax.swing.UIDefaults;
import java.awt.Color;
import java.awt.Graphics;

public class ParanoiaButton extends JButton {

    private static final UIDefaults defaults = javax.swing.UIManager.getDefaults();
    private Color hoverBackgroundColor = defaults.getColor("Button.highlight");
    private Color pressedBackgroundColor = defaults.getColor("Button.select");

    public ParanoiaButton() {
        this(null);
    }

    public ParanoiaButton(String text){
        super(text);
        super.setContentAreaFilled(false);
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
}