package paranoia.services.technical.command;

import org.json.JSONArray;
import org.json.JSONObject;
import paranoia.core.cpu.Skill;

/**
 * This command signals when a Paranoia Attribute have to be
 * modified in the Alpha Complex Personality Form.
 * The arguments are different on server side and client side.
 * -----
 * FILLVALUE: the value that has to be assigned
 * LASTCHOICE: true if this is the last round
 * ATTRIBUTE(server): the attribute that was modified
 * DISABLED: all the attributes that cannot be modified
 * -----
 * SERVER SIDE: sends the FILLVALUE and the ROUND and the DISABLED
 * CLIENT SIDE: sends the ATTRIBUTE and the FILLVALUE
 */
public class DefineCommand extends ParanoiaCommand {

    public interface ParanoiaDefineListener {
        void alert(int fillValue, Skill[] disabled, boolean lastChoice);
    }

    private final int fillValue;
    private final boolean lastChoice;
    private final Skill attribute;
    private final Skill[] disabled;
    private final ParanoiaDefineListener listener;

    public DefineCommand(int fillValue, Skill attribute) {
        this(fillValue, false, attribute, new Skill[0], null);
    }

    public DefineCommand(int fillValue, boolean last, Skill attribute, Skill[] disabled, ParanoiaDefineListener listener) {
        super(CommandType.DEFINE);
        this.fillValue = fillValue;
        this.attribute = attribute;
        this.listener = listener;
        this.disabled = disabled;
        lastChoice = last;
    }

    @Override
    public void execute() {
        listener.alert(fillValue, disabled, lastChoice);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();
        body.put("fillValue", fillValue);
        if(attribute != null)
            body.put("attribute", attribute.toString());
        body.put("disabled", new JSONArray(disabled));
        body.put("last", lastChoice);
        return wrapCommand(body);
    }
}
