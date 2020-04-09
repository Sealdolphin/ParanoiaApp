package paranoia.ui;

import org.assertj.swing.data.Index;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JTabbedPaneFixture;
import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicUITest;
import paranoia.visuals.ComponentName;

public class SkillUITest extends BasicUITest {

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
    public void checkOnDefaultSkills() {
        JPanelFixture skillPanel = window.panel(ComponentName.SKILL_PANEL.name());

    }
}
