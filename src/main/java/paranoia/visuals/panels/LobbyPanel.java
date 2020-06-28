package paranoia.visuals.panels;

import paranoia.services.plc.AssetManager;
import paranoia.services.plc.LayoutManager;
import paranoia.services.technical.networking.Network;
import paranoia.visuals.custom.ParanoiaButton;
import paranoia.visuals.custom.ParanoiaImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import static paranoia.services.plc.LayoutManager.panelOf;

public class LobbyPanel {

    private final JLabel lbAddress = new JLabel();
    private final JLabel lbPlayerName = new JLabel();
    private final JLabel lbRound = new JLabel();
    private final JButton btnLeave = new ParanoiaButton("Leave");

    public LobbyPanel(
        JFrame parent,
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

        btnLeave.setBackground(Color.RED.darker());
        btnLeave.setForeground(Color.WHITE);
        btnLeave.setFont(AssetManager.getFont(23));
        btnLeave.addActionListener(e -> parent.dispose());
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

    public JPanel createLobbyPanel() {
        JLabel lbParty = new JLabel("Your party:");
        lbParty.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbParty.setFont(AssetManager.getBoldFont(15));

        return panelOf(new Component[] {
                lbParty,
                Box.createVerticalGlue(),
                new PlayerPanel("ArthasB", null),
                Box.createVerticalGlue(),
                new PlayerPanel("JSX", null),
                Box.createVerticalGlue(),
                new PlayerPanel("Kamuba", null),
                Box.createVerticalGlue(),
                new PlayerPanel("Blazy", null),
                Box.createVerticalGlue()
            }, BoxLayout.PAGE_AXIS
        );
    }

    private static class PlayerPanel extends JPanel {
        public PlayerPanel(
            String playerName,
            BufferedImage playerImage
        ) {
            ParanoiaImage g = new ParanoiaImage(playerImage, true);
            g.setPreferredSize(new Dimension(75, 75));

            setLayout(new GridBagLayout());

            add(g, LayoutManager.createGrid().at(0, 0, 1, 3).get());
            add(new JLabel(playerName), LayoutManager.createGrid().at(1, 0).get());
            add(new JLabel("Last picked:"), LayoutManager.createGrid().at(1, 1).get());
            add(new JLabel("Alpha Complex +3"), LayoutManager.createGrid().at(1, 2).get());
        }
    }

}
