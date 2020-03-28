package paranoia.visuals.panels;

import paranoia.core.cpu.Mission;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

public class MissionPanel extends JPanel {

    public MissionPanel(List<Mission> missionFeed) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel lbTitle = new JLabel("Mission:");
        lbTitle.setFont(new Font("Segoe", Font.BOLD, 25));
        lbTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(lbTitle);

        missionFeed.stream().filter(m -> m.getPriority().equals(Mission.MissionPriority.REQUIRED)).forEach(m  -> {
            JComponent v = m.getVisual();
            v.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(v);
        });

        JLabel lbOpTitle = new JLabel("Secondary objectives:");
        lbOpTitle.setFont(new Font("Segoe", Font.BOLD, 20));
        add(lbOpTitle);

        missionFeed.stream().filter(m -> m.getPriority().equals(Mission.MissionPriority.OPTIONAL)).forEach(m  -> {
            JComponent v = m.getVisual();
            v.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(v);
        });

        setOpaque(false);
    }


    public JScrollPane getScrollPanel() {
        JScrollPane pane = new JScrollPane(
            this,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        pane.setPreferredSize(new Dimension(0,400));
        return pane;
    }
}
