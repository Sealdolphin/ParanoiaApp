package paranoia.ui;

import org.assertj.swing.data.Index;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JTabbedPaneFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import paranoia.core.Clone;
import paranoia.core.SecurityClearance;
import paranoia.visuals.CerebrealCoretech;
import paranoia.visuals.ComponentName;
import paranoia.visuals.mechanics.Injury;
import paranoia.visuals.mechanics.Moxie;
import paranoia.visuals.mechanics.TreasonStar;


public class MainFrameTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private Clone testClone;

    @BeforeClass
    public static void setupOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void onSetUp(){
        //create test clone
        testClone = new Clone("Test", "TST", SecurityClearance.INFRARED, 0, null);

        CerebrealCoretech frame = GuiActionRunner.execute(() -> new CerebrealCoretech(testClone));
        window = new FrameFixture(robot(), frame);
        window.show();
    }

    @Test
    public void CardSkillPanelTest() {
        JTabbedPaneFixture cardSkillPanel = window.tabbedPane();
        cardSkillPanel.requireEnabled();
        cardSkillPanel.requireSelectedTab(Index.atIndex(0));
        cardSkillPanel.requireTabTitles(
            "Action cards",
            "Equipment cards",
            "Miscellaneous cards",
            "Skills and Stats"
        );
    }

    @Test
    public void SelfPanelTest() {
        JPanelFixture selfPanel = window.panel(ComponentName.SELF_PANEL.name());
        JPanelFixture starPanel = selfPanel.panel(ComponentName.TREASON_STAR_PANEL.name());
        JPanelFixture injuryPanel = selfPanel.panel(ComponentName.INJURY_PANEL.name());
        JPanelFixture moxiePanel = selfPanel.panel(ComponentName.MOXIE_PANEL.name());

        for (int i = 0; i < TreasonStar.TREASON_STAR_COUNT; i++) {
            starPanel.panel(ComponentName.TREASON_STAR + Integer.toString(i)).requireEnabled();
        }
        for (int i = 0; i < Moxie.MOXIE_COUNT; i++) {
            moxiePanel.panel(ComponentName.MOXIE + Integer.toString(i)).requireEnabled();
        }
        for (int i = 0; i < Injury.INJURY_COUNT; i++) {
            injuryPanel.panel(ComponentName.INJURY + Integer.toString(i)).requireEnabled();
        }
    }
}
