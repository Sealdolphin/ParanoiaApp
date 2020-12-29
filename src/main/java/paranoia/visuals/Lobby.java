package paranoia.visuals;

import daiv.networking.command.ParanoiaCommand;
import daiv.networking.command.general.PlayerBroadcast;
import daiv.ui.AssetManager;
import daiv.ui.custom.ParanoiaButtonListener;
import daiv.ui.visuals.ParanoiaButton;
import paranoia.core.ParanoiaPlayer;
import paranoia.services.hpdmc.manager.PlayerManager;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.util.List;

import static daiv.ui.LayoutManager.panelOf;
import static paranoia.services.hpdmc.ParanoiaButtonCommand.EXIT_TO_MENU;

public class Lobby implements PlayerBroadcast.PlayerConnectListener {

    private final JLabel lbAddress = new JLabel();
    private final JLabel lbPlayerName = new JLabel();
    private final ParanoiaButton btnLeave = new ParanoiaButton("Leave", EXIT_TO_MENU.name());
    private final PlayerManager manager = new PlayerManager();

    public Lobby(
        LobbyFrame parent,
        String ipAddress,
        String player,
        ParanoiaButtonListener listener
    ) {
        //Create
        lbAddress.setText("Server IP: " + ipAddress);
        lbAddress.setFont(AssetManager.getFont(20));

        lbPlayerName.setText(player);
        lbPlayerName.setFont(AssetManager.getFont(20, true, true, false));

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
                Box.createHorizontalGlue(), btnLeave
            }, BoxLayout.LINE_AXIS
        );
    }

    @Override
    public void playersConnected(List<PlayerBroadcast.PlayerData> list, String self) {
        manager.clear();
        for (PlayerBroadcast.PlayerData data : list) {
            if (data.uuid.equals(self)) continue;
            ParanoiaPlayer player = new ParanoiaPlayer(data.name, data.id);
            player.changeProfile(ParanoiaCommand.imageFromBytes(data.profile));
            manager.updateAsset(player);
        }
    }
}
