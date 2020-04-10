package paranoia.visuals;

import paranoia.core.Clone;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.visuals.panels.CardPanel;
import paranoia.visuals.panels.MissionPanel;
import paranoia.visuals.panels.SkillPanel;
import paranoia.visuals.panels.TroubleShooterPanel;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import static paranoia.Paranoia.PARANOIA_BACKGROUND;

public class CerebralCoretech extends JFrame {

    private final GroupLayout layout;
    //Assets
    private final JScrollPane troubleShooterPanel;
    private final JScrollPane missionPanel;
    private final JPanel selfPanel;
    private final JTabbedPane cardStatPanel;

    private Boolean isFullScreen = false;

    public CerebralCoretech(ControlUnit controller, Clone self) {
        //noinspection unchecked
        missionPanel = new MissionPanel(controller.getManager(ComponentName.MISSION_PANEL)).getScrollPanel();
        cardStatPanel = createCardSkillPanel(controller);
        troubleShooterPanel = createTroubleShooterPanel(controller);
        selfPanel = createSelfPanel(controller, self);

        layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        //Setup metadata
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paranoia");
        //Setup visuals
        getContentPane().setBackground(PARANOIA_BACKGROUND);

        //Assets
        refreshLayout();
        //Assemble
        pack();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private JTabbedPane createCardSkillPanel(ControlUnit controller) {
        JPanel action = new CardPanel(
            controller.getManager(ComponentName.ACTION_CARD_PANEL),
            ComponentName.ACTION_CARD_PANEL
        );
        JPanel equipment = new CardPanel(
            controller.getManager(ComponentName.EQUIPMENT_CARD_PANEL),
            ComponentName.EQUIPMENT_CARD_PANEL
        );
        JPanel other = new CardPanel(
            controller.getManager(ComponentName.MISC_CARD_PANEL),
            ComponentName.MISC_CARD_PANEL
        );
        JPanel skillPanel = new SkillPanel(
            controller.getManager(ComponentName.SKILL_PANEL)
        );
        JTabbedPane holderPanel = new JTabbedPane();
        holderPanel.addTab("Action cards", action);
        holderPanel.addTab("Equipment cards", equipment);
        holderPanel.addTab("Miscellaneous cards", other);
        holderPanel.addTab("Skills and Stats", skillPanel);
        holderPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        return holderPanel;
    }

    public void setIsFullScreen(Boolean fullScreen){
        this.isFullScreen = fullScreen;
        setUndecorated(isFullScreen);
    }

    @SuppressWarnings("unchecked")
    private JScrollPane createTroubleShooterPanel(ControlUnit controller) {
        return new TroubleShooterPanel(controller.getManager(
            ComponentName.TROUBLESHOOTER_PANEL)).getScrollPane();
    }

    @SuppressWarnings("unchecked")
    private JPanel createSelfPanel(ControlUnit controller, Clone c) {
        return new TroubleShooterPanel(controller.getManager(ComponentName.SELF_PANEL), true, c);
    }


    private void refreshLayout() {
        //set horizontal
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(
                            troubleShooterPanel,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE
                        )
                        .addGroup(
                            layout.createSequentialGroup()
                                .addComponent(missionPanel)
                                .addContainerGap(500, Short.MAX_VALUE)
                        )
                        .addGroup(
                            layout.createSequentialGroup()
                                .addComponent(cardStatPanel)
                                .addContainerGap(500, Short.MAX_VALUE)
                                .addComponent(
                                    selfPanel,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    Short.MAX_VALUE
                                )
                        )
                )
        );

        //set vertical
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(
                    troubleShooterPanel,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE,
                    Short.MAX_VALUE
                )
                .addComponent(missionPanel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 300, Short.MAX_VALUE)
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(cardStatPanel)
                        .addGroup(layout.createBaselineGroup(false, false)
                                .addComponent(
                                    selfPanel,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    Short.MAX_VALUE
                                )
                        )
                )
        );

    }

}
