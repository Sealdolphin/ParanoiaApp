package paranoia.network;

import org.junit.Assert;
import org.junit.Test;
import paranoia.Paranoia;
import paranoia.helper.BasicNetworkTest;
import paranoia.network.interfaces.ACPFListenerMock;
import paranoia.services.technical.command.ACPFCommand;
import paranoia.services.technical.networking.Network;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ACPFCommandTest extends BasicNetworkTest {

    private final String[] personalities = new String[]
        { "testPersonality01", "testPersonality02" };
    private BufferedImage testImage;

    @Test
    public void testACPFCommand() {
        try {
            testImage = ImageIO.read(
                new File(Paranoia.getParanoiaResource("clones/clone0.png"))
            );
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }
        String name = "testName";
        String gender = "testGender";
        ACPFListenerMock acpfMock = new ACPFListenerMock(
            name, gender, personalities, testImage
        );
        Network client = new Network(
            null, acpfMock, null,
            null, null, null
        );
        connect(client);
        ACPFCommand command = new ACPFCommand(
            name, gender, personalities, testImage, null
        );
        server.sendCommand(command);
        client.listen();
        waitForClient();
        Assert.assertTrue(acpfMock.testSuccess());
    }

}
