package paranoia.network.interfaces;

import org.junit.Assert;
import paranoia.services.technical.command.ACPFCommand;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ACPFListenerMock implements
    ACPFCommand.ParanoiaACPFListener,
    ParanoiaNetworkListenerMock
{
    private boolean success = false;
    private final String name;
    private final String gender;
    private final String[] personality;
    private final BufferedImage image;

    public ACPFListenerMock(String name, String gender, String[] personality, BufferedImage image) {
        this.name = name;
        this.gender = gender;
        this.personality = personality;
        this.image = image;
    }

    @Override
    public boolean testSuccess() {
        return success;
    }

    @Override
    public void updateProfile(String name, String gender, String[] personality, BufferedImage image) {
        Assert.assertEquals(this.name, name);
        Assert.assertEquals(this.gender, gender);
        Assert.assertArrayEquals(this.personality, personality);

        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        ByteArrayOutputStream actual = new ByteArrayOutputStream();

        try {
            ImageIO.write(this.image, "png", expected);
            ImageIO.write(image, "png", actual);
            Assert.assertArrayEquals(
                expected.toByteArray(),
                actual.toByteArray()
            );
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }

        success = true;
    }
}
