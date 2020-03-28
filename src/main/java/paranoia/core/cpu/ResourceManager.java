package paranoia.core.cpu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static paranoia.Paranoia.getParanoiaResource;

public abstract class ResourceManager {

    public enum ResourceIcon {
        INJURY,
        INJURY_OFF,
        TREASON_STAR,
        TREASON_STAR_OFF,
        MOXIE,
        MOXIE_OFF,
        MOXIE_CROSSED,
        MISSION,
        MISSION_FAILED,
        MISSION_COMPLETED
    }

    private static Map<ResourceIcon, BufferedImage> images = new HashMap<>();

    public static void loadResources() throws IOException {
        images.put(ResourceIcon.INJURY, ImageIO.read(new File(getParanoiaResource("mechanics/injuryActive.png"))));
        images.put(ResourceIcon.INJURY_OFF, ImageIO.read(new File(getParanoiaResource("mechanics/injury.png"))));
        images.put(ResourceIcon.TREASON_STAR, ImageIO.read(new File(getParanoiaResource("mechanics/treasonStar.png"))));
        images.put(ResourceIcon.TREASON_STAR_OFF, ImageIO.read(new File(getParanoiaResource("mechanics/treasonStarEmpty.png"))));
        images.put(ResourceIcon.MOXIE, ImageIO.read(new File(getParanoiaResource("mechanics/moxie.png"))));
        images.put(ResourceIcon.MOXIE_OFF, ImageIO.read(new File(getParanoiaResource("mechanics/moxieFilled.png"))));
        images.put(ResourceIcon.MOXIE_CROSSED, ImageIO.read(new File(getParanoiaResource("mechanics/moxieCrossed.png"))));
        images.put(ResourceIcon.MISSION, ImageIO.read(new File(getParanoiaResource("mechanics/mission.png"))));
        images.put(ResourceIcon.MISSION_COMPLETED, ImageIO.read(new File(getParanoiaResource("mechanics/missionComplete.png"))));
        images.put(ResourceIcon.MISSION_FAILED, ImageIO.read(new File(getParanoiaResource("mechanics/missionFailed.png"))));
    }

    public static BufferedImage getResource(ResourceIcon key) {
        return images.getOrDefault(key, null);
    }

}
