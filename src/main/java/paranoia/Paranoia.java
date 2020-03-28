package paranoia;

import paranoia.core.Clone;
import paranoia.core.SecurityClearance;
import paranoia.core.cpu.ResourceManager;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.visuals.CerebrealCoretech;
import paranoia.visuals.RollMessage;
import paranoia.visuals.rnd.ParanoiaCard;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * The game itself
 */
public class Paranoia {

    public static final Color PARANOIA_BACKGROUND = new Color(255, 255, 255);

    public static void main(String[] args) {

        //TODO: remove later
        BufferedImage img0 = null;
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        BufferedImage img3 = null;
        BufferedImage img4 = null;
        //Show Picture
        try {
            ResourceManager.loadResources();    //not this!! This is important
            ParanoiaCard.loadAllCardAssets();

            img0 = ImageIO.read(new File(getParanoiaResource("clones/clone0.png")));
            img1 = ImageIO.read(new File(getParanoiaResource("clones/clone1.png")));
            img2 = ImageIO.read(new File(getParanoiaResource("clones/clone2.png")));
            img3 = ImageIO.read(new File(getParanoiaResource("clones/clone3.png")));
            img4 = ImageIO.read(new File(getParanoiaResource("clones/clone4.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Clone clone0 = new Clone("SYD", "ULS", SecurityClearance.INFRARED, 4, img0);
        Clone clone1 = new Clone("CHRIS", "AFG", SecurityClearance.ORANGE, 2, img1);
        Clone clone2 = new Clone("ROZ", "HYT", SecurityClearance.RED, 0, img2);
        Clone clone3 = new Clone("JOE", "RTE", SecurityClearance.BLUE, 1, img3);
        Clone clone4 = new Clone("CARA", "RLY", SecurityClearance.YELLOW, 3, img4);

        ControlUnit cpu = new ControlUnit();
        CerebrealCoretech coreTech = new CerebrealCoretech(clone4);
        coreTech.setExtendedState(Frame.MAXIMIZED_BOTH);

        coreTech.addClone(clone0);
        coreTech.addClone(clone1);
        coreTech.addClone(clone2);
        coreTech.addClone(clone3);
        coreTech.setSelf(clone4);
        coreTech.setVisible(true);

        //TODO: remove later
        Map<String, Integer> positive = new HashMap<>();
        Map<String, Integer> negative = new HashMap<>();

        positive.put("The GM likes your style", 1);
        positive.put("Action card", 2);
        negative.put("Injury", 2);

        RollMessage message = new RollMessage(
                clone0,
                Stat.BRAINS, true,
                Skill.ALPHA_COMPLEX, true,
                positive, negative,
                "Please roll with..."
        );

        //message.setVisible(true);

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
