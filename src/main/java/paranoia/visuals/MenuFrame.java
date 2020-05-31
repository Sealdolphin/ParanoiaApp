package paranoia.visuals;

import paranoia.core.Clone;
import paranoia.core.SecurityClearance;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.services.plc.AssetManager;
import paranoia.services.technical.command.ACPFCommand;
import paranoia.visuals.messages.ParanoiaMessage;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static paranoia.Paranoia.getParanoiaResource;
import static paranoia.Paranoia.setUpSkillsNStats;

public class MenuFrame extends JFrame {

    private String connectUrl = "127.0.0.1";
    private BufferedImage img = null;

    public MenuFrame() {
        try {
            img = ImageIO.read(new File(getParanoiaResource("clones/clone0.png")));
            Image icon = ImageIO.read(new File(getParanoiaResource("ui/paranoia.png")));
            setIconImage(icon.getScaledInstance(64,64,Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paranoia");

        getContentPane().setLayout(new BorderLayout());

        JLabel label = new JLabel("Alpha Complex Interface Emulator");
        label.setFont(AssetManager.getBoldFont(30));
        add(label, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));

        JButton btnStart = new JButton("Start");
        btnStart.setFont(AssetManager.getFont(20));
        btnStart.setMaximumSize(label.getPreferredSize());
        btnStart.addActionListener(e-> {
            setVisible(false);
            //TODO: temporary
            Clone clone = new Clone("CARA", "RLY", SecurityClearance.YELLOW, "MALE", 0, img);
            ControlUnit controlUnit = new ControlUnit(clone);
            JFrame coreTech = controlUnit.getVisuals();
            coreTech.setExtendedState(Frame.MAXIMIZED_BOTH);
            setUpSkillsNStats(controlUnit);
            //Network
            try {
                controlUnit.connectToServer(connectUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
                ParanoiaMessage.error(ex);
            }
            controlUnit.sendCommand(new ACPFCommand("CARA", "MALE", new String[]{}, img, null));
            coreTech.setIconImage(getIconImage());
            coreTech.setVisible(true);
        });

        JLabel lbAddr = new JLabel("Address: " + connectUrl);
        lbAddr.setFont(AssetManager.getItalicFont(15));
        lbAddr.setMaximumSize(label.getPreferredSize());

        JButton btnExit = new JButton("Exit");
        btnExit.setFont(AssetManager.getFont(20));
        btnExit.setMaximumSize(label.getPreferredSize());
        btnExit.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

        JButton btnSettings = new JButton("Settings");
        btnSettings.setFont(AssetManager.getFont(20));
        btnSettings.setMaximumSize(label.getPreferredSize());
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

        btnPanel.add(btnStart);
        btnPanel.add(lbAddr);
        btnPanel.add(btnSettings);
        btnPanel.add(btnExit);

        add(btnPanel, BorderLayout.CENTER);

        add(new JLabel("version v.alpha"), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

}
