package paranoia.network.interfaces;

import org.junit.Assert;
import paranoia.services.technical.command.ReorderCommand;

public class ReorderListenerMock
    extends ParanoiaNetworkListenerMock
    implements ReorderCommand.ParanoiaReorderListener
{

    private final Integer[] order;
    private final String player;

    public ReorderListenerMock(String player, Integer[] order) {
        this.order = order;
        this.player = player;
    }

    @Override
    public void reorder(String player, Integer[] order) {
        Assert.assertArrayEquals(this.order, order);
        Assert.assertEquals(this.player, player);
        succeed();
    }
}
