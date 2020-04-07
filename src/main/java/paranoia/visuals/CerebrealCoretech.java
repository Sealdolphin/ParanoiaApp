package paranoia.visuals;

import paranoia.core.Clone;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.visuals.panels.CardStatHolderPanel;
import paranoia.visuals.panels.MissionPanel;
import paranoia.visuals.panels.TroubleShooterPanel;
import paranoia.visuals.rnd.ParanoiaCard;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import java.util.Collections;
import java.util.List;

import static paranoia.Paranoia.PARANOIA_BACKGROUND;

public class CerebrealCoretech extends JFrame {

    private List<Clone> troubleShooters;
    private Clone self;

    private GroupLayout layout;
    //Assets
    private JScrollPane troubleShooterPanel;
    private JScrollPane missionPanel;
    private JPanel selfPanel;

    private Boolean isFullScreen = false;

    public CerebrealCoretech(Clone self, ControlUnit controller) {
        this(self, controller, Collections.emptyList());
    }


    private CerebrealCoretech(
        Clone self,
        ControlUnit controller,
        List<Clone> troubleShooters
    ) {
        this.self = self;
        this.troubleShooters = troubleShooters;
        //noinspection unchecked
        missionPanel = new MissionPanel(
            Collections.emptyList(), controller.getManager(ComponentName.MISSION_PANEL))
            .getScrollPanel();

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

    public void setSelf(Clone clone) {
        self = clone;
        JPanel newSelfPanel = clone.getSelfVisual();
        layout.replace(selfPanel, newSelfPanel);
        selfPanel = newSelfPanel;
    }

    public void addClone(Clone clone) {
        troubleShooters.add(clone);
        JScrollPane newScrollPane = createTroubleShooterPanel();
        layout.replace(troubleShooterPanel, newScrollPane);
        troubleShooterPanel = newScrollPane;
    }

    public void setIsFullScreen(Boolean fullScreen){
        this.isFullScreen = fullScreen;
        setUndecorated(isFullScreen);
    }

    private JScrollPane createTroubleShooterPanel() {
        return new TroubleShooterPanel(troubleShooters).getScrollPane();
    }

    private void refreshLayout() {
        troubleShooterPanel = createTroubleShooterPanel();
        selfPanel = self.getSelfVisual();
        JTabbedPane cardStatPanel = new CardStatHolderPanel(
            self.getCards(ParanoiaCard.CardType.ACTION),
            self.getCards(ParanoiaCard.CardType.EQUIPMENT),
            self.getMiscCards(),
            self.getSkillPanel()
        );

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
                                .addComponent(selfPanel,
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
                                .addComponent(selfPanel,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    Short.MAX_VALUE
                                )
                        )
                )
        );

    }

}
