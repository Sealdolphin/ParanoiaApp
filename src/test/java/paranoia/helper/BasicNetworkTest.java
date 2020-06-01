package paranoia.helper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import paranoia.services.technical.CommandParser;
import paranoia.services.technical.networking.Network;

import java.io.IOException;
import java.net.URL;

public abstract class BasicNetworkTest {

    protected MockServer server;
    protected CommandParser parser = new CommandParser();
    private static int port = 6001;
    private final Object lock = new Object();

    @Before
    public void setUp() {
        server = new MockServer(port, lock);
        server.start();
    }

    protected void connect(Network client) {
        try {
            client.connect(new URL("http", "127.0.0.1", port, ""));
            port += 1;
            synchronized(lock) {
                while (!server.isOpen()) {
                    lock.wait();
                }
            }
        } catch (IOException | InterruptedException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    protected void waitForClient() {
        synchronized (lock) {
            try {
                lock.wait(3000);
            } catch (InterruptedException e) {
                Assert.fail(e.getLocalizedMessage());
            }
        }
    }

    @After
    public void shutDown() {
        server.close();
    }
}
