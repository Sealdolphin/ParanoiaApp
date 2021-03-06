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

    private final boolean self;

    public TroubleShooterPanel(ParanoiaManager<Clone> cpu) {
        this(cpu, false, null);
    }

    public TroubleShooterPanel(ParanoiaManager<Clone> cpu, boolean selfPanel, Clone clone) {
        FlowLayout panelLayout = new FlowLayout();
        setLayout(panelLayout);
        setOpaque(false);
        cpu.addListener(this);
        self = selfPanel;
        if(!selfPanel) {
            panelLayout.setHgap(25);
            panelLayout.setVgap(15);
            setName(ComponentName.TROUBLESHOOTER_PANEL.name());
            updateVisualDataChange(Collections.emptyList());
        } else {
            cpu.updateAsset(clone);
            updateVisualDataChange(Collections.singletonList(clone));
        }
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
        if(self)
            updatedModel.forEach(clone -> add(clone.getSelfVisual()));
        else
            updatedModel.forEach(clone -> add(clone.getVisual()));
        updateUI();
    }
}
