package paranoia.network.interfaces;

import org.junit.Assert;
import paranoia.services.technical.command.ChatCommand;

public class ChatListenerMock implements
    ChatCommand.ParanoiaChatListener,
    ParanoiaNetworkListenerMock
{

    private final String testSender;
    private final String testBody;
    private final String testTime;
    private boolean success = false;

    public ChatListenerMock(String testSender, String testBody, String testTime) {
        this.testSender = testSender;
        this.testBody = testBody;
        this.testTime = testTime;
    }


    @Override
    public void digest(String sender, String message, String timestamp) {
        Assert.assertEquals(sender, testSender);
        Assert.assertEquals(message, testBody);
        Assert.assertEquals(timestamp, testTime);
        success = true;
    }

    public boolean testSuccess() {
        return success;
    }
}
