package paranoia.visuals.panels;

import paranoia.visuals.rnd.ParanoiaCard;

import javax.swing.JTabbedPane;
import java.util.ArrayList;
import java.util.List;

public class CardStatHolderPanel extends JTabbedPane {

    public CardStatHolderPanel() {
        List<ParanoiaCard> actionCards = new ArrayList<>();
        List<ParanoiaCard> equipmentCards = new ArrayList<>();

        addTab("Action cards", null, new CardPanel(ParanoiaCard.CardType.ACTION, actionCards));
        addTab("Equipment cards", null, new CardPanel(ParanoiaCard.CardType.EQUIPMENT, equipmentCards));
    }

}
