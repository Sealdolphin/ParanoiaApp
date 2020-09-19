package paranoia.services.technical.command;

/**
 * Basic implementation of ParanoiaCommand
 */
public class PingCommand extends ParanoiaCommand {

    public interface ParanoiaPingListener {
        void pong();
    }

    transient private final ParanoiaPingListener listener;

    public PingCommand() {
        this(null);
    }

    private PingCommand(ParanoiaPingListener listener) {
        super(CommandType.PING);
        this.listener = listener;
    }

    public static PingCommand copy(ParanoiaPingListener listener) {
        return new PingCommand(listener);
    }

    @Override
    public void execute() {
        listener.pong();
    }
}
