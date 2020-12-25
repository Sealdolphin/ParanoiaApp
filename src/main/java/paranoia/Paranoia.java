package paranoia;

import paranoia.core.Computer;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.services.plc.ResourceManager;
import paranoia.services.rnd.ParanoiaCard;
import paranoia.services.technical.CommandParser;
import paranoia.services.technical.networking.Network;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static daiv.Computer.getParanoiaResource;

/**
 * The game itself
 */
public class Paranoia {

    public static final Color PARANOIA_BACKGROUND = new Color(255, 255, 255);
    public static final String version = "version v.alpha";
    public static final Image icon = prepareIcon();

    public static BufferedImage prepareIcon() {
        try {
            return ImageIO.read(new File(getParanoiaResource("ui/paranoia.png")));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        try {
            ResourceManager.loadResources();
            ParanoiaCard.loadAllCardAssets();
            Computer.initDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new ControlUnit(new Network(new CommandParser()));
    }

}
