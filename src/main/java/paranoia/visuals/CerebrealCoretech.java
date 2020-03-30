package paranoia.visuals;

import paranoia.core.Clone;
import paranoia.core.cpu.Mission;
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
import java.util.ArrayList;
import java.util.List;

import static paranoia.Paranoia.PARANOIA_BACKGROUND;

public class CerebrealCoretech extends JFrame {

    private List<Clone> troubleShooters;
    private List<Mission> missionFeed = new ArrayList<>();
    private Clone self;
    private GroupLayout layout;

    private JScrollPane troubleShooterPanel;
    private JScrollPane missionPanel;
    private JPanel selfPanel;
    private JTabbedPane cardStatPanel;

    private Boolean isFullScreen = false;

    public CerebrealCoretech(Clone self) {
        this(self, new ArrayList<>());
    }

    public CerebrealCoretech(
        Clone self,
        List<Clone> troubleShooters
    ) {
        this.self = self;
        this.troubleShooters = troubleShooters;

        layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        //Setup metadata
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paranoia");
        //Setup visuals
        getContentPane().setBackground(PARANOIA_BACKGROUND);

        //TODO: remove later
        Mission m0 = new Mission("Secure the package", "Quest given by the Computer. Reward: 500 XP points");
        Mission m1 = new Mission("Disable terrorist bomb", "Quest given by the Computer. Reward: 300 XP points");
        Mission m2 = new Mission(
            "Don't let the Commies take the package", "Quest for IntSec. Reward: Elevated security cake",
            Mission.MissionPriority.OPTIONAL
        );
        m0.complete();
        m2.fail();
        missionFeed.add(m0);
        missionFeed.add(m1);
        missionFeed.add(m2);

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

    private JScrollPane createMissionPanel() {
        return new MissionPanel(missionFeed).getScrollPanel();
    }

    private void refreshLayout() {
        troubleShooterPanel = createTroubleShooterPanel();
        missionPanel = createMissionPanel();
        selfPanel = self.getSelfVisual();
        cardStatPanel = new CardStatHolderPanel(
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
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER,
                                    false
                                    )
                                    .addComponent(
                                        selfPanel,
                                        GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.PREFERRED_SIZE,
                                        Short.MAX_VALUE
                                    )
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
                    layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(cardStatPanel)
                        .addComponent(
                            selfPanel,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            Short.MAX_VALUE
                        )
                )
        );

    }

}
