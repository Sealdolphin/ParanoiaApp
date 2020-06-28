package paranoia.services.technical.command;

import org.json.JSONObject;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This command conveys a chat message between the server and the client.
 * The arguments are the same on both sides.
 * -----
 * SENDER: the name of the sender
 * BODY: the content of the chat message
 * TIMESTAMP: the time when the message was sent
 * -----
 */
public class ChatCommand extends ParanoiaCommand {

    public interface ParanoiaChatListener {
        void digest(String sender, String message, String timestamp);
    }

    private final String sender;
    private final String body;
    private final String timestamp;
    private final ParanoiaChatListener listener;

    public ChatCommand(String sender, String body, String time, ParanoiaChatListener listener) {
        super(CommandType.CHAT);
        this.sender = sender;
        this.body = body;
        this.timestamp = time;
        this.listener = listener;
    }

    public static String generateCommandJson(String sender, String message) {
        ChatCommand command = new ChatCommand(
            sender,
            message,
            DateTimeFormatter.ofPattern("hh:mm:ss").format(LocalTime.now()),
            null
        );
        return command.toJsonObject().toString();
    }

    @Override
    public void execute() {
        if(listener != null)
            listener.digest(sender, body, timestamp);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("sender", sender);
        json.put("body", body);
        json.put("timestamp", timestamp);
        return wrapCommand(json);
    }
}
