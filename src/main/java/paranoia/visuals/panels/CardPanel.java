package paranoia.visuals.panels;

import paranoia.visuals.rnd.ParanoiaCard;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.List;

public class CardPanel extends JPanel {

    List<ParanoiaCard> cards;

    public CardPanel(List<ParanoiaCard> cards) {
        GridLayout layout = new GridLayout(0,4);
        setLayout(layout);

        layout.setHgap(15);
        layout.setHgap(15);

        cards.forEach(this::add);
    }

}
