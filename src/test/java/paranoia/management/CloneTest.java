package paranoia.management;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import paranoia.core.Clone;
import paranoia.helper.ParanoiaListenerMock;
import paranoia.helper.TestClone;
import paranoia.services.hpdmc.manager.TroubleShooterManager;
import paranoia.services.technical.command.ModifyCommand;

import java.util.Random;

public class CloneTest {

    private final TroubleShooterManager manager = new TroubleShooterManager();
    private final ParanoiaListenerMock<Clone> clones = new ParanoiaListenerMock<>();
    private final TestClone testClone = new TestClone(0);

    @Before
    public void setUp() {
        manager.addListener(clones);
        manager.updateAsset(testClone);
    }

    @Test
    public void testProperCloning() {
        int id = new Random().nextInt(5);
        Assert.assertEquals(1, clones.getSize());
        Assert.assertEquals(1, clones.get(0).getClone());
        Assert.assertEquals(testClone, clones.get(0));
        //Do cloning
        manager.modify(ModifyCommand.Modifiable.CLONE, id, null);
        //Assert cloned clone to original clone
        Assert.assertEquals(1, clones.getSize());
        Assert.assertEquals(1 + id, clones.get(0).getClone());
        Assert.assertEquals(testClone.getPlayerId(), clones.get(0).getPlayerId());
        Assert.assertEquals(testClone.getClearance(), clones.get(0).getClearance());
    }

}
