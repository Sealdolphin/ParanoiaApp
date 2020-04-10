package paranoia.helper;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import paranoia.core.Clone;
import paranoia.core.Computer;
import paranoia.services.hpdmc.ControlUnit;

import javax.swing.JFrame;

import static paranoia.helper.ParanoiaUtils.testCloneClearance;
import static paranoia.helper.ParanoiaUtils.testCloneName;
import static paranoia.helper.ParanoiaUtils.testCloneSector;
import static paranoia.helper.ParanoiaUtils.testGender;

public abstract class BasicUITest extends AssertJSwingJUnitTestCase {

    protected FrameFixture window;
    protected Clone testClone;
    protected ControlUnit controller;
    protected ParanoiaUtils utils;

    @BeforeClass
    public static void setupOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void onSetUp(){
        GuiActionRunner.execute(Computer::initDatabase);
        //create test clone
        testClone = new Clone(testCloneName, testCloneSector, testCloneClearance, testGender, 0, null);
        controller = GuiActionRunner.execute(() -> new ControlUnit(testClone));
        utils = GuiActionRunner.execute(() -> new ParanoiaUtils(controller));

        //showing window
        JFrame coreTech = controller.getVisuals();
        window = new FrameFixture(robot(), coreTech);
        window.show();
    }

}
