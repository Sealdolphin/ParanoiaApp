package paranoia.services.technical.command;

import org.json.JSONObject;
import paranoia.Paranoia;

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
public abstract class ParanoiaCommand {

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
        PING
    }

    protected ParanoiaCommand(CommandType type) {
        this.type = type;
    }

    private final CommandType type;

    public abstract void execute();

    public abstract JSONObject toJsonObject();

    protected JSONObject wrapCommand(JSONObject body) {
        JSONObject json = new JSONObject();
        json.put("type", type.name());
        json.put("command", body);
        json.put("version", Paranoia.version);
        return json;
    }
}
