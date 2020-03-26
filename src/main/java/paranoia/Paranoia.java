package paranoia;

import paranoia.core.Clone;
import paranoia.core.SecurityClearance;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.visuals.CerebrealCoretech;
import paranoia.visuals.RollMessage;

import javax.imageio.ImageIO;
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

    public static void main(String[] args) {
        ControlUnit cpu = new ControlUnit();
        CerebrealCoretech coreTech = new CerebrealCoretech();

        BufferedImage img = null;
        //Show Picture
        try {
            img = ImageIO.read(new File(getParanoiaResource("clones/clone0.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Clone clone = new Clone("SYD", "ULS", SecurityClearance.RED, 3, img);
        coreTech.addClone(clone);
        coreTech.setVisible(true);

        Map<String, Integer> positive = new HashMap<>();
        Map<String, Integer> negative = new HashMap<>();

        positive.put("The GM likes your style", 1);
        positive.put("Action card", 2);
        negative.put("Injury", 2);

        RollMessage message = new RollMessage(
                clone,
                Stat.BRAINS, true,
                Skill.ALPHA_COMPLEX, true,
                positive, negative,
                "Please roll with..."
        );
        message.setVisible(true);

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
