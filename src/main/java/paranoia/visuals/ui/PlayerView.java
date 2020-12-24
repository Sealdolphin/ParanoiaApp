package paranoia.visuals.ui;

import daiv.ui.AssetManager;
import daiv.ui.visuals.ParanoiaImage;
import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.services.plc.ResourceManager;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import static paranoia.services.plc.ResourceManager.ResourceIcon.MISTERY_CLONE;

public class PlayerView extends JPanel {

    public PlayerView(String name, ParanoiaAttribute pick, BufferedImage image) {
        JLabel lbName = new JLabel(name);
        JLabel lbLastPick = new JLabel("Last picked: " + pick.getName() + " " + pick.getValue());
        ParanoiaImage profile = new ParanoiaImage(
            image == null ? ResourceManager.getResource(MISTERY_CLONE) : image
        );

        lbName.setFont(AssetManager.getBoldFont(25));
        lbLastPick.setFont(AssetManager.getFont(12, true, true, false));

        profile.setMaximumSize(new Dimension(160,160));
        profile.setPreferredSize(profile.getMaximumSize());

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(profile);
        add(lbName);
        add(lbLastPick);

        for (Component comp : getComponents()) {
            ((JComponent) comp).setAlignmentX(CENTER_ALIGNMENT);
        }
    }

}
