package paranoia.helper;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import paranoia.services.technical.Network;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

public abstract class BasicNetworkTest {

    protected static MockServer server;

    @BeforeClass
    public static void setUp() {
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

    @AfterClass
    public static void shutDown() {
        server.close();
        try {
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
