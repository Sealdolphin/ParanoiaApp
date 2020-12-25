package paranoia.services.plc;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static daiv.Computer.getParanoiaResource;

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
        MISSION_COMPLETED,
        MISTERY_CLONE
    }

    public static final Color DEFAULT_FONT = new Color(0,0,0);
    public static final Color FAILED_MISSION = new Color(185,0,0);

    private static final Map<ResourceIcon, BufferedImage> images = new HashMap<>();

    public static void loadResources() throws IOException {
        images.put(ResourceIcon.INJURY, ImageIO.read(new File(getParanoiaResource("mechanics/injuryActive.png"))));
        images.put(ResourceIcon.INJURY_OFF, ImageIO.read(new File(getParanoiaResource("mechanics/injury.png"))));
        images.put(ResourceIcon.TREASON_STAR, ImageIO.read(new File(getParanoiaResource("mechanics/treasonStar.png"))));
        images.put(ResourceIcon.TREASON_STAR_OFF, ImageIO.read(new File(getParanoiaResource("mechanics/treasonStarEmpty.png"))));
        images.put(ResourceIcon.MOXIE, ImageIO.read(new File(getParanoiaResource("mechanics/moxieFilled.png"))));
        images.put(ResourceIcon.MOXIE_OFF, ImageIO.read(new File(getParanoiaResource("mechanics/moxie.png"))));
        images.put(ResourceIcon.MOXIE_CROSSED, ImageIO.read(new File(getParanoiaResource("mechanics/moxieCrossed.png"))));
        images.put(ResourceIcon.MISSION, ImageIO.read(new File(getParanoiaResource("mechanics/mission.png"))));
        images.put(ResourceIcon.MISSION_COMPLETED, ImageIO.read(new File(getParanoiaResource("mechanics/missionComplete.png"))));
        images.put(ResourceIcon.MISSION_FAILED, ImageIO.read(new File(getParanoiaResource("mechanics/missionFailed.png"))));
        images.put(ResourceIcon.MISTERY_CLONE, ImageIO.read(new File(getParanoiaResource("clones/clone0.png"))));
    }

    public static BufferedImage getResource(ResourceIcon key) {
        if(images.isEmpty()) try {
            throw new ResourceNotLoadedException();
        } catch (ResourceNotLoadedException e) {
            e.printStackTrace();
        }
        return images.getOrDefault(key, null);
    }

    public static class ResourceNotLoadedException extends Throwable {

        public ResourceNotLoadedException() {
            super("Paranoia resources has not been loaded!");
        }

    }

}
