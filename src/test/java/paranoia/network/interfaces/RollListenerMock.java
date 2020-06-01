package paranoia.network.interfaces;

import org.junit.Assert;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.technical.command.RollCommand;

import java.util.Map;

public class RollListenerMock implements
    ParanoiaNetworkListenerMock,
    RollCommand.ParanoiaRollListener
{

    private final Skill skill;
    private final Stat stat;
    private final boolean statEnabled;
    private final boolean skillEnabled;
    private final Map<String, Integer> positive;
    private final Map<String, Integer> negative;

    private boolean success = false;

    public RollListenerMock(
        Skill skill, Stat stat, boolean statEnabled, boolean skillEnabled,
        Map<String, Integer> positive, Map<String, Integer> negative
    ) {
        this.skill = skill;
        this.stat = stat;
        this.statEnabled = statEnabled;
        this.skillEnabled = skillEnabled;
        this.positive = positive;
        this.negative = negative;
    }

    @Override
    public boolean testSuccess() {
        return success;
    }

    @Override
    public void fireRollMessage(
        Stat stat, Skill skill, boolean statChange, boolean skillChange,
        Map<String, Integer> positive, Map<String, Integer> negative
    ) {
        Assert.assertEquals(this.skill, skill);
        Assert.assertEquals(this.stat, stat);
        Assert.assertEquals(this.skillEnabled, skillChange);
        Assert.assertEquals(this.statEnabled, statChange);
        Assert.assertArrayEquals(
            this.positive.entrySet().toArray(),
            positive.entrySet().toArray()
        );
        Assert.assertArrayEquals(
            this.negative.entrySet().toArray(),
            negative.entrySet().toArray()
        );
        success = true;
    }
}
