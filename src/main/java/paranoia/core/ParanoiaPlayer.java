package paranoia.core;

import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.visuals.ui.PlayerView;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;

public class ParanoiaPlayer implements ICoreTechPart {

    private final String name;
    private ParanoiaAttribute lastPick;
    private BufferedImage profile = null;

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
