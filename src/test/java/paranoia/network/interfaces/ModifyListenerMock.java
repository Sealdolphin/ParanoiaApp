package paranoia.network.interfaces;

import org.junit.Assert;
import paranoia.services.technical.command.ModifyCommand;

public class ModifyListenerMock
    extends ParanoiaNetworkListenerMock
    implements ModifyCommand.ParanoiaModifyListener
{

    private final ModifyCommand.Modifiable attribute;
    private final int value;
    private final Object details;

    public ModifyListenerMock(
        ModifyCommand.Modifiable attribute,
        int value,
        Object details
    ) {
        this.attribute = attribute;
        this.value = value;
        this.details = details;
    }

    @Override
    public void modify(ModifyCommand.Modifiable attribute, int value, Object details) {
        Assert.assertEquals(this.attribute, attribute);
        Assert.assertEquals(this.value, value);
        Assert.assertEquals(this.details, details);
        succeed();
    }
}
