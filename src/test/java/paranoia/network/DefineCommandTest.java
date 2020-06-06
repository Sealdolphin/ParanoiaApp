package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.core.Computer;
import paranoia.core.cpu.Skill;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.DefineListenerMock;
import paranoia.services.technical.command.DefineCommand;

import java.util.Random;

public class DefineCommandTest extends BasicNetworkTest {

    private final int fillValue = new Random().nextInt(10) - 5;
    private final boolean lastChoice = Computer.coinFlip();
    private final Skill attribute = Computer.randomItem(Skill.values());
    private final Skill[] disabled = new Skill[]{
        Computer.randomItem(Skill.values()),
        Computer.randomItem(Skill.values()),
        Computer.randomItem(Skill.values())
    };

    private final DefineListenerMock defineMock = new DefineListenerMock(
        fillValue, lastChoice, attribute, disabled
    );

    @Test
    public void testDefineCommand() {
        parser.setDefineListener(defineMock);
        connect();
        DefineCommand command = new DefineCommand(
            fillValue, lastChoice, attribute, disabled, null
        );
        server.sendCommand(command);
        client.listen();
        waitForClient();
        Assert.assertTrue(defineMock.testSuccess());
    }
}
