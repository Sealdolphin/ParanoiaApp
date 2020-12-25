package paranoia.visuals;

import daiv.ui.AssetManager;
import daiv.ui.custom.ParanoiaButtonListener;
import daiv.ui.visuals.ParanoiaButton;
import paranoia.services.hpdmc.manager.PlayerManager;
import paranoia.services.technical.networking.Network;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;

import static daiv.ui.LayoutManager.panelOf;
import static paranoia.services.hpdmc.ParanoiaButtonCommand.EXIT_TO_MENU;

public class Lobby {

    private final JLabel lbAddress = new JLabel();
    private final JLabel lbPlayerName = new JLabel();
    private final JLabel lbRound = new JLabel();
    private final ParanoiaButton btnLeave = new ParanoiaButton("Leave", EXIT_TO_MENU.name());
    private final PlayerManager manager = new PlayerManager();

    public Lobby(
        LobbyFrame parent,
        Network network,
        String player,
        ParanoiaButtonListener listener
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
        btnLeave.addParanoiaButtonListener(listener);
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
