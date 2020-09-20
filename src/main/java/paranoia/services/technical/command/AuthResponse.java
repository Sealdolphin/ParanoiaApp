package paranoia.services.technical.command;

public class AuthResponse extends ParanoiaCommand {

    public interface ParanoiaAuthListener {
        void authenticate(String player, String password);
    }

    transient private ParanoiaAuthListener listener;

    private final String player;
    private final String password;

    public AuthResponse(String player, String password) {
        this(player, password, null);
    }

    private AuthResponse(String player, String password, ParanoiaAuthListener listener) {
        super(CommandType.AUTH);
        this.listener = listener;
        this.player = player;
        this.password = password;
    }

    public String getPlayerName() {
        return player;
    }

    public static AuthResponse create(ParanoiaCommand command, ParanoiaAuthListener listener) {
        AuthResponse origin = (AuthResponse) command;
        return new AuthResponse(origin.player, origin.password, listener);
    }

    @Override
    public void execute() {
        listener.authenticate(player, password);
    }
}
