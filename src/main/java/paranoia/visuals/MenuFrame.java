package paranoia.visuals;

import paranoia.Paranoia;
import paranoia.services.plc.AssetManager;
import paranoia.services.technical.CommandParser;
import paranoia.services.technical.networking.Network;
import paranoia.visuals.custom.ParanoiaButton;
import paranoia.visuals.messages.ParanoiaMessage;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import static paranoia.Paranoia.getParanoiaResource;
import static paranoia.services.plc.LayoutManager.panelOf;

public class MenuFrame extends JFrame {

    private String connectUrl = "http://127.0.0.1:6532";

    public MenuFrame() {
        try {
            Image icon = ImageIO.read(new File(getParanoiaResource("ui/paranoia.png")));
            setIconImage(icon.getScaledInstance(64,64,Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paranoia");

        getContentPane().setLayout(new BorderLayout());

        JLabel label = new JLabel("Alpha Complex Interface Emulator", SwingConstants.CENTER);
        label.setFont(AssetManager.getBoldFont(30));
        add(label, BorderLayout.NORTH);

        FontMetrics m = getFontMetrics(label.getFont());
        Dimension size = new Dimension(Short.MAX_VALUE, m.getHeight());

        JButton btnStart = new ParanoiaButton("Start");
        btnStart.setBackground(new Color(164, 201, 127));
        btnStart.setFont(AssetManager.getFont(20));
        btnStart.setMaximumSize(size);
        btnStart.addActionListener(e-> createPlayer());

        JLabel lbAddr = new JLabel("Address: " + connectUrl);
        lbAddr.setFont(AssetManager.getItalicFont(15));
        lbAddr.setMaximumSize(size);

        JButton btnExit = new JButton("Exit");
        btnExit.setFont(AssetManager.getFont(20));
        btnExit.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        btnExit.setMaximumSize(size);

        JButton btnSettings = new JButton("Settings");
        btnSettings.setFont(AssetManager.getFont(20));
        btnSettings.setMaximumSize(size);
        btnSettings.addActionListener(e -> {
            String address = JOptionPane.showInputDialog(
                this,
                "Set destination of Alpha Complex",
                "Settings",
                JOptionPane.INFORMATION_MESSAGE
            );
            if(address != null && !address.equals("")) {
                System.out.println("New address: '" + address + "'");
                connectUrl = address;
                lbAddr.setText("Address: " + connectUrl);
            }
        });

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

    private void createPlayer() {
        String playerName = ParanoiaMessage.input("What is your name, citizen?");
        if(playerName != null && !playerName.isEmpty()) {
            Network network = new Network(new CommandParser());
            //Network
            try {
                network.connectToServer(connectUrl);
                dispose();
                new LobbyFrame(network, playerName).setVisible(true);
            } catch (IOException ex) {
                ex.printStackTrace();
                ParanoiaMessage.error(ex);
            }
        }
    }

}
