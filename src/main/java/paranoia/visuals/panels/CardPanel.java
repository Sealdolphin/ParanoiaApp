package paranoia.visuals.panels;

import paranoia.visuals.rnd.ParanoiaCard;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.util.List;

public class CardPanel extends JPanel {

    List<ParanoiaCard> cards;

    public CardPanel(ParanoiaCard.CardType type, List<ParanoiaCard> cards) {
        FlowLayout layout = new FlowLayout(FlowLayout.LEADING);
        setLayout(layout);

        layout.setHgap(30);
        layout.setHgap(15);

        cards.forEach(this::add);
    }

}
