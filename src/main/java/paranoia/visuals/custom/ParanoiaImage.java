package paranoia.visuals.custom;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ParanoiaImage extends JPanel {

    private BufferedImage image;

    public ParanoiaImage(BufferedImage image){
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,
            0, 0, getWidth(), getHeight(),
            0 ,0, image.getWidth(), image.getHeight(),
            null);
    }
}
