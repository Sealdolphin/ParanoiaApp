package paranoia.network.interfaces;

import org.junit.Assert;
import paranoia.services.technical.command.DiceCommand;

public class DiceListenerMock
    extends ParanoiaNetworkListenerMock
    implements DiceCommand.ParanoiaDiceResultListener
{
    private final int success;
    private final boolean computer;

    public DiceListenerMock(int success, boolean computer) {
        this.success = success;
        this.computer = computer;
    }

    @Override
    public void getResult(int success, boolean computer) {
        Assert.assertEquals(this.success, success);
        Assert.assertEquals(this.computer,computer);
        succeed();
    }
}
