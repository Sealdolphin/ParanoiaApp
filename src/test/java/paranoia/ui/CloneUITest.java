package paranoia.ui;

import org.assertj.swing.fixture.JPanelFixture;
import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicUITest;
import paranoia.visuals.ComponentName;
import paranoia.visuals.mechanics.Injury;
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
            testTreasonStarPanel(clonePanel.panel(ComponentName.TREASON_STAR_PANEL.name() + i), i);
            testInjuredPanel(clonePanel.panel(ComponentName.INJURY_PANEL.name() + i), i);
        }
    }

    private void testTreasonStarPanel(JPanelFixture starPanel, int playerId) {
        for (int i = 0; i < TreasonStar.TREASON_STAR_COUNT; i++) {
            TreasonStar star = starPanel.panel(
                ComponentName.TREASON_STAR + Integer.toString(playerId) + i
            ).requireEnabled().targetCastedTo(TreasonStar.class);
            Assert.assertEquals(i < utils.treasonStars[playerId] , star.isActive());
        }
    }

    private void testInjuredPanel(JPanelFixture injuryPanel, int playerId) {
        for (int i = 0; i < Injury.INJURY_COUNT; i++) {
            Injury injury = injuryPanel.panel(
                ComponentName.INJURY + Integer.toString(playerId) + i
            ).requireEnabled().targetCastedTo(Injury.class);
            Assert.assertEquals(i < utils.injuries[playerId] , injury.isActive());
        }
    }
}
