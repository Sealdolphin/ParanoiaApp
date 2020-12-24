package paranoia;

import paranoia.core.Computer;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.services.plc.ResourceManager;
import paranoia.services.rnd.ParanoiaCard;
import paranoia.services.technical.CommandParser;
import paranoia.services.technical.networking.Network;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

/**
 * The game itself
 */
public class Paranoia {

    public static final Color PARANOIA_BACKGROUND = new Color(255, 255, 255);
    public static final String version = "version v.alpha";

    public static void main(String[] args) {

        try {
            ResourceManager.loadResources();    //not this!! This is important
            ParanoiaCard.loadAllCardAssets();
            Computer.initDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new ControlUnit(new Network(new CommandParser()));
    }

    public static String getParanoiaResource(String path) throws IOException {
        URL url = Paranoia.class.getClassLoader().getResource(path);
        if(url != null) {
            return url.getFile();
        } else {
            throw new IOException("Could not load file: " + path);
        }
    }

}
