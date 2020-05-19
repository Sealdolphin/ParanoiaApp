package paranoia.helper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import paranoia.services.technical.Network;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public abstract class BasicNetworkTest {

    protected MockServer server;
    private static int port = 6001;

    @Before
    public void setUp() {
        server = new MockServer(port);
        server.start();
    }

    protected static void connect(Network client) {
        try {
            client.connect(new URL("http", "127.0.0.1", port, ""));
            port += 1;
        } catch (MalformedURLException | UnknownHostException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @After
    public void shutDown() {
        server.close();
    }
}
