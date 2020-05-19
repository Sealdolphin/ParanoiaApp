package paranoia.services.technical;

import org.json.JSONObject;
import paranoia.services.technical.command.ChatCommand;
import paranoia.services.technical.command.DisconnectCommand;
import paranoia.services.technical.command.ParanoiaCommand;

public class CommandParser {

    private ChatCommand.ParanoiaChatListener chatListener;
    private DisconnectCommand.ParanoiaDisconnectListener disconnectListener;

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

    private ParanoiaCommand parseChatCommand(JSONObject body) {
        String sender = body.getString("sender");
        String message = body.getString("body");
        String time = body.getString("timestamp");
        return new ChatCommand(sender, message, time, chatListener);
    }

    private ParanoiaCommand parseDisconnectCommand(JSONObject body) {
        return new DisconnectCommand(disconnectListener);
    }

}
