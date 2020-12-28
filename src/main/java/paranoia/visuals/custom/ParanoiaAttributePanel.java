package paranoia.visuals.custom;

import daiv.ui.AssetManager;
import daiv.ui.visuals.ParanoiaButton;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ParanoiaAttributePanel extends JPanel implements ActionListener {

    private final ParanoiaButton btnValue = new ParanoiaButton("");
    private final JLabel label = new JLabel();
    private boolean editable = true;
    private boolean selected = false;
    private ParanoiaSkillButtonListener listener = null;
    private static final Color lbColor = new Color(38, 38, 38);
    private static final Color lbColorDisabled = new Color(177, 44, 44);

    public ParanoiaAttributePanel(String name, int value) {
        btnValue.setText(String.valueOf(value));
        label.setText(name);
        Font font = AssetManager.getBoldFont(15);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        btnValue.addActionListener(this);
        for(JComponent panel : Arrays.asList(label, btnValue)) {
            panel.setFont(font);
            panel.setAlignmentX(CENTER_ALIGNMENT);
            panel.setMaximumSize(new Dimension(Short.MAX_VALUE, label.getPreferredSize().height * 2));
            add(panel);
        }
    }

    public void setListener(ParanoiaSkillButtonListener listener) {
        this.listener = listener;
    }

    public void reset() {
        selected = false;
        editable = true;
        setColors();
    }

    public void select() {
        selected = editable;
        setColors();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        editable = enabled;
        btnValue.setLabelMode(!enabled);
    }

    public int getValue() {
        return Integer.parseInt(btnValue.getText());
    }

    public String getName() { return label.getText(); }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener != null && editable)
            listener.selectAttribute(this);
    }

    public void lock() {
        if(selected) {
            selected = false;
        }
        editable = false;
        setColors();
    }

    private void setColors() {
        btnValue.setEnabled(editable);
        Color c;
        if(editable) {
            label.setForeground(lbColor);
            if(selected) {
                c = new Color(189, 178, 6, 255);
            } else {
                c = AssetManager.defaultButtonBackground;
            }
        } else {
            label.setForeground(lbColorDisabled);
            c = new Color(234, 181, 181, 255);
        }
        btnValue.setBackground(c);
    }

    public interface ParanoiaSkillButtonListener {
        void selectAttribute(ParanoiaAttributePanel panel);
    }
}
