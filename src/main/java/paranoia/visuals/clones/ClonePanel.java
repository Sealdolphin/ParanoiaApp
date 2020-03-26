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

public class ClonePanel extends JPanel {

    public ClonePanel(BufferedImage image) {
        //Setup assets
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(layout);

        //Setup fields
        //TODO: read from clones!!
        CloneInfoPanel namePanel = new CloneInfoPanel(Collections.emptyMap(), SecurityClearance.RED);
        CloneInfoPanel detailsPanel = new CloneInfoPanel(Collections.emptyMap(), SecurityClearance.RED);
        JPanel starPanel = TreasonStar.createTreasonStarPanel(3);
        JPanel injuryPanel = Injury.createInjuryPanel(1);
        ParanoiaImage profilePicture = new ParanoiaImage(image, true);
        profilePicture.setPreferredSize(new Dimension(64,64));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(profilePicture);
        bottomPanel.add(injuryPanel);

        //Set horizontal
        add(namePanel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(starPanel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(detailsPanel);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(bottomPanel);


        //Set vertical

    }


}
