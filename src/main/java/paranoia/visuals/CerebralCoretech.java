package paranoia.visuals;

import paranoia.core.Clone;
import paranoia.core.cpu.Mission;
import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.services.hpdmc.ParanoiaController;
import paranoia.services.rnd.ParanoiaCard;
import paranoia.visuals.panels.CardPanel;
import paranoia.visuals.panels.MissionPanel;
import paranoia.visuals.panels.OperationPanel;
import paranoia.visuals.panels.SkillPanel;
import paranoia.visuals.panels.TroubleShooterPanel;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static paranoia.Paranoia.PARANOIA_BACKGROUND;

public class CerebralCoretech extends JFrame {

    private final GroupLayout layout;
    //Assets
    private final JScrollPane troubleShooterPanel;
    private final JScrollPane missionScrollPanel;
    private final JPanel selfPanel;
    private final JTabbedPane cardStatPanel;
    private final JPanel miscPanel;

    //TODO: Settings must implement this
    @SuppressWarnings("FieldCanBeLocal")
    private Boolean isFullScreen = false;

    public CerebralCoretech(ParanoiaController controller, Clone self) {
        //Missions
        MissionPanel missionPanel = new MissionPanel();
        controller.addListener(Mission.class, missionPanel);
        missionScrollPanel = missionPanel.getScrollPanel();
        //Cards
        cardStatPanel = createCardSkillPanel(controller);
        troubleShooterPanel = createTroubleShooterPanel(controller);
        selfPanel = createSelfPanel(self);
        miscPanel = createMiscPanel();

        layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        //Setup metadata
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Paranoia HUB");
        //Setup visuals
        getContentPane().setBackground(PARANOIA_BACKGROUND);
        //Setup closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
//                controller.sendCommand(new DisconnectCommand(null));
                super.windowClosed(e);
            }
        });

        //Assets
        refreshLayout();
        //Assemble
        pack();
        setLocationRelativeTo(null);
    }

    private JTabbedPane createCardSkillPanel(ParanoiaController controller) {
        CardPanel action = new CardPanel();
        CardPanel equipment = new CardPanel();
        CardPanel other = new CardPanel();
        controller.addListener(ParanoiaCard.class, action);
        controller.addListener(ParanoiaCard.class, equipment);
        controller.addListener(ParanoiaCard.class, other);

        SkillPanel skillPanel = new SkillPanel();
        controller.addListener(ParanoiaAttribute.class, skillPanel);

        JTabbedPane holderPanel = new JTabbedPane();
        holderPanel.addTab("Action cards", action);
        holderPanel.addTab("Equipment cards", equipment);
        holderPanel.addTab("Miscellaneous cards", other);
        holderPanel.addTab("Skills and Stats", skillPanel);
        holderPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        holderPanel.setOpaque(false);
        return holderPanel;
    }

    public void setIsFullScreen(Boolean fullScreen){
        this.isFullScreen = fullScreen;
        setUndecorated(isFullScreen);
    }

    private JScrollPane createTroubleShooterPanel(ParanoiaController controller) {
        TroubleShooterPanel tPanel = new TroubleShooterPanel();
        controller.addListener(Clone.class, tPanel);
        return tPanel.getScrollPane();
    }

    private JPanel createSelfPanel(Clone c) {
        //FIXME: need another panel for that
        return new TroubleShooterPanel(true, c);
    }

    public JPanel createMiscPanel() {
        JPanel miscPanel = new JPanel();
        miscPanel.setLayout(new BorderLayout());
        OperationPanel operationPanel = new OperationPanel();

        miscPanel.add(OperationPanel.createOperationPanel(operationPanel), BorderLayout.NORTH);
        miscPanel.add(operationPanel, BorderLayout.CENTER);
        return miscPanel;
    }


    private void refreshLayout() {
        //set horizontal
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(troubleShooterPanel)
                                .addGroup(
                                    layout.createSequentialGroup()
                                        .addComponent(missionScrollPanel)
                                        .addGap(0, 100, Short.MAX_VALUE)
                                )
                        )
                        .addComponent(
                            miscPanel,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                )
                .addGroup(
                    layout.createSequentialGroup()
                        .addComponent(cardStatPanel)
                        .addGap(0, 100, Short.MAX_VALUE)
                        .addComponent(selfPanel)
                )
        );
        //set vertical
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(
                            layout.createSequentialGroup()
                                .addComponent(
                                    troubleShooterPanel,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    Short.MAX_VALUE
                                )
                                .addComponent(missionScrollPanel)
                        )
                        .addComponent(miscPanel)
                )
                .addGap(0, 100, Short.MAX_VALUE)
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(cardStatPanel)
                        .addComponent(selfPanel)
                )
        );

    }

}
