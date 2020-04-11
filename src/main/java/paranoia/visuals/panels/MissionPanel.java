package paranoia.visuals.panels;

import paranoia.core.cpu.Mission;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.hpdmc.manager.ParanoiaManager;
import paranoia.services.plc.AssetManager;
import paranoia.visuals.ComponentName;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;
import java.util.Collections;

public class MissionPanel extends JPanel implements ParanoiaListener<Mission> {

    private final JLabel lbTitle = new JLabel("Mission:");
    private final JLabel lbOpTitle = new JLabel("Secondary objectives:");

    public MissionPanel(ParanoiaManager<Mission> cpu) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        updateVisualDataChange(Collections.emptyList());
        cpu.addListener(this);
        setName(ComponentName.MISSION_PANEL.name());
        setOpaque(false);
    }

    public JScrollPane getScrollPanel() {
        JScrollPane pane = new JScrollPane(
            this,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        pane.setPreferredSize(new Dimension(0,500));
        return pane;
    }

    private void getMissions(Collection<Mission> missions, Mission.MissionPriority priority) {
        missions.stream().filter(m -> m.getPriority()
            .equals(priority)).forEach(m -> {
                JComponent visual = m.getVisual();
                visual.setAlignmentX(Component.LEFT_ALIGNMENT);
                add(visual);
        });
    }

    @Override
    public void updateVisualDataChange(Collection<Mission> updatedModel) {
        removeAll();
        lbTitle.setFont(AssetManager.getFont(25, true, false));
        lbTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(lbTitle);

        getMissions(updatedModel, Mission.MissionPriority.REQUIRED);

        lbOpTitle.setFont(AssetManager.getFont(20, true, false));
        add(lbOpTitle);

        getMissions(updatedModel, Mission.MissionPriority.OPTIONAL);
        updateUI();
    }
}
