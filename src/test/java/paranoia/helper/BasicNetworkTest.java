package paranoia.helper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import paranoia.services.technical.Network;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

public abstract class BasicNetworkTest {

    protected MockServer server;

    @Before
    public void setUp() {
        server = new MockServer();
        server.start();
    }

    protected static void connect(Network client) {
        try {
            client.connectWithIP("127.0.0.1");
        } catch (MalformedURLException | UnknownHostException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @After
    public void shutDown() {
        server.close();
    }
}
