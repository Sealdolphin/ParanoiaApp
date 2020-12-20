package paranoia.visuals;

import daiv.networking.command.general.AuthRequest;
import daiv.networking.command.general.AuthResponse;
import daiv.networking.command.general.PingCommand;
import daiv.ui.AssetManager;
import daiv.ui.custom.ParanoiaMessage;
import paranoia.core.ParanoiaPlayer;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.technical.networking.Network;
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

import static daiv.ui.LayoutManager.panelOf;

public class LobbyFrame extends JFrame implements
    PingCommand.ParanoiaPingListener,   //TODO: frontend should not handle business logic!!!!
    AuthRequest.ParanoiaAuthListener,
    ParanoiaListener<ParanoiaPlayer>
{

    private final Network network;
    private final String name;
    private JScrollPane lobbyPanel = new JScrollPane();

    public LobbyFrame(Network network, String player) {
        this.network = network;
        this.name = player;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 400));
        setTitle("Alpha Complex Personal Lobby");

        Lobby lobby = new Lobby(this, network, name);

        network.getParser().setPingListener(this);
        network.getParser().setAuthListener(this);

        setLayout(new BorderLayout());
        add(lobby.createInfoPanel(), BorderLayout.NORTH);
        add(lobbyPanel, BorderLayout.EAST);
        add(new ACPFPanel(network), BorderLayout.CENTER);
        pack();
    }

    @Override
    public void pong() {
        if(network == null) return;
        network.sendCommand(new PingCommand());
    }

    @Override
    public void requestAuth(boolean hasPassword) {
        String pass = null;
        if(hasPassword) {
            pass = ParanoiaMessage.input("Enter server password");
        }

        network.sendCommand(new AuthResponse(name, pass));
    }

    public void leave() {
        dispose();
        network.fireTerminated();
        //open menu frame!!
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaPlayer> updatedModel) {
        remove(lobbyPanel);
        lobbyPanel = new JScrollPane(createLobbyPanel((List<ParanoiaPlayer>) updatedModel));
        add(lobbyPanel, BorderLayout.EAST);
        pack();
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
