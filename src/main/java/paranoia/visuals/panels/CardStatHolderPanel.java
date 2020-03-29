package paranoia.visuals.panels;

import paranoia.visuals.rnd.ParanoiaCard;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.util.List;

public class CardStatHolderPanel extends JTabbedPane {

    public CardStatHolderPanel(
        List<ParanoiaCard> actionCards,
        List<ParanoiaCard> equipmentCards,
        List<ParanoiaCard> otherCards
    ) {
        JPanel action = new CardPanel(actionCards);
        JPanel equipment = new CardPanel(equipmentCards);
        JPanel other = new CardPanel(otherCards);

        addTab("Action cards", null, action);
        addTab("Equipment cards", null, equipment);
        addTab("Miscellaneous cards", null, other);
    }

}
