package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicNetworkTest;
import paranoia.services.technical.command.DisconnectCommand;
import paranoia.services.technical.networking.Network;

public class ConnectionTest extends BasicNetworkTest {

    private final Network client = new Network(
        null, null, null,
        null, null, null
    );

    @Test
    public void testConnection() {
        connect(client);
        DisconnectCommand command = new DisconnectCommand(null);
        server.sendCommand(command);
        client.listen();
        waitForClient();
        Assert.assertFalse(client.isOpen());
    }

}
