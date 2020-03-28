package paranoia.visuals.clones;

import paranoia.core.SecurityClearance;
import paranoia.visuals.custom.ParanoiaButton;
import paranoia.visuals.custom.ParanoiaImage;
import paranoia.visuals.mechanics.Injury;
import paranoia.visuals.mechanics.Moxie;
import paranoia.visuals.mechanics.TreasonStar;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

public class SelfPanel extends JPanel {

    public SelfPanel(
        BufferedImage profilePicture,
        String fullName,
        SecurityClearance clearance,
        int xpPoints,
        int treasonStars,
        int injury,
        int moxie,
        int crossedOutMoxie
    ){
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        Map<String, String> infoPanel = new LinkedHashMap<>();
        infoPanel.put("CITIZEN",fullName);
        infoPanel.put("XP POINTS", Integer.toString(xpPoints));
        //Elements
        ParanoiaImage profile = new ParanoiaImage(profilePicture, true);
        profile.setPreferredSize(new Dimension(196,196));
        JPanel moxiePanel = Moxie.createMoxiePanel(moxie, crossedOutMoxie);
        JPanel injuryPanel = Injury.createInjuryPanel(injury);
        JPanel starPanel = TreasonStar.createTreasonStarPanel(treasonStars);
        CloneInfoPanel details = new CloneInfoPanel(infoPanel, clearance);
        ParanoiaButton btnStats = new ParanoiaButton("Skills & Stats");
        btnStats.setForeground(new Color(0,0,0));
        btnStats.setBackground(new Color(255, 255, 255));
        btnStats.setHoverBG(new Color(191, 191, 191));
        btnStats.setPressedBG(new Color(128, 128, 128));

        //Horizontal Group
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(btnStats)
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
                                .addComponent(btnStats)
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
