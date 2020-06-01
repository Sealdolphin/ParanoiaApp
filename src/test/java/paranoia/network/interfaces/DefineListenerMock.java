package paranoia.network.interfaces;

import org.junit.Assert;
import paranoia.core.cpu.Skill;
import paranoia.services.technical.command.DefineCommand;

public class DefineListenerMock
    extends ParanoiaNetworkListenerMock
    implements DefineCommand.ParanoiaDefineListener
{
    private final int fillValue;
    private final boolean lastChoice;
    private final Skill attribute;
    private final Skill[] disabled;

    public DefineListenerMock(int fillValue, boolean lastChoice, Skill attribute, Skill[] disabled) {
        this.fillValue = fillValue;
        this.lastChoice = lastChoice;
        this.attribute = attribute;
        this.disabled = disabled;
    }

    @Override
    public void alert(int fillValue, Skill[] disabled, boolean lastChoice) {
        Assert.assertEquals(this.fillValue, fillValue);
        Assert.assertEquals(this.lastChoice, lastChoice);
        Assert.assertEquals(this.attribute, attribute);
        Assert.assertArrayEquals(this.disabled, disabled);
        succeed();
    }
}
