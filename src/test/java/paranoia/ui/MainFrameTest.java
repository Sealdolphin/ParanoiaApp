package paranoia.ui;

import org.assertj.swing.data.Index;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JTabbedPaneFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import paranoia.core.Clone;
import paranoia.core.Computer;
import paranoia.core.SecurityClearance;
import paranoia.visuals.CerebrealCoretech;
import paranoia.visuals.ComponentName;
import paranoia.visuals.mechanics.Injury;
import paranoia.visuals.mechanics.Moxie;
import paranoia.visuals.mechanics.TreasonStar;
import paranoia.visuals.panels.CardPanel;
import paranoia.visuals.rnd.ParanoiaCard;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MainFrameTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private Clone testClone;
    private static final String testCloneName = "test";
    private static final String testCloneSector = "TST";
    private static final SecurityClearance testCloneClearance = SecurityClearance.INFRARED;
    private static final int testStars = 2;
    private static final int testCards = 4;

    private int[] actionCards;
    private int[] equipmentCards;
    private int mutationCard;
    private int secretSocietyCard;
    private int bonusDutyCard;

    @BeforeClass
    public static void setupOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void onSetUp(){
        GuiActionRunner.execute(Computer::initDatabase);

        //create test clone
        testClone = new Clone(testCloneName, testCloneSector, testCloneClearance, testStars, null);
        actionCards = new int[testCards];
        equipmentCards = new int[testCards];
        mutationCard = new Random().nextInt(ParanoiaCard.MUTATION_CARDS);
        secretSocietyCard = new Random().nextInt(ParanoiaCard.SECRET_SOCIETY_CARDS);
        bonusDutyCard = new Random().nextInt(ParanoiaCard.BONUS_DUTY_CARDS);

        for (int i = 0; i < testCards; i++) {
            actionCards[i] = new Random().nextInt(ParanoiaCard.ACTION_CARDS);
            equipmentCards[i] = new Random().nextInt(ParanoiaCard.EQUIPMENT_CARDS);

            testClone.addCard(Computer.getActionCard(actionCards[i]));
            testClone.addCard(Computer.getEquipmentCard(equipmentCards[i]));
        }
        testClone.addCard(Computer.getMutationCard(mutationCard));
        testClone.addCard(Computer.getSecretSocietyCard(secretSocietyCard));
        testClone.addCard(Computer.getBonusDutyCard(bonusDutyCard));

        CerebrealCoretech frame = GuiActionRunner.execute(() -> new CerebrealCoretech(testClone));
        window = new FrameFixture(robot(), frame);
        window.show();
    }

    @Test
    public void actionCardsTest() {
        window.tabbedPane().selectTab(0);
        JPanelFixture cardPanel = window.panel(ComponentName.CARD_PANEL.name());
        int allCards = cardPanel.targetCastedTo(CardPanel.class).getCards();
        Assert.assertEquals(allCards, testCards);
        //Action cards
        for (int i = 0; i < allCards; i++) {
            String panelName = ParanoiaCard.CardType.ACTION.name() + actionCards[i];
            JPanelFixture card = cardPanel.panel(panelName);
            ParanoiaCard trueCard = card.targetCastedTo(ParanoiaCard.class);
            Assert.assertEquals(trueCard.getType(), ParanoiaCard.CardType.ACTION);
            Assert.assertEquals(trueCard.getId(), actionCards[i]);
        }
    }

    @Test
    public void equipmentCardsTest() {
        window.tabbedPane().selectTab(1);
        JPanelFixture cardPanel = window.panel(ComponentName.CARD_PANEL.name());
        int allCards = cardPanel.targetCastedTo(CardPanel.class).getCards();
        Assert.assertEquals(allCards, testCards);
        //Equipment Cards
        for (int i = 0; i < allCards; i++) {
            String panelName = ParanoiaCard.CardType.EQUIPMENT.name() + equipmentCards[i];
            JPanelFixture card = cardPanel.panel(panelName);
            ParanoiaCard trueCard = card.targetCastedTo(ParanoiaCard.class);
            Assert.assertEquals(trueCard.getType(), ParanoiaCard.CardType.EQUIPMENT);
            Assert.assertEquals(trueCard.getId(), equipmentCards[i]);
        }
    }

    @Test
    public void miscCards() {
        window.tabbedPane().selectTab(2);
        JPanelFixture cardPanel = window.panel(ComponentName.CARD_PANEL.name());
        int allCards = cardPanel.targetCastedTo(CardPanel.class).getCards();
        Assert.assertEquals(allCards, 3);
        Map<ParanoiaCard.CardType, Integer> cardMap = new HashMap<>();
        cardMap.put(ParanoiaCard.CardType.SECRET_SOCIETY, secretSocietyCard);
        cardMap.put(ParanoiaCard.CardType.BONUS_DUTY, bonusDutyCard);
        cardMap.put(ParanoiaCard.CardType.MUTATION, mutationCard);

        cardMap.forEach((key, value) -> {
            JPanelFixture card = cardPanel.panel(key + value.toString());
            ParanoiaCard trueCard = card.targetCastedTo(ParanoiaCard.class);
            Assert.assertEquals(trueCard.getType(), key);
            Assert.assertEquals(trueCard.getId(), value.intValue());
        });

    }

    @Test
    public void cardSkillPanelTest() {
        JTabbedPaneFixture cardSkillPanel = window.tabbedPane();
        cardSkillPanel.requireEnabled();
        cardSkillPanel.requireSelectedTab(Index.atIndex(0));
        cardSkillPanel.requireTabTitles(
            "Action cards",
            "Equipment cards",
            "Miscellaneous cards",
            "Skills and Stats"
        );

        Assert.assertEquals(4, cardSkillPanel.target().getTabCount());
    }

    @Test
    public void selfPanelTest() {
        //Check for basic panels
        JPanelFixture selfPanel = window.panel(ComponentName.SELF_PANEL.name());
        JPanelFixture starPanel = selfPanel.panel(ComponentName.TREASON_STAR_PANEL.name());
        JPanelFixture injuryPanel = selfPanel.panel(ComponentName.INJURY_PANEL.name());
        JPanelFixture moxiePanel = selfPanel.panel(ComponentName.MOXIE_PANEL.name());
        //Checking little panel details
        for (int i = 0; i < TreasonStar.TREASON_STAR_COUNT; i++) {
            starPanel.panel(ComponentName.TREASON_STAR + Integer.toString(i)).requireEnabled();
        }
        for (int i = 0; i < Moxie.MOXIE_COUNT; i++) {
            moxiePanel.panel(ComponentName.MOXIE + Integer.toString(i)).requireEnabled();
        }
        for (int i = 0; i < Injury.INJURY_COUNT; i++) {
            injuryPanel.panel(ComponentName.INJURY + Integer.toString(i)).requireEnabled();
        }
        //Checking label
        JTextComponentFixture infoPanel = selfPanel.textBox(ComponentName.INFO_PANEL.name());
        infoPanel.requireText("///CITIZEN: test-I-TST-1" + System.lineSeparator() +"///XP POINTS: 0");
    }
}
