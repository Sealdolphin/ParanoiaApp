package paranoia.services.hpdmc;

import daiv.networking.command.ParanoiaCommand;
import daiv.ui.custom.ParanoiaButtonListener;
import daiv.ui.custom.ParanoiaMessage;
import paranoia.core.ICoreTechPart;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.manager.AttributeManager;
import paranoia.services.hpdmc.manager.CardManager;
import paranoia.services.hpdmc.manager.MissionManager;
import paranoia.services.hpdmc.manager.ParanoiaManager;
import paranoia.services.hpdmc.manager.TroubleShooterManager;
import paranoia.services.technical.CommandParser;
import paranoia.services.technical.networking.Network;
import paranoia.visuals.CerebralCoretech;
import paranoia.visuals.ComponentName;
import paranoia.visuals.LobbyFrame;
import paranoia.visuals.MenuFrame;
import paranoia.visuals.messages.RollMessage;
import paranoia.visuals.panels.OperationPanel;
import paranoia.visuals.panels.acpf.ACPFPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls the core game elements - GameMaster interface
 */
public class ControlUnit implements ParanoiaController, ParanoiaButtonListener {

    CerebralCoretech visuals;
    private MenuFrame mainFrame;
    private final Map<ComponentName, ParanoiaManager<? extends ICoreTechPart>> managerMap;
    private final OperationPanel operationPanel;
    private final Network network;
    private String connectUrl = "http://127.0.0.1:6532";
    private String player;

    public ControlUnit(Network network) {
        operationPanel = new OperationPanel();
        //Setup managers
        managerMap = new HashMap<>();
        managerMap.put(ComponentName.MISSION_PANEL, new MissionManager());
        managerMap.put(ComponentName.ACTION_CARD_PANEL, new CardManager());
        managerMap.put(ComponentName.EQUIPMENT_CARD_PANEL, new CardManager());
        managerMap.put(ComponentName.MISC_CARD_PANEL, new CardManager());
        managerMap.put(ComponentName.SKILL_PANEL, new AttributeManager());
        managerMap.put(ComponentName.TROUBLESHOOTER_PANEL, new TroubleShooterManager());
        managerMap.put(ComponentName.SELF_PANEL, new TroubleShooterManager());
        //Setup miscellaneous
//        ChatPanel chatPanel = new ChatPanel(clone, this);
//        operationPanel.activatePanel(chatPanel, ComponentName.CHAT_PANEL.name());
        //Setup network
        this.network = network;
        ACPFPanel acpfPanel = new ACPFPanel(network);
//        network.getParser().setChatListener(chatPanel);
//        network.getParser().setAcpfListener(clone);
//        network.getParser().setRollListener(this);
//        network.getParser().setDefineListener(acpfPanel.getDefineListener());
//        network.getParser().setReorderListener(acpfPanel.getReorderListener());
        //Setup visuals
//        visuals = new CerebralCoretech(this, clone);
    }

    @SuppressWarnings("rawtypes")
    public ParanoiaManager getManager(ComponentName name) {
        return managerMap.get(name);
    }

    public JFrame getVisuals() {
        return visuals;
    }

    public void updateAsset(ICoreTechPart asset, ComponentName managerName) {
        managerMap.get(managerName).updateAsset(asset);
    }

    public JPanel getMiscPanel() {
        JPanel miscPanel = new JPanel();
        miscPanel.setLayout(new BorderLayout());
        miscPanel.setName(ComponentName.MISC_PANEL.name());

        miscPanel.add(OperationPanel.createOperationPanel(operationPanel), BorderLayout.NORTH);
        miscPanel.add(operationPanel, BorderLayout.CENTER);
        return miscPanel;
    }

    public OperationPanel getOperationPanel() {
        return operationPanel;
    }

    //FIXME: This is frontend
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

    private void createPlayer(String connectUrl, MenuFrame menuFrame) {
        String playerName = ParanoiaMessage.input("What is your name, citizen?");
        if(playerName != null && !playerName.isEmpty()) {
            player = playerName;
            Network network = new Network(new CommandParser());
            //Network
            try {
                network.connectToServer(connectUrl);
                menuFrame.dispose();
                new LobbyFrame(network, playerName).setVisible(true);
            } catch (IOException ex) {
                ex.printStackTrace();
                ParanoiaMessage.error(ex);
            }
        }
    }

    private void changeSettings() {
        String address = ParanoiaMessage.input("Set destination of Alpha Complex");
        if(address != null && !address.equals("")) {
            System.out.println("New address: '" + address + "'");
            connectUrl = address;
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
                createPlayer(connectUrl, mainFrame);
                break;
            case SETTINGS:
                changeSettings();
                break;
            default:
                break;
        }
    }
}
