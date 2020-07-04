package paranoia.services.technical.command;

import org.json.JSONObject;
import paranoia.core.Player;

public class LobbyCommand extends ParanoiaCommand {

    public interface LobbyListener {
        void playerConnects(Player newPlayer);

        void playerDisconnects(Player oldPlayer);
    }

    public enum LobbyEvent {
        CONNECT,
        DISCONNECT
    }

    public LobbyCommand(Player playerInfo, LobbyEvent eventType, LobbyListener listener) {
        super(CommandType.LOBBY);
        this.playerInfo = playerInfo;
        this.eventType = eventType;
        this.listener = listener;
    }

    private final Player playerInfo;
    private final LobbyEvent eventType;
    private final LobbyListener listener;


    @Override
    public void execute() {
        if(eventType == LobbyEvent.CONNECT)
            listener.playerConnects(playerInfo);
        else if(eventType == LobbyEvent.DISCONNECT)
            listener.playerDisconnects(playerInfo);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();

        body.put("event", eventType);
        body.put("player", playerInfo.toJsonObject());

        return wrapCommand(body);
    }
}
