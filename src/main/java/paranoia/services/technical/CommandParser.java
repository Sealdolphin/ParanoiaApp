package paranoia.services.technical;


import daiv.networking.command.ParanoiaCommand;
import daiv.networking.command.general.AuthRequest;
import daiv.networking.command.general.DisconnectCommand;
import daiv.networking.command.general.PingCommand;


public class CommandParser {

    private PingCommand.ParanoiaPingListener pingListener;
    private AuthRequest.ParanoiaAuthListener authListener;
    private DisconnectCommand.ParanoiaDisconnectListener disconnectListener;

    public void parse(ParanoiaCommand command) {

        switch (command.getType()) {
            case PING:
                PingCommand.create(pingListener).execute();
                break;
            case REQ_AUTH:
                AuthRequest.create(command, authListener).execute();
                break;
            case DISCONNECT:
                DisconnectCommand.create(command, disconnectListener).execute();
                break;
            default:
                break;
        }
    }

    public void setPingListener(PingCommand.ParanoiaPingListener listener) {
        this.pingListener = listener;
    }
    public void setAuthListener(AuthRequest.ParanoiaAuthListener listener) {
        this.authListener = listener;
    }
    public void setDisconnectListener(DisconnectCommand.ParanoiaDisconnectListener disconnectListener) {
        this.disconnectListener = disconnectListener;
    }
}
