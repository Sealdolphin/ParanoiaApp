package paranoia.services.hpdmc;

import daiv.networking.command.ParanoiaCommand;
import daiv.networking.command.acpf.request.LobbyRequest;
import daiv.networking.command.acpf.response.LobbyResponse;
import daiv.ui.custom.ParanoiaButtonListener;
import daiv.ui.custom.ParanoiaMessage;
import paranoia.core.Clone;
import paranoia.core.ICoreTechPart;
import paranoia.core.ParanoiaPlayer;
import paranoia.core.cpu.Mission;
import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.manager.AttributeManager;
import paranoia.services.hpdmc.manager.CardManager;
import paranoia.services.hpdmc.manager.MissionManager;
import paranoia.services.hpdmc.manager.ParanoiaManager;
import paranoia.services.hpdmc.manager.PlayerManager;
import paranoia.services.hpdmc.manager.TroubleShooterManager;
import paranoia.services.rnd.ParanoiaCard;
import paranoia.services.technical.ParanoiaManagerMap;
import paranoia.services.technical.networking.Network;
import paranoia.visuals.CerebralCoretech;
import paranoia.visuals.ComponentName;
import paranoia.visuals.LobbyFrame;
import paranoia.visuals.MenuFrame;
import paranoia.visuals.messages.RollMessage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.io.IOException;
import java.util.Map;

/**
 * Controls the core game elements - GameMaster interface
 */
public class ControlUnit implements
    ParanoiaController, ParanoiaButtonListener, LobbyResponse.ParanoiaAuthListener {

    private CerebralCoretech visuals;
    private final MenuFrame mainFrame;
    private final ParanoiaManagerMap managerMap = new ParanoiaManagerMap();
    private final Network network;
    private String connectUrl = "http://127.0.0.1:6532";
    private ParanoiaPlayer player;

    public ControlUnit(Network network) {
        mainFrame = new MenuFrame(this);
        mainFrame.updateSettings(connectUrl);
        //Setup managers
        AttributeManager attrManager = new AttributeManager();
        managerMap.put(Mission.class, new MissionManager());
        managerMap.put(ParanoiaCard.class, new CardManager());
        managerMap.put(ParanoiaAttribute.class, attrManager);
        managerMap.put(ParanoiaPlayer.class, new PlayerManager());
        managerMap.put(Clone.class, new TroubleShooterManager());
        //Setup miscellaneous
        //TODO: need some update over time
//        ChatPanel chatPanel = new ChatPanel(clone, this);
//        operationPanel.activatePanel(chatPanel, ComponentName.CHAT_PANEL.name());
        //Setup network
        this.network = network;
        network.getParser().setAuthListener(this);
        network.getParser().addSkillListener(attrManager);
//        network.getParser().setChatListener(chatPanel);
        //Setup visuals
//        visuals = new CerebralCoretech(this, clone);
        mainFrame.setVisible(true);
    }

    @Deprecated
    public ParanoiaManager getManager(ComponentName name) {
        return null;
    }

    @Override
    public <T extends ICoreTechPart> void addListener(Class<T> asset, ParanoiaListener<T> listener) {
        managerMap.get(asset).addListener(listener);
    }

    public JFrame getVisuals() {
        return visuals;
    }

    @Deprecated
    public void updateAsset(ICoreTechPart asset, ComponentName managerName) {
        //Do nothing
    }

    //FIXME: This is frontend task, not backend
    public void fireRollMessage(
        Stat stat, Skill skill,
        boolean statChange, boolean skillChange,
        Map<String, Integer> positive,
        Map<String, Integer> negative
    ) {
        RollMessage msg = new RollMessage(
            this,
            stat, statChange,
            skill, skillChange,
            positive, negative,
            "Please roll with..."
        );

        msg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        msg.setLocationRelativeTo(visuals);
        msg.setVisible(true);
    }

    private void createPlayer() {
        String playerName = ParanoiaMessage.input("What is your name, citizen?");
        if(playerName == null) {
            network.fireTerminated("Client terminated the connection.");
        } else {
            player = new ParanoiaPlayer(playerName);
            sendCommand(new LobbyRequest(player.getName(), ""));
        }
    }

    private void connectToAlphaComplex(String connectUrl) {
        try {
            network.connectToServer(connectUrl);
            createPlayer();
        } catch (IOException ex) {
            ex.printStackTrace();
            ParanoiaMessage.error(ex);
        }
    }

    private void changeSettings() {
        String address = ParanoiaMessage.input("Set destination of Alpha Complex");
        if(address != null && !address.equals("")) {
            System.out.println("New address: '" + address + "'");
            connectUrl = address;
            mainFrame.updateSettings(connectUrl);
        }
    }

    @Override
    public boolean sendCommand(ParanoiaCommand command) {
        return network.sendCommand(command);
    }

    @Override
    public void runCommand(String command) {
        ParanoiaButtonCommand btn = ParanoiaButtonCommand.NULL;
        try {
            btn = ParanoiaButtonCommand.valueOf(command);
        } catch (IllegalArgumentException ignored) {}
        switch (btn) {
            case START_LOBBY:
                connectToAlphaComplex(connectUrl);
                break;
            case SETTINGS:
                changeSettings();
                break;
            case EXIT_TO_MENU:
                mainFrame.setVisible(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void validateLobby(boolean valid, boolean hasPassword) {
        if (valid) {
            //Auth successful
            mainFrame.dispose();
            new LobbyFrame(network, player, this).setVisible(true);
        } else {
            if (hasPassword) {
                //Request password
                String password = ParanoiaMessage.input("Enter server password");
                sendCommand(new LobbyRequest(player.getName(), password));
            } else {
                ParanoiaMessage.error("This name is already assigned to a TroubleShooter");
                createPlayer();
            }
        }
    }
}
