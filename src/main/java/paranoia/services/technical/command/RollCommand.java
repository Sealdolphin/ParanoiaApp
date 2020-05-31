package paranoia.services.technical.command;

import org.json.JSONArray;
import org.json.JSONObject;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RollCommand extends ParanoiaCommand{

    public interface ParanoiaRollListener {
        void fireRollMessage(
            Stat stat, Skill skill,
            boolean statChange, boolean skillChange,
            Map<String, Integer> positive,
            Map<String, Integer> negative
        );
    }

    private final Map<String, Integer> positive;
    private final Map<String, Integer> negative;
    private final Skill defaulSkill;
    private final Stat defaulStat;
    private final boolean skillChange;
    private final boolean statChange;
    private final ParanoiaRollListener listener;

    public RollCommand(
        Stat defaulStat,
        Skill defaulSkill,
        boolean statChange,
        boolean skillChange,
        Map<String, Integer> positive,
        Map<String, Integer> negative,
        ParanoiaRollListener listener
    ) {
        super(CommandType.ROLL);
        this.listener = listener;
        this.positive = positive;
        this.negative = negative;
        this.skillChange = skillChange;
        this.statChange = statChange;
        this.defaulSkill = defaulSkill;
        this.defaulStat = defaulStat;
    }

    @Override
    public void execute() {
        if(listener != null)
        listener.fireRollMessage(
            defaulStat, defaulSkill,
            statChange, skillChange,
            positive, negative
        );
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();
        body.put("stat", defaulStat);
        body.put("skill", defaulSkill);
        body.put("stat_enabled", statChange);
        body.put("skill_enabled", skillChange);

        List<JSONObject> positiveObj = new ArrayList<>();
        List<JSONObject> negativeObj = new ArrayList<>();

        positive.forEach((k, v) -> {
            JSONObject entry = new JSONObject();
            entry.put("message", k);
            entry.put("value", v);
            positiveObj.add(entry);
        });

        negative.forEach((k, v) -> {
            JSONObject entry = new JSONObject();
            entry.put("message", k);
            entry.put("value", v);
            negativeObj.add(entry);
        });

        body.put("positive", new JSONArray(positiveObj));
        body.put("negative", new JSONArray(negativeObj));
        return wrapCommand(body);
    }
}
