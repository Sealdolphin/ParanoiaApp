package paranoia.ui;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.BeforeClass;
import org.junit.Test;
import paranoia.core.Clone;
import paranoia.core.SecurityClearance;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.visuals.messages.RollMessage;

import javax.swing.JDialog;
import java.util.Collections;
import java.util.Random;

import static paranoia.helper.ParanoiaUtils.testCloneName;
import static paranoia.helper.ParanoiaUtils.testCloneSector;
import static paranoia.helper.ParanoiaUtils.testGender;

public class DiceUITest extends AssertJSwingJUnitTestCase {

    private Clone testClone;
    private ControlUnit cpu;
    private Stat testStat;
    private Skill testSkill;
    private DialogFixture dialog;

    private final SecurityClearance clearance =
        SecurityClearance.values()[new Random()
            .nextInt(SecurityClearance.values().length)];
    private static final String testMessage = "Please roll with...";

    @BeforeClass
    public static void setupOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Override
    protected void onSetUp() {
        JDialog rollDialog;
        GuiActionRunner.execute(() -> {
            testClone = new Clone(testCloneName, testCloneSector, clearance, testGender, 0, null);
            cpu = new ControlUnit(testClone);
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
    public void rollMessageTest() {
        //Summon Roll Message dialog
        dialog.requireModal();
        //test combo boxes

        //test labels

        //test positive + negative

    }
}
