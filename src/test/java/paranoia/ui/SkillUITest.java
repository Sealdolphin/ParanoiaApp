package paranoia.ui;

import org.assertj.swing.data.Index;
import org.assertj.swing.data.TableCell;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JTabbedPaneFixture;
import org.assertj.swing.fixture.JTableCellFixture;
import org.assertj.swing.fixture.JTableFixture;
import org.junit.Assert;
import org.junit.Test;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
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
        window.tabbedPane().selectTab(3);
        JPanelFixture skillPanel = window.panel(ComponentName.SKILL_PANEL.name());
        skillPanel.requireEnabled();

        //Skills
        JTableFixture skillTable = window.table("Skills");
        skillTable.requireColumnCount(8);
        skillTable.requireRowCount(4);
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 4; row++) {
                JTableCellFixture cell = skillTable.cell(TableCell.row(row).column(column));
                cell.requireNotEditable();
                if(column % 2 == 0) {
                    Skill skill = Skill.values()[row + (column / 2)*4];
                    cell.requireValue(skill.toString());
                } else {
                    cell.requireValue("0");
                }
            }
        }

        //Stats
        JTableFixture statTable = window.table("Stats");
        statTable.requireColumnCount(8);
        statTable.requireRowCount(1);
        for (int column = 0; column < 8; column++) {
            JTableCellFixture cell = statTable.cell(TableCell.row(0).column(column));
            cell.requireNotEditable();
            if(column % 2 == 0) {
                Stat stat = Stat.values()[column / 2];
                cell.requireValue(stat.toString());
            } else {
                cell.requireValue("0");
            }
        }
    }
}
