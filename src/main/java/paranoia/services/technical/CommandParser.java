package paranoia.services.technical;

import org.json.JSONArray;
import org.json.JSONObject;
import paranoia.core.Player;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.technical.command.ACPFCommand;
import paranoia.services.technical.command.ChatCommand;
import paranoia.services.technical.command.DefineCommand;
import paranoia.services.technical.command.DiceCommand;
import paranoia.services.technical.command.DisconnectCommand;
import paranoia.services.technical.command.HelloCommand;
import paranoia.services.technical.command.LobbyCommand;
import paranoia.services.technical.command.ModifyCommand;
import paranoia.services.technical.command.ParanoiaCommand;
import paranoia.services.technical.command.PingCommand;
import paranoia.services.technical.command.ReorderCommand;
import paranoia.services.technical.command.RollCommand;
import paranoia.visuals.messages.ParanoiaMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {

    private ChatCommand.ParanoiaChatListener chatListener;
    private DisconnectCommand.ParanoiaDisconnectListener disconnectListener;
    private ACPFCommand.ParanoiaACPFListener acpfListener;
    private DefineCommand.ParanoiaDefineListener defineListener;
    private ReorderCommand.ParanoiaReorderListener reorderListener;
    private ModifyCommand.ParanoiaModifyListener modifyListener;
    private RollCommand.ParanoiaRollListener rollListener;
    private DiceCommand.ParanoiaDiceResultListener diceListener;
    private HelloCommand.ParanoiaInfoListener infoListener;
    private PingCommand.ParanoiaPingListener pingListener;
    private LobbyCommand.LobbyListener lobbyListener;

    public void parse(String pureMessage) {
        JSONObject message = new JSONObject(pureMessage);
        ParanoiaCommand.CommandType type = ParanoiaCommand.CommandType
                .valueOf(message.getString("type").toUpperCase());
        JSONObject body = message.getJSONObject("command");

        ParanoiaCommand command;
        switch (type) {
            case CHAT:
                command = parseChatCommand(body);
                break;
            case DISCONNECT:
                command = parseDisconnectCommand();
                break;
            case ACPF:
                command = parseACPFCommand(body);
                break;
            case DEFINE:
                command = parseDefineCommand(body);
                break;
            case REORDER:
                command = parseReorderCommand(body);
                break;
            case MODIFY:
                command = parseModifiyCommand(body);
                break;
            case ROLL:
                command = parseRollCommand(body);
                break;
            case DICE:
                command = parseDiceCommand(body);
                break;
            case HELLO:
                command = parseHelloCommand(body);
                break;
            case PING:
                command = parsePingCommand();
                break;
            case LOBBY:
                command = parseLobbyCommand(body);
                break;
            default:
                command = null;
                break;
        }
        if(command != null)
            command.execute();
    }

    private ParanoiaCommand parsePingCommand() {
        return new PingCommand(pingListener);
    }

    private ParanoiaCommand parseHelloCommand(JSONObject body) {
        String name = body.optString("player", null);
        String password = body.optString("password", null);
        boolean hasPassword = body.getBoolean("hasPass");
        return new HelloCommand(name, password, hasPassword, infoListener);
    }

    private ParanoiaCommand parseDiceCommand(JSONObject body) {
        int success = body.getInt("success");
        boolean computer = body.getBoolean("computer");
        return new DiceCommand(success, computer, diceListener);
    }

    private ParanoiaCommand parseRollCommand(JSONObject body) {
        Map<String, Integer> positive = new HashMap<>();
        Map<String, Integer> negative = new HashMap<>();

        Stat stat = Stat.valueOf(body.getString("stat").toUpperCase());
        Skill skill = Skill.valueOf(body.getString("skill").toUpperCase());
        boolean statChange = body.getBoolean("stat_enabled");
        boolean skillChange = body.getBoolean("skill_enabled");

        JSONArray positiveObj = body.getJSONArray("positive");
        JSONArray negativeObj = body.getJSONArray("negative");

        positiveObj.forEach( o -> {
            JSONObject entry = (JSONObject) o;
            String key = entry.getString("message");
            int value = entry.getInt("value");
            positive.put(key, value);
        });

        negativeObj.forEach( o -> {
            JSONObject entry = (JSONObject) o;
            String key = entry.getString("message");
            int value = entry.getInt("value");
            negative.put(key, value);
        });

        return new RollCommand(
            stat, skill, statChange, skillChange,
            positive, negative, rollListener
        );
    }

    private ParanoiaCommand parseModifiyCommand(JSONObject body) {
        ModifyCommand.Modifiable attribute = ModifyCommand.Modifiable
            .valueOf(body.getString("attribute").toUpperCase());
        int value = body.getInt("value");
        Object details = body.get("details");

        return new ModifyCommand(attribute, value, details, modifyListener);
    }

    private ParanoiaCommand parseReorderCommand(JSONObject body) {
        String playerName = body.getString("player");
        Integer[] order = body.getJSONArray("order")
            .toList().stream().map(item -> Integer.parseInt(item.toString()))
            .toArray(Integer[]::new);
        return new ReorderCommand(playerName, order, reorderListener);
    }

    private ParanoiaCommand parseChatCommand(JSONObject body) {
        String sender = body.getString("sender");
        String message = body.getString("body");
        String time = body.getString("timestamp");
        return new ChatCommand(sender, message, time, chatListener);
    }

    private ParanoiaCommand parseDisconnectCommand() {
        return new DisconnectCommand(disconnectListener);
    }

    private ParanoiaCommand parseACPFCommand(JSONObject body) {
        String name = body.getString("name");
        String gender = body.getString("gender");
        String[] personalities = body.getJSONArray("personality")
            .toList().stream().map(Object::toString)
            .toArray(String[]::new);
        Byte[] imageRaw = body.getJSONArray("profile").toList()
           .stream().map(Object::toString).map(Integer::parseInt).map(Integer::byteValue)
           .toArray(Byte[]::new);
        byte[] trueByteImage = new byte[imageRaw.length];
        for (int i = 0; i < imageRaw.length; i++) {
            trueByteImage[i] = imageRaw[i];
        }

        BufferedImage image = null;
        try {
           image = ImageIO.read(new ByteArrayInputStream(trueByteImage));
        } catch (IOException e) {
            ParanoiaMessage.error(e);
        }

        return new ACPFCommand(name, gender, personalities, image, acpfListener);
    }

    private ParanoiaCommand parseDefineCommand(JSONObject body) {
        int value = body.getInt("fillValue");
        Skill[] disabled = body.getJSONArray("disabled").toList()
            .stream().map(Object::toString).map(Skill::valueOf)
            .toArray(Skill[]::new);
        boolean last = body.getBoolean("last");

        return new DefineCommand(value, last, null, disabled, defineListener);
    }

    private ParanoiaCommand parseLobbyCommand(JSONObject body) {
        LobbyCommand.LobbyEvent event = LobbyCommand.LobbyEvent.valueOf(body.getString("event"));
        JSONObject playerObj = body.getJSONObject("player");
        String name = playerObj.getString("name");
        //get image
        //get last picked attribute
        Player player = new Player(name, null);
        return new LobbyCommand(player, event, lobbyListener);
    }

    public void setChatListener(ChatCommand.ParanoiaChatListener listener) {
        chatListener = listener;
    }
    public void setDisconnectListener(DisconnectCommand.ParanoiaDisconnectListener listener) {
        disconnectListener = listener;
    }
    public void setAcpfListener(ACPFCommand.ParanoiaACPFListener listener) {
        acpfListener = listener;
    }
    public void setDefineListener(DefineCommand.ParanoiaDefineListener listener) {
        defineListener = listener;
    }
    public void setReorderListener(ReorderCommand.ParanoiaReorderListener listener) {
        reorderListener = listener;
    }
    public void setRollListener(RollCommand.ParanoiaRollListener listener) {
        this.rollListener = listener;
    }
    public void setDiceListener(DiceCommand.ParanoiaDiceResultListener listener) {
        this.diceListener = listener;
    }
    public void setInfoListener(HelloCommand.ParanoiaInfoListener listener) {
        this.infoListener = listener;
    }
    public void setPingListener(PingCommand.ParanoiaPingListener listener) {
        this.pingListener = listener;
    }
    public void setLobbyListener(LobbyCommand.LobbyListener listener) {
        this.lobbyListener = listener;
    }

}
