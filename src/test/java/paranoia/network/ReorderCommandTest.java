package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.ReorderListenerMock;
import paranoia.services.technical.command.ReorderCommand;

import java.util.Random;

public class ReorderCommandTest extends BasicNetworkTest {

    private final Integer[] order = new Integer[]{
        new Random().nextInt(4),
        new Random().nextInt(4),
        new Random().nextInt(4),
        new Random().nextInt(4)
    };
    private final String player = "testPlayer";

    private final ReorderListenerMock reorderMock = new ReorderListenerMock(player, order);

    @Test
    public void testReorderCommand() {
        parser.setReorderListener(reorderMock);
        connect();
        ReorderCommand command = new ReorderCommand(player, order,null);
        server.sendCommand(command);
        client.listen();
        waitForClient();
        Assert.assertTrue(reorderMock.testSuccess());
    }
}
