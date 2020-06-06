package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.DisconnectListenerMock;
import paranoia.services.technical.command.DisconnectCommand;

public class ConnectionTest extends BasicNetworkTest {

    private final DisconnectListenerMock disConnectMock = new DisconnectListenerMock();

    @Test
    public void testConnection() {
        parser.setDisconnectListener(disConnectMock);
        connect();
        DisconnectCommand command = new DisconnectCommand(null);
        server.sendCommand(command);
        client.listen();
        waitForClient();
        Assert.assertTrue(disConnectMock.testSuccess());
    }

}
