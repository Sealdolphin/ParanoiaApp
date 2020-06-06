package paranoia.services.technical.command;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReorderCommand extends ParanoiaCommand {

    public interface ParanoiaReorderListener {
        void reorder(String player, Integer[] order);
    }

    private final Integer[] order;
    private final ParanoiaReorderListener listener;
    private final String player;

    public ReorderCommand(String playerName, Integer[] order, ParanoiaReorderListener listener) {
        super(CommandType.REORDER);
        this.order = order;
        this.listener = listener;
        this.player = playerName;
    }

    @Override
    public void execute() {
        listener.reorder(player, order);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();
        body.put("player", player);
        body.put("order", new JSONArray(order));
        return wrapCommand(body);
    }
}
