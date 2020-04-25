package paranoia.visuals.panels;

import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.hpdmc.manager.ParanoiaManager;
import paranoia.services.rnd.ParanoiaCard;
import paranoia.visuals.ComponentName;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Collections;

public class CardPanel extends JPanel implements ParanoiaListener<ParanoiaCard> {

    public CardPanel(ParanoiaManager<ParanoiaCard> cpu, ComponentName panelName) {
        GridLayout layout = new GridLayout(0,4);
        setLayout(layout);

        updateVisualDataChange(Collections.emptyList());
        cpu.addListener(this);

        layout.setHgap(15);
        setName(panelName.name());
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaCard> updatedModel) {
        removeAll();
        updatedModel.forEach(this::add);
        updateUI();
    }
}
