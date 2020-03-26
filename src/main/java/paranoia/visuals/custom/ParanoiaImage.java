package paranoia.visuals.custom;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ParanoiaImage extends JPanel {

    protected BufferedImage image;

    public ParanoiaImage(BufferedImage image){
        this(image, false);
    }

    public ParanoiaImage(BufferedImage image, Boolean hasBorder){
        this.image = image;
        if(hasBorder) setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image != null)
            g.drawImage(image,
            0, 0, getWidth(), getHeight(),
            0 ,0, image.getWidth(), image.getHeight(),
            null);
    }
}
