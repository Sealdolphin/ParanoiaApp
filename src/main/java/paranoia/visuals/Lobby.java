package paranoia.visuals;

import paranoia.services.hpdmc.manager.PlayerManager;
import paranoia.services.plc.AssetManager;
import paranoia.services.technical.networking.Network;
import paranoia.visuals.custom.ParanoiaButton;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;

import static paranoia.services.plc.LayoutManager.panelOf;

public class Lobby {

    private final JLabel lbAddress = new JLabel();
    private final JLabel lbPlayerName = new JLabel();
    private final JLabel lbRound = new JLabel();
    private final JButton btnLeave = new ParanoiaButton("Leave");
    private final PlayerManager manager = new PlayerManager();

    public Lobby(
        LobbyFrame parent,
        Network network,
        String player
    ) {
        //Create
        lbAddress.setText("Server IP: " + network.getIP());
        lbAddress.setFont(AssetManager.getFont(20));

        lbPlayerName.setText(player);
        lbPlayerName.setFont(AssetManager.getFont(20, true, true, false));

        lbRound.setText("Round: 0");
        lbRound.setFont(AssetManager.getBoldFont(15));

        manager.addListener(parent);

        btnLeave.setBackground(Color.RED.darker());
        btnLeave.setForeground(Color.WHITE);
        btnLeave.setFont(AssetManager.getFont(23));
        btnLeave.addActionListener(e -> parent.leave());
    }

    public JPanel createInfoPanel() {
        return panelOf(new Component[]{
                Box.createHorizontalStrut(5), lbAddress,
                Box.createHorizontalGlue(), lbPlayerName,
                Box.createHorizontalStrut(15), lbRound,
                Box.createHorizontalGlue(), btnLeave
            }, BoxLayout.LINE_AXIS
        );
    }
}
