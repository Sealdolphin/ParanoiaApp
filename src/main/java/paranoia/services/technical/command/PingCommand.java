package paranoia.services.technical.command;

/**
 * Basic implementation of ParanoiaCommand
 */
public class PingCommand extends ParanoiaCommand {

    public interface ParanoiaPingListener {
        void pong();
    }

    private final ParanoiaPingListener listener;

    public PingCommand() {
        this(null);
    }

    public PingCommand(ParanoiaPingListener listener) {
        super(CommandType.PING);
        this.listener = listener;
    }

    @Override
    public void execute() {
        listener.pong();
    }
}
