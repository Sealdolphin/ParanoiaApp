package paranoia.services.technical;

import org.json.JSONObject;
import paranoia.core.cpu.Skill;
import paranoia.services.technical.command.ACPFCommand;
import paranoia.services.technical.command.ChatCommand;
import paranoia.services.technical.command.DefineCommand;
import paranoia.services.technical.command.DisconnectCommand;
import paranoia.services.technical.command.ParanoiaCommand;
import paranoia.visuals.messages.ParanoiaError;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CommandParser {

    private ChatCommand.ParanoiaChatListener chatListener;
    private DisconnectCommand.ParanoiaDisconnectListener disconnectListener;
    private ACPFCommand.ParanoiaACPFListener acpfListener;
    private DefineCommand.ParanoiaDefineListener defineListener;

    public void parse(String pureMessage) {
        JSONObject message = new JSONObject(pureMessage);
        ParanoiaCommand.CommandType type = ParanoiaCommand.CommandType
                .valueOf(message.getString("type").toUpperCase());

        JSONObject body = message.getJSONObject("command");

        ParanoiaCommand command;
        switch (type) {
            case CHAT:
                command = parseChatCommand(body);
                break;
            case DISCONNECT:
                command = parseDisconnectCommand(body);
                break;
            case ACPF:
                command = parseACPFCommand(body);
                break;
            case DEFINE:
                command = parseDefineCommand(body);
                break;
            default:
                command = null;
                break;
        }
        command.execute();
    }

    public void setChatListener(ChatCommand.ParanoiaChatListener listener) {
        chatListener = listener;
    }
    public void setDisconnectListener(DisconnectCommand.ParanoiaDisconnectListener listener) {
        disconnectListener = listener;
    }
    public void setAcpfListener(ACPFCommand.ParanoiaACPFListener listener) {
        acpfListener = listener;
    }
    public void setDefineListener(DefineCommand.ParanoiaDefineListener listener) {
        defineListener = listener;
    }

    private ParanoiaCommand parseChatCommand(JSONObject body) {
        String sender = body.getString("sender");
        String message = body.getString("body");
        String time = body.getString("timestamp");
        return new ChatCommand(sender, message, time, chatListener);
    }

    private ParanoiaCommand parseDisconnectCommand(JSONObject body) {
        return new DisconnectCommand(disconnectListener);
    }

    private ParanoiaCommand parseACPFCommand(JSONObject body) {
        String name = body.getString("name");
        String gender = body.getString("gender");
        String[] personalities = body.getJSONArray("personality")
            .toList().stream().map(Object::toString)
            .toArray(String[]::new);
        Byte[] imageRaw = body.getJSONArray("profile").toList()
           .stream().map(Object::toString).map(Integer::parseInt).map(Integer::byteValue)
           .toArray(Byte[]::new);
        byte[] trueByteImage = new byte[imageRaw.length];
        for (int i = 0; i < imageRaw.length; i++) {
            trueByteImage[i] = imageRaw[i];
        }

        BufferedImage image = null;
        try {
           image = ImageIO.read(new ByteArrayInputStream(trueByteImage));
        } catch (IOException e) {
            ParanoiaError.error(e);
        }

        return new ACPFCommand(name, gender, personalities, image, acpfListener);
    }

    private ParanoiaCommand parseDefineCommand(JSONObject body) {
        int value = body.getInt("fillValue");
        Skill[] disabled = body.getJSONArray("disabled").toList()
            .stream().map(Object::toString).map(Skill::valueOf)
            .toArray(Skill[]::new);

        return new DefineCommand(value, null, disabled, defineListener);
    }

}
