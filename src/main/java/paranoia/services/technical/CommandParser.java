package paranoia.services.technical;


import daiv.networking.command.ParanoiaCommand;
import daiv.networking.command.acpf.response.LobbyResponse;
import daiv.networking.command.acpf.response.SkillResponse;
import daiv.networking.command.general.DisconnectRequest;
import daiv.networking.command.general.Ping;
import daiv.networking.command.general.PlayerBroadcast;

import java.util.ArrayList;
import java.util.List;


public class CommandParser {

    private String uuid = null;
    private Ping.ParanoiaPingListener pingListener;
    private LobbyResponse.ParanoiaAuthListener authListener;
    private DisconnectRequest.ParanoiaDisconnectListener disconnectListener;
    private PlayerBroadcast.PlayerConnectListener playerListener;
    private final List<SkillResponse.ParanoiaSkillListener> skillListeners = new ArrayList<>();

    public void parse(ParanoiaCommand command) {
        if(uuid == null) { uuid = command.getUUID(); }

        switch (command.getType()) {
            case PING:
                Ping.create(command, pingListener).execute();
                break;
            case LOBBY_RESP:
                LobbyResponse.create(command, authListener).execute();
                break;
            case DISCONNECT:
                DisconnectRequest.create(command, disconnectListener).execute();
                break;
            case PLAYER:
                PlayerBroadcast.create(command, uuid, playerListener).execute();
                break;
            case SKILL_RESP:
                for (SkillResponse.ParanoiaSkillListener skillListener : skillListeners)
                    SkillResponse.create(command, skillListener).execute();
                break;
            default:
                break;
        }
    }

    public String getUUID() {
        return uuid;
    }

    public void setPlayerListener(PlayerBroadcast.PlayerConnectListener listener) {
        this.playerListener = listener;
    }
    public void setPingListener(Ping.ParanoiaPingListener listener) {
        this.pingListener = listener;
    }
    public void setAuthListener(LobbyResponse.ParanoiaAuthListener listener) {
        this.authListener = listener;
    }
    public void setDisconnectListener(DisconnectRequest.ParanoiaDisconnectListener disconnectListener) {
        this.disconnectListener = disconnectListener;
    }
    public void addSkillListener(SkillResponse.ParanoiaSkillListener listener) {
        skillListeners.add(listener);
    }
}
