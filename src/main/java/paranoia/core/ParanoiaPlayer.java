package paranoia.core;

import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.services.plc.ResourceManager;
import paranoia.visuals.ui.PlayerView;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;

import static paranoia.services.plc.ResourceManager.ResourceIcon.MISTERY_CLONE;

public class ParanoiaPlayer implements ICoreTechPart {

    private final String name;
    private ParanoiaAttribute lastPick;
    private BufferedImage profile = ResourceManager.getResource(MISTERY_CLONE);

    public ParanoiaPlayer(String name) {
        this.name = name;
    }

    public void pick(ParanoiaAttribute pick) {
        lastPick = pick;
    }

    @Override
    public JPanel getVisual() {
        return new PlayerView(name, lastPick, profile);
    }

    public String getName() {
        return name;
    }
}
