package paranoia.services.technical;


import daiv.networking.command.ParanoiaCommand;
import daiv.networking.command.acpf.response.LobbyResponse;
import daiv.networking.command.general.DisconnectRequest;
import daiv.networking.command.general.Ping;


public class CommandParser {

    private Ping.ParanoiaPingListener pingListener;
    private LobbyResponse.ParanoiaAuthListener authListener;
    private DisconnectRequest.ParanoiaDisconnectListener disconnectListener;

    public void parse(ParanoiaCommand command) {

        switch (command.getType()) {
            case PING:
                Ping.create(command.getTimestamp(), pingListener).execute();
                break;
            case LOBBY_RESP:
                LobbyResponse.create(command, authListener).execute();
                break;
            case DISCONNECT:
                DisconnectRequest.create(command, disconnectListener).execute();
                break;
            default:
                break;
        }
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
}
