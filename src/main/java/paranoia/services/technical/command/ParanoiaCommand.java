package paranoia.services.technical.command;

import paranoia.Paranoia;
import paranoia.visuals.messages.ParanoiaMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Abstract Paranoia Command. Every child has it's respective command type and
 * a default listener interface.
 * -----
 * Every client must follow the Paranoia Protocol (@see {@link paranoia.services.technical.networking.Network})
 * Each command has a default structure in JSON format.
 * TYPE: the type of the command
 * COMMAND: the command body (depends on the type)
 * VERSION: the version of the Paranoia High Programmer Interface
 * -----
 */
public abstract class ParanoiaCommand implements Serializable {

    public enum CommandType {
        CHAT,
        DISCONNECT,
        ACPF,
        DEFINE,
        REORDER,
        OPTIMIZE,
        MODIFY,
        ROLL,
        DICE,
        HELLO,
        PING,
        LOBBY
    }

    protected ParanoiaCommand(CommandType type) {
        this.type = type;
    }

    private final CommandType type;

    private String host;

    private final Date timestamp = new Date();

    private final String version = Paranoia.version;

    transient private ByteArrayOutputStream output = new ByteArrayOutputStream();

    public abstract void execute();

    public byte[] toNetworkMessage(String host) {
        this.host = host;

        byte[] message = null;
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(output);
            outStream.writeObject(this);
            outStream.flush();
            message = output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException ignored) { }
        }

        return message;
    }

    public String getHost() {
        return host;
    }

    public CommandType getType() {
        return type;
    }

    public static byte[] parseImage(BufferedImage image) {
        //Parse buffered image
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] imageRaw = new byte[0];
        if(image != null)
            try {
                ImageIO.write(image, "png", outStream);
                imageRaw = outStream.toByteArray();
            } catch (IOException e) {
                ParanoiaMessage.error(e);
            }
        return imageRaw;
    }

    public static ParanoiaCommand parseCommand(byte[] message) throws IOException, ClassNotFoundException {
        ByteArrayInputStream input = new ByteArrayInputStream(message);
        ObjectInputStream inputStream = new ObjectInputStream(input);
        ParanoiaCommand command = (ParanoiaCommand) inputStream.readObject();
        inputStream.close();
        input.close();
        return command;
    }
}
