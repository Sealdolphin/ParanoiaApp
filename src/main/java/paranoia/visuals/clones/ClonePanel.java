package paranoia.visuals.clones;

import paranoia.core.SecurityClearance;
import paranoia.visuals.custom.ParanoiaImage;
import paranoia.visuals.mechanics.Injury;
import paranoia.visuals.mechanics.TreasonStar;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
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
        GroupLayout layout = new GroupLayout(this);
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
        profilePicture.setMaximumSize(new Dimension(70,70));
        profilePicture.setMinimumSize(new Dimension(70,70));

        //Set horizontal
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(namePanel)
            .addComponent(starPanel)
            .addComponent(detailsPanel)
            .addGroup(layout.createSequentialGroup()
                .addComponent(profilePicture)
                .addGap(30)
                .addComponent(injuryPanel)
            )
        );
        //Set vertical
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(namePanel)
                .addComponent(starPanel)
                .addComponent(detailsPanel)
                .addGap(5)
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(profilePicture)
                        .addComponent(injuryPanel)
                )
        );

    }


}
