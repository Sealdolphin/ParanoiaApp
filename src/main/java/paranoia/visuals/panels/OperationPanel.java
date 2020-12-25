package paranoia.visuals.panels;

import daiv.ui.custom.ParanoiaMessage;
import daiv.ui.visuals.ParanoiaButton;
import paranoia.visuals.ComponentName;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static daiv.Computer.getParanoiaResource;

public class OperationPanel extends JPanel {

    private final CardLayout layout = new CardLayout();
    private final Map<String, Component> componentMap = new HashMap<>();

    public OperationPanel() {
        setLayout(layout);
    }

    public void activatePanel(JPanel panel, String place) {
        add(panel, place);
        layout.show(this, place);
    }

    public void showPanel(String panel) {
        layout.show(this, panel);
    }

    public static JPanel createOperationPanel(OperationPanel panel) {

        JButton btnRoll;
        JButton btnChat;

        try {
            BufferedImage chat = ImageIO.read(new File(getParanoiaResource("ui/btnChat.png")));
            BufferedImage roll = ImageIO.read(new File(getParanoiaResource("ui/btnRoll.png")));
            btnChat = new ParanoiaButton(chat, "Chat");
            btnRoll = new ParanoiaButton(roll, "Roll");

        } catch (IOException e) {
            ParanoiaMessage.error(e);

            btnChat = new JButton("Chat");
            btnRoll = new JButton("Roll");
        }

        JPanel operation = new JPanel();
        operation.setLayout(new FlowLayout());

        btnRoll.addActionListener(e -> panel.showPanel(ComponentName.DICE_PANEL.name()));
        btnChat.addActionListener(e -> panel.showPanel(ComponentName.CHAT_PANEL.name()));

        operation.add(btnRoll);
        operation.add(btnChat);
        return operation;
    }
    
    @Override
    public void add(Component comp, Object constraints) {
        String key = (String) constraints;
        Component contains = componentMap.get(key);
        if(contains != null) {
            remove(contains);
        }
        componentMap.put(key, comp);
        super.add(comp, constraints);
    }

    public boolean checkComponent(String key) {
        return componentMap.containsKey(key);
    }
}
