package paranoia.core;

import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.services.plc.ResourceManager;
import paranoia.visuals.ui.PlayerView;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;

import static paranoia.services.plc.ResourceManager.ResourceIcon.MISTERY_CLONE;

public class ParanoiaPlayer implements ICoreTechPart {

    private final String name;
    private final int id;
    private ParanoiaAttribute lastPick = new ParanoiaAttribute("No picks", null);
    private BufferedImage profile = ResourceManager.getResource(MISTERY_CLONE);

    /**
     * Create other players
     * @param name player name
     * @param id player ID
     */
    public ParanoiaPlayer(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Create self
     * @param name player name
     */
    public ParanoiaPlayer(String name) {
        this(name, -1);
    }

    public void pick(ParanoiaAttribute pick) {
        lastPick = pick;
    }

    public boolean isSelf() {
        return id == -1;
    }

    public int getID() {
        return id;
    }

    public void changeProfile(BufferedImage image) {
        this.profile = image;
    }

    @Override
    public JPanel getVisual() {
        return new PlayerView(name, lastPick, profile);
    }

    public String getName() {
        return name;
    }
}
