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

    private Collection<ParanoiaCard> cards;

    public CardPanel(ParanoiaManager<ParanoiaCard> cpu, ComponentName panelName) {
        GridLayout layout = new GridLayout(0,4);
        setLayout(layout);
        cards = Collections.emptyList();

        updateVisualDataChange(cards);
        cpu.addListener(this);

        layout.setHgap(15);
        setName(panelName.name());
    }

    public int getCards() {
        return cards.size();
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaCard> updatedModel) {
        cards = updatedModel;
        updatedModel.forEach(this::add);
    }
}
