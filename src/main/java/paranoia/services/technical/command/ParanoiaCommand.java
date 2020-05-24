package paranoia.services.technical.command;

import org.json.JSONObject;

public abstract class ParanoiaCommand {

    public enum CommandType {
        CHAT,
        DISCONNECT,
        ACPF
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
        System.out.println(json.toString());
        return json;
    }
}
