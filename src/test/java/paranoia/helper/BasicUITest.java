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
import paranoia.services.technical.CommandParser;
import paranoia.services.technical.networking.Network;

import javax.swing.JFrame;

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
        testClone = new TestClone(0);
        controller = GuiActionRunner.execute(() -> new ControlUnit(new Network(new CommandParser())));
        utils = GuiActionRunner.execute(() -> new ParanoiaUtils(controller));

        //showing window
        JFrame coreTech = controller.getVisuals();
        window = new FrameFixture(robot(), coreTech);
        window.show();
    }

}
