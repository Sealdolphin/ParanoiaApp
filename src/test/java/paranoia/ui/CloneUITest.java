package paranoia.ui;

import org.assertj.swing.fixture.JPanelFixture;
import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicUITest;
import paranoia.visuals.ComponentName;
import paranoia.visuals.mechanics.TreasonStar;

public class CloneUITest extends BasicUITest {

    @Test
    public void cloneNumberTest() {
        JPanelFixture clonePanel = window.panel(ComponentName.TROUBLESHOOTER_PANEL.name());
        clonePanel.requireEnabled();
        for (int i = 0; i < utils.troubleshooters.length; i++) {
            JPanelFixture clone = clonePanel
                .panel(ComponentName.CLONE_PANEL.name() + i);
            clone.requireEnabled();

            //Check for basic panels
            testTreasonStarPanel(clonePanel.panel("null"));
            testInjuredPanel(clonePanel.panel(ComponentName.INJURY_PANEL.name()));
        }
    }

    private void testTreasonStarPanel(JPanelFixture starPanel) {
        for (int i = 0; i < TreasonStar.TREASON_STAR_COUNT; i++) {
            starPanel.panel(ComponentName.TREASON_STAR + Integer.toString(i)).requireEnabled();
        }
    }

    private void testInjuredPanel(JPanelFixture injuredPanel) {
        Assert.assertTrue(true);
    }

    private void testInfoPanel(JPanelFixture infoPanel) {

    }
}
