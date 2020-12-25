package paranoia.visuals;

import daiv.ui.AssetManager;
import daiv.ui.custom.ParanoiaButtonListener;
import paranoia.Paranoia;
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
import java.awt.Image;
import java.util.Collection;
import java.util.List;

import static daiv.ui.LayoutManager.panelOf;

public class LobbyFrame extends JFrame implements
    ParanoiaListener<ParanoiaPlayer>
{

    private final Network network;
    private JScrollPane lobbyPanel = new JScrollPane();

    public LobbyFrame(Network network, ParanoiaPlayer player, ParanoiaButtonListener listener) {
        setIconImage(Paranoia.icon != null ? Paranoia.icon.getScaledInstance(64, 64, Image.SCALE_SMOOTH) : null);

        this.network = network;
        String name = player.getName();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 400));
        setTitle("Alpha Complex Personal Lobby");

        Lobby lobby = new Lobby(this, network, name, listener);

        setLayout(new BorderLayout());
        add(lobby.createInfoPanel(), BorderLayout.NORTH);
        add(lobbyPanel, BorderLayout.EAST);
        add(new ACPFPanel(network), BorderLayout.CENTER);
        pack();
    }

    public void leave() {
        dispose();
        network.fireTerminated("Player left the lobby");
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
