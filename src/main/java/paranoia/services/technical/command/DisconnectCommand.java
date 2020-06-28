package paranoia.services.technical.command;

import org.json.JSONObject;

/**
 * This command shuts down the connection of the client socket
 */
public class DisconnectCommand extends ParanoiaCommand {

    public interface ParanoiaDisconnectListener {
        void disconnect();
    }

    private final ParanoiaDisconnectListener listener;

    public DisconnectCommand(ParanoiaDisconnectListener listener) {
        super(CommandType.DISCONNECT);
        this.listener = listener;
    }

    @Override
    public void execute() {
        listener.disconnect();
    }

    @Override
    public JSONObject toJsonObject() {
        return wrapCommand(new JSONObject());
    }
}
