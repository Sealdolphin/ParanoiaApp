package paranoia.services.technical.command;

import org.json.JSONObject;

public class HelloCommand extends ParanoiaCommand {

    public interface ParanoiaInfoListener {
        void sayHello(String player, String password, boolean hasPassword);
    }

    private final String playerName;
    private final String password;
    private final boolean hasPassword;
    private final ParanoiaInfoListener listener;

    public HelloCommand(String playerName, String password, boolean hasPassword, ParanoiaInfoListener listener) {
        super(CommandType.HELLO);
        this.playerName = playerName;
        this.password = password;
        this.hasPassword = hasPassword;
        this.listener = listener;
    }

    @Override
    public void execute() {
        if(listener != null)
            listener.sayHello(playerName, password, hasPassword);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();
        body.put("player", playerName);
        body.put("hasPass", hasPassword);
        body.put("password", password);
        return wrapCommand(body);
    }

}
