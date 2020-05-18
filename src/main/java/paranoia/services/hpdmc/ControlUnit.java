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
import paranoia.services.technical.Network;
import paranoia.services.technical.command.DisconnectCommand;
import paranoia.services.technical.command.ParanoiaCommand;
import paranoia.visuals.CerebralCoretech;
import paranoia.visuals.ComponentName;
import paranoia.visuals.messages.ParanoiaError;
import paranoia.visuals.messages.RollMessage;
import paranoia.visuals.panels.ChatPanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls the core game elements - GameMaster interface
 */
public class ControlUnit implements DisconnectCommand.ParanoiaDisconnectListener {

    CerebralCoretech visuals;
    private final Map<ComponentName, ParanoiaManager<? extends ICoreTechPart>> managerMap;

    private final JPanel miscPanel;
    private final Network network;
    private final ChatPanel chatPanel;

    public ControlUnit(Clone clone) {
        miscPanel = new JPanel();
        miscPanel.setLayout(new BorderLayout());
        miscPanel.setName(ComponentName.MISC_PANEL.name());
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
        chatPanel = new ChatPanel(clone, this);
        //Setup network
        network = new Network(chatPanel, this);
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

    public void updateAsset(ICoreTechPart asset, ComponentName name) {
        managerMap.get(name).updateAsset(asset);
    }

    public void connectToServer(String ipAddress) throws MalformedURLException, UnknownHostException {
        //Connect to server
        network.connectWithIP(ipAddress);

        //Start listening thread
        Thread listening = new Thread(() -> {
            while (network.isOpen()) {
                network.listen();
            }
        });
        listening.start();
    }

    @Override
    public void disconnect() {
        network.disconnect();
    }

    public void activateMiscPanel(JPanel panel) {
        clearPanel();
        JButton btnX = new JButton("Clear");
        btnX.addActionListener(event -> clearPanel());
        miscPanel.add(btnX, BorderLayout.NORTH);
        miscPanel.add(panel, BorderLayout.CENTER);
        miscPanel.updateUI();
    }

    private void clearPanel() {
        miscPanel.removeAll();
        miscPanel.updateUI();
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
        //Auto updates from clone info
        //Injury: --> negatives
        //Action card on play --> positives

        msg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        msg.setLocationRelativeTo(visuals);
        msg.setVisible(true);
    }

    public void activateChatWindow() {
        activateMiscPanel(chatPanel);
    }

    public boolean sendCommand(ParanoiaCommand command) {
        if(network.isOpen()) {
            network.sendMessage(command.toJsonObject().toString());
            return true;
        } else {
            ParanoiaError.error("Network is unavailable");
            return false;
        }
    }

    public JPanel getMiscPanel() {
        return miscPanel;
    }
}
