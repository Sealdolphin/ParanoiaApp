package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.core.Computer;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.DiceListenerMock;
import paranoia.services.technical.command.DiceCommand;
import paranoia.services.technical.networking.Network;

import java.util.Random;

public class DiceCommandTest extends BasicNetworkTest {

    private final int success = new Random().nextInt(10);
    private final boolean computer = Computer.coinFlip();

    private final DiceListenerMock diceMock = new DiceListenerMock(success, computer);

    private final Network client = new Network(parser);

    @Test
    public void testDiceCommand() {
        parser.setDiceListener(diceMock);
        connect(client);
        DiceCommand command = new DiceCommand(
            success, computer, null
        );
        server.sendCommand(command);
        client.listen();
        waitForClient();
        Assert.assertTrue(diceMock.testSuccess());
    }
}
