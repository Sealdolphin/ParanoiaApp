package paranoia.services.technical.command;

import org.json.JSONArray;
import org.json.JSONObject;
import paranoia.core.cpu.Skill;

public class DefineCommand extends ParanoiaCommand {

    public interface ParanoiaDefineListener {
        void alert(int fillValue, Skill[] disabled);
    }

    private final int fillValue;
    private final Skill attribute;
    private final Skill[] disabled;
    private final ParanoiaDefineListener listener;

    public DefineCommand(int fillValue, Skill attribute) {
        this(fillValue, attribute, new Skill[0], null);
    }

    public DefineCommand(int fillValue, Skill attribute, Skill[] disabled, ParanoiaDefineListener listener) {
        super(CommandType.DEFINE);
        this.fillValue = fillValue;
        this.attribute = attribute;
        this.listener = listener;
        this.disabled = disabled;
    }

    @Override
    public void execute() {
        listener.alert(fillValue, disabled);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();
        body.put("fillValue", fillValue);
        if(attribute != null)
            body.put("attribute", attribute.toString());
        body.put("disabled", new JSONArray(disabled));

        return wrapCommand(body);
    }
}
