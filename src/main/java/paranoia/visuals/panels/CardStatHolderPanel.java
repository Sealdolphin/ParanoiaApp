package paranoia.visuals.panels;

import paranoia.visuals.rnd.ParanoiaCard;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.util.List;

public class CardStatHolderPanel extends JTabbedPane {

    public CardStatHolderPanel(List<ParanoiaCard> actionCards, List<ParanoiaCard> equipmentCards) {
        JPanel action = new CardPanel(ParanoiaCard.CardType.ACTION, actionCards);
        JPanel equipment = new CardPanel(ParanoiaCard.CardType.EQUIPMENT, equipmentCards);

        addTab("Action cards", null, action);
        addTab("Equipment cards", null, equipment);
    }

}
