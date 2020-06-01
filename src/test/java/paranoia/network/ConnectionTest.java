package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.ACPFListenerMock;
import paranoia.network.interfaces.ChatListenerMock;
import paranoia.services.technical.Network;
import paranoia.services.technical.command.DisconnectCommand;

public class ConnectionTest extends BasicNetworkTest{

    private final Network client = new Network(
        new ChatListenerMock(),
        new ACPFListenerMock(),
        null,
        null,
        null,
        null);

    @Test
    public void testConnection() {
        connect(client);
        DisconnectCommand command = new DisconnectCommand(null);
        server.sendCommand(command);
        client.listen();
        Assert.assertFalse(client.isOpen());
    }

}
