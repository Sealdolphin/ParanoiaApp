package paranoia.visuals;

import daiv.ui.AssetManager;
import daiv.ui.visuals.ParanoiaButton;
import paranoia.Paranoia;
import paranoia.services.hpdmc.ControlUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.WindowEvent;

import static daiv.ui.LayoutManager.panelOf;
import static paranoia.services.hpdmc.ParanoiaButtonCommand.SETTINGS;
import static paranoia.services.hpdmc.ParanoiaButtonCommand.START_LOBBY;

public class MenuFrame extends JFrame {

    JLabel lbAddr = new JLabel("Address: ???");

    public MenuFrame(ControlUnit control) {
        setIconImage(Paranoia.icon != null ? Paranoia.icon.getScaledInstance(64, 64, Image.SCALE_SMOOTH) : null);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paranoia");

        getContentPane().setLayout(new BorderLayout());

        JLabel label = new JLabel("Alpha Complex Interface Emulator", SwingConstants.CENTER);
        label.setFont(AssetManager.getBoldFont(30));
        add(label, BorderLayout.NORTH);

        FontMetrics m = getFontMetrics(label.getFont());
        Dimension size = new Dimension(Short.MAX_VALUE, m.getHeight());

        ParanoiaButton btnStart = new ParanoiaButton("Start", START_LOBBY.name());
        btnStart.setBackground(new Color(164, 201, 127));
        btnStart.setFont(AssetManager.getFont(20));
        btnStart.setMaximumSize(size);
        btnStart.addParanoiaButtonListener(control);

        lbAddr.setFont(AssetManager.getItalicFont(15));
        lbAddr.setMaximumSize(size);

        JButton btnExit = new JButton("Exit");
        btnExit.setFont(AssetManager.getFont(20));
        btnExit.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        btnExit.setMaximumSize(size);

        ParanoiaButton btnSettings = new ParanoiaButton("Settings", SETTINGS.name());
        btnSettings.setFont(AssetManager.getFont(20));
        btnSettings.setMaximumSize(size);
        btnSettings.addParanoiaButtonListener(control);

        add(panelOf(new Component[]{
            btnStart,
            lbAddr,
            btnSettings,
            btnExit
        }, BoxLayout.PAGE_AXIS), BorderLayout.CENTER);

        add(new JLabel(Paranoia.version), BorderLayout.SOUTH);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void updateSettings(String address) {
        lbAddr.setText("Address: " + address);
    }

}
