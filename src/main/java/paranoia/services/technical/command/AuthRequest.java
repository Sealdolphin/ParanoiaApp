package paranoia.services.technical.command;

public class AuthRequest extends ParanoiaCommand{

    public interface AuthReqListener {
        void requestAuth(boolean hasPassword);
    }

    private transient AuthReqListener listener;

    private final boolean hasPassword;

    public AuthRequest(boolean hasPass) {
        this(hasPass, null);
    }

    private AuthRequest(boolean hasPass, AuthReqListener listener) {
        super(CommandType.REQ_AUTH);
        hasPassword = hasPass;
        this.listener = listener;
    }

    public static AuthRequest create(ParanoiaCommand command, AuthReqListener listener) {
        AuthRequest origin = (AuthRequest) command;
        return new AuthRequest(origin.hasPassword, listener);
    }

    @Override
    public void execute() {
        listener.requestAuth(hasPassword);
    }
}
