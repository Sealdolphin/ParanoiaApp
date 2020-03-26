package paranoia.visuals.clones;

import paranoia.core.SecurityClearance;
import paranoia.visuals.custom.ParanoiaImage;
import paranoia.visuals.mechanics.Injury;
import paranoia.visuals.mechanics.TreasonStar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ClonePanel extends JPanel {

    public ClonePanel(BufferedImage image) {
        //Setup assets
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(layout);

        //Info
        Map<String, String> name = new HashMap<>();
        name.put("CITIZEN","SYD-R-ULS-7");

        Map<String, String> details = new HashMap<>();
        details.put("CIVIC ZEAL","UNKNOWN");
        details.put("MARKET VALUE","UNKNOWN");
        details.put("XP","230 XP POINTS");
        //Setup fields
        //TODO: read from clones!!
        CloneInfoPanel namePanel = new CloneInfoPanel(name, SecurityClearance.RED);
        CloneInfoPanel detailsPanel = new CloneInfoPanel(details, SecurityClearance.RED, true);
        JPanel starPanel = TreasonStar.createTreasonStarPanel(3);
        JPanel injuryPanel = Injury.createInjuryPanel(1);
        ParanoiaImage profilePicture = new ParanoiaImage(image, true);
        profilePicture.setPreferredSize(new Dimension(96,96));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        bottomPanel.add(profilePicture);
        bottomPanel.add(injuryPanel);

        //Alingment
        if(starPanel != null)
            starPanel.setAlignmentX(RIGHT_ALIGNMENT);

        //Set horizontal
        add(namePanel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(starPanel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(detailsPanel);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(bottomPanel);
    }


}
