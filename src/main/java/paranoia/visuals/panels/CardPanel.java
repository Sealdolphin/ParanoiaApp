package paranoia.visuals.panels;

import paranoia.visuals.ComponentName;
import paranoia.visuals.rnd.ParanoiaCard;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.List;

public class CardPanel extends JPanel {

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

}
