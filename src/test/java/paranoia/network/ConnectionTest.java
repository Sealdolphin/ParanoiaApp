package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicNetworkTest;
import paranoia.services.technical.command.DisconnectCommand;

public class ConnectionTest extends BasicNetworkTest {

    @Test
    public void testConnection() {
        connect();
        DisconnectCommand command = new DisconnectCommand(null);
        server.sendCommand(command);
        client.listen();
        waitForClient();
        Assert.assertFalse(client.isOpen());
    }

}
