package paranoia.network;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.ChatListenerMock;
import paranoia.network.interfaces.DisconnectListenerMock;
import paranoia.services.technical.Network;
import paranoia.services.technical.command.DisconnectCommand;

public class ConnectionTest extends BasicNetworkTest{

    private final DisconnectListenerMock disconnectMock =
        new DisconnectListenerMock();
    private final Network client = new Network(
        new ChatListenerMock(),
        disconnectMock
    );

    @Test
    @Ignore
    public void testConnection() {
        connect(client);
        DisconnectCommand command = new DisconnectCommand(null);
        server.sendCommand(command);
        client.listen();
        Assert.assertTrue(disconnectMock.testSuccess());
    }

}
