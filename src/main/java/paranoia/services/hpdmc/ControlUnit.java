package paranoia.services.hpdmc;

import paranoia.core.Clone;
import paranoia.core.ICoreTechPart;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.manager.AttributeManager;
import paranoia.services.hpdmc.manager.CardManager;
import paranoia.services.hpdmc.manager.MissionManager;
import paranoia.services.hpdmc.manager.ParanoiaManager;
import paranoia.services.hpdmc.manager.TroubleShooterManager;
import paranoia.services.technical.command.HelloCommand;
import paranoia.services.technical.command.ParanoiaCommand;
import paranoia.services.technical.command.RollCommand;
import paranoia.services.technical.networking.Network;
import paranoia.visuals.CerebralCoretech;
import paranoia.visuals.ComponentName;
import paranoia.visuals.messages.ParanoiaMessage;
import paranoia.visuals.messages.RollMessage;
import paranoia.visuals.panels.ChatPanel;
import paranoia.visuals.panels.OperationPanel;

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
public class ControlUnit implements ParanoiaController,
    RollCommand.ParanoiaRollListener,
    HelloCommand.ParanoiaInfoListener
{

    CerebralCoretech visuals;
    private final Map<ComponentName, ParanoiaManager<? extends ICoreTechPart>> managerMap;

    private final OperationPanel operationPanel;
    private final Network network;
    private final String playerName;

    public ControlUnit(Clone clone, String playerName) {
        this.playerName = playerName;
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
        ChatPanel chatPanel = new ChatPanel(clone, this);
        operationPanel.activatePanel(chatPanel, ComponentName.CHAT_PANEL.name());
        //Setup network
        //TODO: ACPF panel -> listens to network define and reorder commands
        network = new Network(
            chatPanel, clone, null,
            null, this, this
        );
        //Setup visuals
        visuals = new CerebralCoretech(this, clone);
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

    public void connectToServer(String ipAddress) throws IOException {
        //Connect to server
        network.connectWithIP(ipAddress);
        network.listen();
    }

    public boolean sendCommand(ParanoiaCommand command) {
        if(network.isOpen()) {
            network.sendMessage(command.toJsonObject().toString());
            return true;
        } else {
            return false;
        }
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

    @Override
    public void sayHello(String player, String password, boolean hasPassword) {
        System.out.println("HELLO!!");
        String pass = null;
        if(hasPassword) {
            pass = ParanoiaMessage.input("Enter server password");
        }
        //PONG!
        sendCommand(new HelloCommand(playerName, pass, hasPassword, null));
    }
}
