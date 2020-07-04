package paranoia.visuals;

import paranoia.core.ParanoiaPlayer;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.plc.AssetManager;
import paranoia.services.technical.command.HelloCommand;
import paranoia.services.technical.command.PingCommand;
import paranoia.services.technical.networking.Network;
import paranoia.visuals.messages.ParanoiaMessage;
import paranoia.visuals.panels.LobbyPanel;
import paranoia.visuals.panels.acpf.ACPFPanel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;
import java.util.List;

import static paranoia.services.plc.LayoutManager.panelOf;

public class LobbyFrame extends JFrame implements
    PingCommand.ParanoiaPingListener,
    HelloCommand.ParanoiaInfoListener,
    ParanoiaListener<ParanoiaPlayer>
{

    private final Network network;
    private final String name;
    private JScrollPane lobby;

    public LobbyFrame(Network network, String player) {
        this.network = network;
        this.name = player;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 400));
        setTitle("Alpha Complex Personal Lobby");

        network.getParser().setPingListener(this);
        network.getParser().setInfoListener(this);
        LobbyPanel lobbyPanel = new LobbyPanel(this, network, name);

        lobby = new JScrollPane();

        setLayout(new BorderLayout());
        add(lobbyPanel.createInfoPanel(), BorderLayout.NORTH);
        add(lobby, BorderLayout.EAST);
        add(new ACPFPanel(network), BorderLayout.CENTER);
        pack();
    }

    @Override
    public void pong() {
        network.sendCommand(new PingCommand());
    }

    @Override
    public void sayHello(String player, String password, boolean hasPassword) {
        String pass = null;
        if(hasPassword) {
            pass = ParanoiaMessage.input("Enter server password");
        }

        network.sendCommand(new HelloCommand(name, pass, hasPassword, null));
    }

    public void leave() {
        dispose();
        network.disconnect();
        new MenuFrame().setVisible(true);
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaPlayer> updatedModel) {
        remove(lobby);
        lobby = new JScrollPane(createLobbyPanel((List<ParanoiaPlayer>) updatedModel));
        add(lobby, BorderLayout.EAST);
    }

    public JPanel createLobbyPanel(List<ParanoiaPlayer> players) {
        JLabel lbParty = new JLabel("Your party:");
        lbParty.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbParty.setFont(AssetManager.getBoldFont(15));
        Component[] components = new Component[2 * players.size() + 1];
        components[0] = lbParty;

        for (int i = 0; i < players.size(); i++) {
            components[2 * i + 1] = Box.createHorizontalGlue();
            components[2 * i + 2] = players.get(i).getVisual();
        }

        return panelOf(components, BoxLayout.PAGE_AXIS);
    }
}
