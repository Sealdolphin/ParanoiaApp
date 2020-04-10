package paranoia.visuals.panels;

import paranoia.core.Clone;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.hpdmc.manager.ParanoiaManager;
import paranoia.visuals.ComponentName;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.Collections;

public class TroubleShooterPanel extends JPanel implements ParanoiaListener<Clone> {

    public TroubleShooterPanel(ParanoiaManager<Clone> cpu) {
        FlowLayout panelLayout = new FlowLayout();
        panelLayout.setHgap(25);
        panelLayout.setVgap(15);
        setOpaque(false);
        setLayout(panelLayout);
        cpu.addListener(this);
        setName(ComponentName.TROUBLESHOOTER_PANEL.name());
        updateVisualDataChange(Collections.emptyList());
    }

    public JScrollPane getScrollPane() {
        return new JScrollPane(
            this,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
    }

    @Override
    public void updateVisualDataChange(Collection<Clone> updatedModel) {
        removeAll();
        updatedModel.forEach( clone -> add(clone.getVisual()));
    }
}
