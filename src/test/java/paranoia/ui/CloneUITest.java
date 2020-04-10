package paranoia.ui;

import org.assertj.swing.fixture.JPanelFixture;
import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicUITest;
import paranoia.visuals.ComponentName;
import paranoia.visuals.mechanics.Injury;
import paranoia.visuals.mechanics.Moxie;
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

    @Test
    public void selfUITest() {
        JPanelFixture selfPanel = window.panel(ComponentName.SELF_PANEL.name());
        selfPanel.requireEnabled();
        //Check for basic panels
        testTreasonStarPanel(selfPanel.panel(ComponentName.TREASON_STAR_PANEL.name() + "0"), 0);
        testInjuredPanel(selfPanel.panel(ComponentName.INJURY_PANEL.name() + "0"), 0);
        testMoxiePanel(selfPanel.panel(ComponentName.MOXIE_PANEL + "0"));
    }

    private void testTreasonStarPanel(JPanelFixture starPanel, int playerID) {
        for (int i = 0; i < TreasonStar.TREASON_STAR_COUNT; i++) {
            TreasonStar star = starPanel.panel(
                ComponentName.TREASON_STAR + Integer.toString(playerID) + i
            ).requireEnabled().targetCastedTo(TreasonStar.class);
            Assert.assertEquals(
                String.format(
                    "Player#%d has %d stars evaluating star no. %d:",
                    playerID, utils.treasonStars[playerID], i + 1
                ),
                i < utils.treasonStars[playerID] , star.isActive()
            );
        }
    }

    private void testInjuredPanel(JPanelFixture injuryPanel, int playerID) {
        for (int i = 0; i < Injury.INJURY_COUNT; i++) {
            Injury injury = injuryPanel.panel(
                ComponentName.INJURY + Integer.toString(playerID) + i
            ).requireEnabled().targetCastedTo(Injury.class);
            Assert.assertEquals(
                String.format(
                    "Player#%d has %d injuries evaluating injury no. %d:",
                    playerID, utils.injuries[playerID], i + 1
                ),
                i < utils.injuries[playerID] , injury.isActive()
            );
        }
    }

    private void testMoxiePanel(JPanelFixture moxiePanel) {
        for (int i = 0; i < Moxie.MOXIE_COUNT; i++) {
            Moxie moxie = moxiePanel.panel(
                ComponentName.MOXIE + Integer.toString(0) + i
            ).requireEnabled().targetCastedTo(Moxie.class);
            //Crossed out
            Assert.assertEquals(
                String.format(
                    "Player#0 has %d moxies crossed out evaluating moxie no. %d:",
                    utils.crossedOut, i + 1
                ),
                i >= Moxie.MOXIE_COUNT - utils.crossedOut,
                moxie.crossedOut()
            );
            if(moxie.crossedOut()) {
                Assert.assertFalse("Crossed out moxie should be inactive", moxie.isActive());
            } else {
                Assert.assertEquals(
                    String.format(
                        "Player#0 has %d moxies evaluating moxie no. %d:",
                        utils.moxies, i + 1
                    ),
                    i < utils.moxies, moxie.isActive()
                );
            }
        }
    }
}
