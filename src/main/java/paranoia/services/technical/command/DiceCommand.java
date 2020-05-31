package paranoia.services.technical.command;

import org.json.JSONObject;
import paranoia.core.cpu.DiceRoll;

public class DiceCommand extends ParanoiaCommand{

    public DiceCommand(DiceRoll result) {
        this(result.getSuccess(), result.isComputer(), null);
    }

    public interface ParanoiaDiceResultListener {
        void getResult(int success, boolean computer);
    }

    private final int success;
    private final boolean computer;
    private final ParanoiaDiceResultListener listener;

    public DiceCommand(int success, boolean computer, ParanoiaDiceResultListener listener) {
        super(CommandType.DICE);
        this.success = success;
        this.computer = computer;
        this.listener = listener;
    }

    @Override
    public void execute() {
        if(listener != null)
            listener.getResult(success, computer);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();
        body.put("success", success);
        body.put("computer", computer);
        return wrapCommand(body);
    }
}
