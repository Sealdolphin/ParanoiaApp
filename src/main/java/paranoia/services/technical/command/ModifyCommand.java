package paranoia.services.technical.command;

import org.json.JSONObject;

public class ModifyCommand extends ParanoiaCommand {

    public enum Modifiable {
        SKILL,
        STAT,
        MOXIE,
        MAX_MOXIE,
        TREASON_STAR,
        CLONE
    }

    public interface ParanoiaModifyListener {
        void modify(Modifiable attribute, int value, Object details);
    }

    private final ParanoiaModifyListener listener;
    private final Modifiable attribute;
    private final int value;
    private final Object details;

    public ModifyCommand(Modifiable attribute, int value, Object details, ParanoiaModifyListener listener) {
        super(CommandType.MODIFY);
        this.listener = listener;
        this.attribute = attribute;
        this.value = value;
        this.details = details;
    }

    @Override
    public void execute() {
        listener.modify(attribute, value, details);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();
        body.put("attribute", attribute.name());
        body.put("value", value);
        body.put("details", details);
        return wrapCommand(body);
    }
}
