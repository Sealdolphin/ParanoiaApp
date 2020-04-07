package paranoia.visuals.panels;

import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.rnd.ParanoiaCard;
import paranoia.visuals.ComponentName;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.List;

public class CardPanel extends JPanel implements ParanoiaListener<ParanoiaCard> {

    private List<ParanoiaCard> cards;

    public CardPanel(List<ParanoiaCard> cards) {
        GridLayout layout = new GridLayout(0,4);
        setLayout(layout);

        layout.setHgap(15);

        cards.forEach(this::add);
        setName(ComponentName.CARD_PANEL.name());
        this.cards = cards;
    }

    public int getCards() {
        return cards.size();
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaCard> updatedModel) {

    }
}
