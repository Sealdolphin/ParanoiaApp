package paranoia.network;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import paranoia.helper.MockServer;
import paranoia.network.interfaces.ChatListenerMock;
import paranoia.network.interfaces.DisconnectListenerMock;
import paranoia.services.technical.Network;
import paranoia.services.technical.command.ChatCommand;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatCommandTest {
    private static MockServer server;
    private static Network client;
    private static final String testSender = "TestSender";
    private static final String testBody = "Hello World";
    private static final String testTime =
        DateTimeFormatter.ofPattern("hh:mm:ss").format(LocalTime.now());
    private static final ChatListenerMock chatMock =
        new ChatListenerMock(testSender, testBody, testTime);

    @BeforeClass
    public static void setup() {
        client = new Network(
            chatMock,
            new DisconnectListenerMock()
        );
        server = new MockServer();
        server.start();
        try {
            client.connectWithIP("127.0.0.1");
        } catch (MalformedURLException | UnknownHostException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void sendChatCommand() {
        ChatCommand command = new ChatCommand(
            testSender,
            testBody,
            testTime,
            null
        );
        server.sendCommand(command);
        client.listen();
    }

    @AfterClass
    public static void shutDown() {
        server.close();
        Assert.assertTrue(chatMock.testSuccess());
    }

}
