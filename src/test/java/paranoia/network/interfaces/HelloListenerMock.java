package paranoia.network.interfaces;

import org.junit.Assert;
import paranoia.services.technical.command.HelloCommand;

public class HelloListenerMock
    extends ParanoiaNetworkListenerMock
    implements HelloCommand.ParanoiaInfoListener
{

    private final String playerName;
    private final String password;
    private final boolean hasPassword;

    public HelloListenerMock(String playerName, String password, boolean hasPassword) {
        this.playerName = playerName;
        this.password = password;
        this.hasPassword = hasPassword;
    }

    @Override
    public void sayHello(String player, String password, boolean hasPassword) {
        Assert.assertEquals(this.playerName, playerName);
        Assert.assertEquals(this.password, password);
        Assert.assertEquals(this.hasPassword, hasPassword);
        succeed();
    }
}
