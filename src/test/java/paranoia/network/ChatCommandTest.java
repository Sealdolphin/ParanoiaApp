package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.ChatListenerMock;
import paranoia.services.technical.command.ChatCommand;
import paranoia.services.technical.networking.Network;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatCommandTest extends BasicNetworkTest {

    private final String testSender = "TestSender";
    private final String testBody = "Hello World";
    private final String testTime =
        DateTimeFormatter.ofPattern("hh:mm:ss").format(LocalTime.now());
    private final ChatListenerMock chatMock =
        new ChatListenerMock(testSender, testBody, testTime);
    private final Network client = new Network(
        chatMock, null, null,
        null, null, null
    );

    @Test
    public void testChatCommand() {
        connect(client);
        ChatCommand command = new ChatCommand(
            testSender,
            testBody,
            testTime,
            null
        );
        server.sendCommand(command);
        client.listen();
        waitForClient();
        Assert.assertTrue(chatMock.testSuccess());
    }

}
