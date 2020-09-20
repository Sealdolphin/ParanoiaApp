package paranoia.services.technical;

import daiv.networking.command.AuthRequest;
import daiv.networking.command.AuthResponse;
import daiv.networking.command.ParanoiaCommand;
import daiv.networking.command.PingCommand;

public class CommandParser {

    private PingCommand.ParanoiaPingListener pingListener;
    private AuthResponse.ParanoiaAuthListener authListener;
    private AuthRequest.AuthReqListener authReqListener;

    public void parse(ParanoiaCommand command) {

        switch (command.getType()) {
            case PING:
                PingCommand.create(pingListener).execute();
                break;
            case AUTH:
                AuthResponse.create(command, authListener).execute();
                break;
            case REQ_AUTH:
                AuthRequest.create(command, authReqListener).execute();
                break;
            default:
                break;
        }
    }

    public void setAuthReqListener(AuthRequest.AuthReqListener listener) {
        this.authReqListener = listener;
    }
    public void setPingListener(PingCommand.ParanoiaPingListener listener) {
        this.pingListener = listener;
    }
    public void setAuthListener(AuthResponse.ParanoiaAuthListener listener) {
        this.authListener = listener;
    }

}
