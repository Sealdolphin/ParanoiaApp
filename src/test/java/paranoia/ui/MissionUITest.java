package paranoia.ui;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.Assert;
import org.junit.Test;
import paranoia.core.cpu.Mission;
import paranoia.helper.BasicUITest;
import paranoia.services.hpdmc.manager.MissionManager;
import paranoia.visuals.ComponentName;
import paranoia.visuals.mechanics.Injury;
import paranoia.visuals.mechanics.Moxie;
import paranoia.visuals.mechanics.TreasonStar;

import java.awt.Color;
import java.awt.font.TextAttribute;


public class MissionUITest extends BasicUITest {

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

    @Test
    public void missionPanelTest() {
        //Locate mission panel
        JPanelFixture missionPanel = window.panel(ComponentName.MISSION_PANEL.name());
        //Locate mission labels

        //Test for missions
        for (Mission mission : utils.testMissions) {
            JTextComponentFixture missionBox =
                missionPanel.textBox(ComponentName.MISSION.name() + mission.getId());

            int missionId = mission.getId();
            MissionManager manager = (MissionManager) controller.getManager(ComponentName.MISSION_PANEL);

            missionBox.requireNotEditable();
            missionBox.requireText(mission.getTitle());
            missionBox.requireToolTip(mission.getDescription());
            missionBox.foreground().requireEqualTo(Color.BLACK);
            GuiActionRunner.execute(
                () -> manager.updateMissionStatus(missionId, Mission.MissionStatus.COMPLETED));
            missionBox =
                missionPanel.textBox(ComponentName.MISSION.name() + mission.getId());
            missionBox.foreground().requireEqualTo(Color.BLACK);
            GuiActionRunner.execute(
                () -> manager.updateMissionStatus(missionId, Mission.MissionStatus.FAILED));
            missionBox =
                missionPanel.textBox(ComponentName.MISSION.name() + mission.getId());
            missionBox.foreground().requireEqualTo(new Color(185, 0, 0));
            Assert.assertEquals(
                TextAttribute.STRIKETHROUGH_ON,
                missionBox.font().target().getAttributes().get(TextAttribute.STRIKETHROUGH)
            );
        }
    }
}
