package paranoia.ui;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JComboBoxFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import paranoia.core.Computer;
import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.helper.TestClone;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.visuals.ComponentName;
import paranoia.visuals.messages.RollMessage;

import javax.swing.JDialog;
import java.awt.Dimension;
import java.util.Collections;

public class DiceUITest extends AssertJSwingJUnitTestCase {

    private TestClone testClone;
    private ControlUnit cpu;
    private DialogFixture dialog;


    private final Stat testStat = Computer.randomItem(Stat.values());
    private final Skill testSkill = Computer.randomItem(Skill.values());

    private static final String testMessage = "Please roll with...";

    @BeforeClass
    public static void setupOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Override
    protected void onSetUp() {
        JDialog rollDialog;
        GuiActionRunner.execute(() -> {
            testClone = new TestClone();
            cpu = new ControlUnit(testClone);
            testClone.attributes.iterator().forEachRemaining(paranoiaAttribute ->
                cpu.updateAsset(paranoiaAttribute, ComponentName.SKILL_PANEL));

        });
        rollDialog = GuiActionRunner.execute(() -> new RollMessage(
            cpu, testStat, false, testSkill, false,
            Collections.emptyMap(), Collections.emptyMap(), testMessage
            )
        );
        dialog = new DialogFixture(robot(), rollDialog);
        dialog.show();
    }

    @Test
    public void basicMessageTest() {
        //Summon Roll Message dialog
        dialog.requireModal();
        Integer testSkillValue = getTestSkill();
        Integer testStatValue = getTestStat();

        //Find test properties
        JComboBoxFixture skill = dialog.comboBox("cbSkill");
        JComboBoxFixture stat = dialog.comboBox("cbStat");
        JLabelFixture lbSkill = dialog.label("lbSkill");
        JLabelFixture lbStat = dialog.label("lbStat");
        JLabelFixture lbNode = dialog.label("lbNode");

        //test combo boxes
        skill.requireSelection(testSkill.toString());
        stat.requireSelection(testStat.toString());
        //test labels
        lbSkill.requireText(testSkillValue.toString());
        lbStat.requireText(testStatValue.toString());
        //test positive + negative

        //Calculate node
        int node = testSkillValue + testStatValue;
        lbNode.requireText(Integer.toString(node));

    }

    @Test
    public void exitMessageTest() {
        //Close dialog
        dialog.close();
        //Assert roll action to false
        //Assert roll action value
        FrameFixture coreTech = new FrameFixture(robot(), cpu.getVisuals());
        coreTech.show();
        JPanelFixture miscPanel = coreTech.panel(ComponentName.MISC_PANEL.name());
        Assert.assertEquals(0, miscPanel.target().getComponentCount());
    }

    @Test
    public void rollMessageTest() {
        //Click roll button
        dialog.requireVisible();
        JButtonFixture btnRoll = dialog.button("btnRoll");
        btnRoll.click();
        //Assert dialog closed
        dialog.requireNotVisible();

        //Assert roll action value
        FrameFixture coreTech = new FrameFixture(robot(), cpu.getVisuals());
        coreTech.show(new Dimension(700, 300));
        JPanelFixture miscPanel = coreTech.panel(ComponentName.MISC_PANEL.name());
        JPanelFixture dicePanel = miscPanel.panel(ComponentName.DICE_PANEL.name());
        dicePanel.requireEnabled();
        dicePanel.requireVisible();
        int dice = Math.abs(getTestSkill() + getTestStat()) + 1;
        Assert.assertEquals(dice, dicePanel.target().getComponentCount());
        //Close roll panel
        miscPanel.button().click();
        Assert.assertEquals(0, miscPanel.target().getComponentCount());
    }

    private int getTestSkill() {
        return testClone.attributes.stream()
            .filter(attr -> attr.getName().equals(testSkill.toString()))
            //If not found it is 0 -> not initialized
            .findFirst().orElse(new ParanoiaAttribute(testSkill.toString(), 0))
            .getValue();
    }

    private int getTestStat() {
        return testClone.attributes.stream()
            .filter(attr -> attr.getName().equals(testStat.toString()))
            //If not found it is 0 -> not initialized
            .findFirst().orElse(new ParanoiaAttribute(testStat.toString(), 0))
            .getValue();
    }
}
