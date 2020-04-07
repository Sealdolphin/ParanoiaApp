package paranoia.visuals.panels;

import paranoia.services.rnd.ParanoiaCard;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.util.List;

public class CardStatHolderPanel extends JTabbedPane {

    public CardStatHolderPanel(
        List<ParanoiaCard> actionCards,
        List<ParanoiaCard> equipmentCards,
        List<ParanoiaCard> otherCards,
        SkillPanel skillPanel
    ) {
        JPanel action = new CardPanel(actionCards);
        JPanel equipment = new CardPanel(equipmentCards);
        JPanel other = new CardPanel(otherCards);

        addTab("Action cards", action);
        addTab("Equipment cards", equipment);
        addTab("Miscellaneous cards", other);
        addTab("Skills and Stats", skillPanel);

        setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
    }

}
