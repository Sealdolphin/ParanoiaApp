package paranoia.services.technical.command;

import org.json.JSONObject;
import paranoia.core.cpu.Skill;

public class DefineCommand extends ParanoiaCommand {

    public interface ParanoiaDefineListener {
        void alert(int fillValue);
    }

    private final int fillValue;
    private final Skill attribute;
    private final ParanoiaDefineListener listener;

    public DefineCommand(int fillValue, Skill attribute) {
        this(fillValue, attribute, null);
    }

    public DefineCommand(int fillValue, Skill attribute, ParanoiaDefineListener listener) {
        super(CommandType.DEFINE);
        this.fillValue = fillValue;
        this.attribute = attribute;
        this.listener = listener;
    }

    @Override
    public void execute() {
        listener.alert(fillValue);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();
        body.put("fillValue", fillValue);
        if(attribute != null)
            body.put("attribute", attribute.toString());
        return wrapCommand(body);
    }
}
