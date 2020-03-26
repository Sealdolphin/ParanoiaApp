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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClonePanel extends JPanel {

    public ClonePanel(BufferedImage image) {
        this(
            image,
            "SYD-R-ULS-7",
            Collections.emptyMap(),
            SecurityClearance.RED,
            1,
            3
        );
    }

    public ClonePanel(
        BufferedImage image,
        String name,
        Map<String, String> details,
        SecurityClearance clearance,
        int treasonStars,
        int injury
    ) {
        //Setup assets
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(layout);

        //Info
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("CITIZEN", name);

        //Setup fields
        CloneInfoPanel namePanel = new CloneInfoPanel(nameMap, clearance);
        CloneInfoPanel detailsPanel = new CloneInfoPanel(details, clearance, true);
        JPanel starPanel = TreasonStar.createTreasonStarPanel(treasonStars);
        JPanel injuryPanel = Injury.createInjuryPanel(injury);
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
