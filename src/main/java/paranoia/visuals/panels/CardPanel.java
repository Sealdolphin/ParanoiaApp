package paranoia.visuals.panels;

import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.rnd.ParanoiaCard;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Collections;

public class CardPanel extends JPanel implements ParanoiaListener<ParanoiaCard> {

    public CardPanel() {
        GridLayout layout = new GridLayout(0,4);
        setLayout(layout);

        updateVisualDataChange(Collections.emptyList());

        layout.setHgap(15);
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaCard> updatedModel) {
        removeAll();
        updatedModel.forEach(this::add);
        updateUI();
    }
}
