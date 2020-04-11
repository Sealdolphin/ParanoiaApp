package paranoia.visuals.clones;

import paranoia.core.SecurityClearance;
import paranoia.visuals.ComponentName;
import paranoia.visuals.custom.ParanoiaImage;
import paranoia.visuals.mechanics.Injury;
import paranoia.visuals.mechanics.Moxie;
import paranoia.visuals.mechanics.TreasonStar;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

import static paranoia.Paranoia.PARANOIA_BACKGROUND;

public class SelfPanel extends JPanel {

    public SelfPanel(
        BufferedImage profilePicture,
        String fullName,
        SecurityClearance clearance,
        int xpPoints,
        int treasonStars,
        int injury,
        int moxie,
        int crossedOutMoxie,
        int playerId
    ){
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        setBackground(PARANOIA_BACKGROUND);
        setName(ComponentName.SELF_PANEL.name());

        Map<String, String> infoPanel = new LinkedHashMap<>();
        infoPanel.put("CITIZEN",fullName);
        infoPanel.put("XP POINTS", Integer.toString(xpPoints));
        //Elements
        ParanoiaImage profile = new ParanoiaImage(profilePicture, true);
        profile.setPreferredSize(new Dimension(196,196));
        JPanel moxiePanel = Moxie.createMoxiePanel(moxie,playerId, crossedOutMoxie);
        JPanel injuryPanel = Injury.createInjuryPanel(injury,playerId);
        JPanel starPanel = TreasonStar.createTreasonStarPanel(treasonStars,playerId);
        CloneInfoPanel details = new CloneInfoPanel(infoPanel, clearance);

        //Horizontal Group
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(injuryPanel)
                        .addComponent(moxiePanel)
                        .addComponent(starPanel)
                    )
                    .addComponent(profile)
                )
                .addComponent(details)
        );

        //Vertical group
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addGroup(
                            layout.createSequentialGroup()
                                .addComponent(injuryPanel)
                                .addComponent(moxiePanel)
                                .addComponent(starPanel)
                        )
                        .addComponent(profile)
                )
                .addGap(5)
                .addComponent(details)
        );
    }
}
