package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.core.Computer;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.HelloListenerMock;
import paranoia.services.technical.command.HelloCommand;

public class HelloCommandTest extends BasicNetworkTest {

    private final String playerName = "testPlayer";
    private final String password = "testPassword";
    private final boolean hasPassword = Computer.coinFlip();

    private final HelloListenerMock helloMock = new HelloListenerMock(
        playerName, password, hasPassword
    );

    @Test
    public void testHelloCommand() {
        parser.setInfoListener(helloMock);
        connect();
        HelloCommand command = new HelloCommand(
            playerName, password, hasPassword, null
        );
        server.sendCommand(command);
        client.listen();
        waitForClient();
        Assert.assertTrue(helloMock.testSuccess());
    }
}
