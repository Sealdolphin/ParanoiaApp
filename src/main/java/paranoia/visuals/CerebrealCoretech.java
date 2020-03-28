package paranoia.visuals;

import paranoia.core.Clone;
import paranoia.core.cpu.Mission;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

        Mission m0 = new Mission("Secure the package", "");
        Mission m1 = new Mission("Disable terrorist bomb", "");
        Mission m2 = new Mission(
            "Don't let the Commies take the package", "",
            Mission.MissionPriority.OPTIONAL
        );
        m0.complete();
        m2.fail();
        missionFeed.add(m0);
        missionFeed.add(m1);
        missionFeed.add(m2);
        missionFeed.add(m2);
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
        JPanel tsPanel = new JPanel();
        FlowLayout panelLayout = new FlowLayout();
        panelLayout.setHgap(25);
        panelLayout.setVgap(15);
        tsPanel.setBackground(PARANOIA_BACKGROUND);
        tsPanel.setLayout(panelLayout);
        troubleShooters.forEach( clone -> tsPanel.add(clone.getVisual()));
        return new JScrollPane(
            tsPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
    }

    private JScrollPane createMissionPanel() {
        JPanel mPanel = new JPanel();
        mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
        JLabel lbTitle = new JLabel("Mission:");
        lbTitle.setFont(new Font("Segoe", Font.BOLD, 25));
        lbTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        mPanel.add(lbTitle);

        missionFeed.stream().filter(m -> m.getPriority().equals(Mission.MissionPriority.REQUIRED)).forEach(m  -> {
            JComponent v = m.getVisual();
            v.setAlignmentX(Component.LEFT_ALIGNMENT);
            mPanel.add(v);
        });

        JLabel lbOpTitle = new JLabel("Secondary objectives:");
        lbOpTitle.setFont(new Font("Segoe", Font.BOLD, 20));
        mPanel.add(lbOpTitle);

        missionFeed.stream().filter(m -> m.getPriority().equals(Mission.MissionPriority.OPTIONAL)).forEach(m  -> {
            JComponent v = m.getVisual();
            v.setAlignmentX(Component.LEFT_ALIGNMENT);
            mPanel.add(v);
        });

        mPanel.setOpaque(false);
        JScrollPane pane = new JScrollPane(
            mPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        pane.setPreferredSize(new Dimension(0,400));
        return pane;
    }

    private void refreshLayout() {
        troubleShooterPanel = createTroubleShooterPanel();
        missionPanel = createMissionPanel();
        selfPanel = self.getSelfVisual();

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
                .addComponent(
                    selfPanel,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE,
                    Short.MAX_VALUE
                )
        );

    }

}
