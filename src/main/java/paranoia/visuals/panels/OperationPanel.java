package paranoia.visuals.panels;

import paranoia.visuals.ComponentName;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

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
        JPanel operation = new JPanel();
        operation.setLayout(new FlowLayout());
        JButton btnRoll = new JButton("Roll");
        JButton btnChat = new JButton("Chat");

        btnRoll.addActionListener(e -> panel.showPanel(ComponentName.DICE_PANEL.name()));
        btnChat.addActionListener(e -> panel.showPanel(ComponentName.CHAT_PANEL.name()));

        operation.add(btnRoll);
        operation.add(btnChat);
        return operation;
    }

    @SuppressWarnings("NullableProblems")
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
}
