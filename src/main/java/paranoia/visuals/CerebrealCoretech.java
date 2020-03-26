package paranoia.visuals;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CerebrealCoretech extends JFrame {

    public CerebrealCoretech() {
        //Setup metadata
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paranoia");
        setMinimumSize(new Dimension(320, 280));
        //Show Picture
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("clones/clone0.png")).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        getContentPane().getGraphics().drawImage(img, 0, 0, null);

        //Setup visuals

        //Assemble
        pack();
        setLocationRelativeTo(null);
    }



}
