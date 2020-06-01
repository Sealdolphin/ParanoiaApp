package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.core.Computer;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.RollListenerMock;
import paranoia.services.technical.command.RollCommand;
import paranoia.services.technical.networking.Network;

import java.util.HashMap;
import java.util.Map;

public class RollCommandTest extends BasicNetworkTest {

    private final Skill skill = Computer.randomItem(Skill.values());
    private final Stat stat = Computer.randomItem(Stat.values());
    private final boolean statEnabled = Computer.coinFlip();
    private final boolean skillEnabled = Computer.coinFlip();
    private final Map<String, Integer> positive = new HashMap<String, Integer>() {{
        put("testPValueA", 4); put("testPValueB", 2); put("testPValueC", 3);
    }};
    private final Map<String, Integer> negative = new HashMap<String, Integer>() {{
        put("testNValueA", 1); put("testNValueB", 5); put("testNValueC", 2);
    }};

    private final RollListenerMock rollMock = new RollListenerMock(
        skill, stat, statEnabled, skillEnabled, positive, negative
    );

    private final Network client = new Network(
        null, null, null,
        null, rollMock, null
    );

    @Test
    public void testRollCommand() {
        connect(client);
        RollCommand command = new RollCommand(
            stat, skill, statEnabled, skillEnabled, positive, negative, null
        );
        server.sendCommand(command);
        client.listen();
        waitForClient();
        Assert.assertTrue(rollMock.testSuccess());
    }

}
