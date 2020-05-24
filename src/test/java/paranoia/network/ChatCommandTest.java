package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.ACPFListenerMock;
import paranoia.network.interfaces.ChatListenerMock;
import paranoia.network.interfaces.DisconnectListenerMock;
import paranoia.services.technical.Network;
import paranoia.services.technical.command.ChatCommand;

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
        chatMock,
        new DisconnectListenerMock(),
        new ACPFListenerMock(),
        null
    );

    @Test
    public void sendChatCommand() {
        connect(client);
        ChatCommand command = new ChatCommand(
            testSender,
            testBody,
            testTime,
            null
        );
        server.sendCommand(command);
        client.listen();
        Assert.assertTrue(chatMock.testSuccess());
    }

}
