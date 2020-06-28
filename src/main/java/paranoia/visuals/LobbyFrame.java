package paranoia.visuals;

import paranoia.services.technical.networking.Network;
import paranoia.visuals.panels.LobbyPanel;
import paranoia.visuals.panels.acpf.ACPFPanel;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class LobbyFrame extends JFrame {

    public LobbyFrame(Network network, String player) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 400));
        setTitle("Alpha Complex Personal Lobby");

        LobbyPanel lobbyPanel = new LobbyPanel(this, network, player);

        setLayout(new BorderLayout());
        add(lobbyPanel.createInfoPanel(), BorderLayout.NORTH);
        add(new JScrollPane(lobbyPanel.createLobbyPanel()), BorderLayout.EAST);
        add(new ACPFPanel(network), BorderLayout.CENTER);
        pack();
    }

}
