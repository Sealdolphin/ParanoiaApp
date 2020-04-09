package paranoia.ui;

import org.assertj.swing.fixture.JPanelFixture;
import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicUITest;
import paranoia.services.rnd.ParanoiaCard;
import paranoia.visuals.ComponentName;
import paranoia.visuals.panels.CardPanel;

import java.util.HashMap;
import java.util.Map;

import static paranoia.helper.ParanoiaUtils.testCards;

public class EquipmentUITest extends BasicUITest {

    @Test
    public void miscCards() {
        window.tabbedPane().selectTab(2);
        JPanelFixture cardPanel = window.panel(ComponentName.MISC_CARD_PANEL.name());
        int allCards = cardPanel.targetCastedTo(CardPanel.class).getCards();
        Assert.assertEquals(3, allCards);
        Map<ParanoiaCard.CardType, Integer> cardMap = new HashMap<>();
        cardMap.put(ParanoiaCard.CardType.SECRET_SOCIETY, utils.secretSocietyCard);
        cardMap.put(ParanoiaCard.CardType.BONUS_DUTY, utils.bonusDutyCard);
        cardMap.put(ParanoiaCard.CardType.MUTATION, utils.mutationCard);

        cardMap.forEach((key, value) -> {
            JPanelFixture card = cardPanel.panel(key + value.toString());
            ParanoiaCard trueCard = card.targetCastedTo(ParanoiaCard.class);
            Assert.assertEquals(trueCard.getType(), key);
            Assert.assertEquals(trueCard.getId(), value.intValue());
        });

    }

    @Test
    public void actionCardsTest() {
        window.tabbedPane().selectTab(0);
        JPanelFixture cardPanel = window.panel(ComponentName.ACTION_CARD_PANEL.name());
        int allCards = cardPanel.targetCastedTo(CardPanel.class).getCards();
        Assert.assertEquals(testCards, allCards);
        //Action cards
        for (int i = 0; i < allCards; i++) {
            String panelName = ParanoiaCard.CardType.ACTION.name() + utils.actionCards[i];
            JPanelFixture card = cardPanel.panel(panelName);
            ParanoiaCard trueCard = card.targetCastedTo(ParanoiaCard.class);
            Assert.assertEquals(ParanoiaCard.CardType.ACTION, trueCard.getType());
            Assert.assertEquals(utils.actionCards[i], trueCard.getId());
        }
    }

    @Test
    public void equipmentCardsTest() {
        window.tabbedPane().selectTab(1);
        JPanelFixture cardPanel = window.panel(ComponentName.EQUIPMENT_CARD_PANEL.name());
        int allCards = cardPanel.targetCastedTo(CardPanel.class).getCards();
        Assert.assertEquals(testCards, allCards);
        //Equipment Cards
        for (int i = 0; i < allCards; i++) {
            String panelName = ParanoiaCard.CardType.EQUIPMENT.name() + utils.equipmentCards[i];
            JPanelFixture card = cardPanel.panel(panelName);
            ParanoiaCard trueCard = card.targetCastedTo(ParanoiaCard.class);
            Assert.assertEquals(ParanoiaCard.CardType.EQUIPMENT, trueCard.getType());
            Assert.assertEquals(utils.equipmentCards[i], trueCard.getId());
        }
    }
}
