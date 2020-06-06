package paranoia.visuals.custom;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParanoiaSkillButton extends ParanoiaButton implements ActionListener {

    private final JLabel source;
    private final JLabel label;
    private boolean editable = true;
    private boolean set = false;
    private final ParanoiaSkillBroadcastListener listener;
    private static final Color lbColor = new Color(38, 38, 38);

    public ParanoiaSkillButton(JLabel source, JLabel label, ParanoiaSkillBroadcastListener listener) {
        super("0");
        this.source = source;
        addActionListener(this);
        this.listener = listener;
        this.label = label;
    }

    public void setEditable(boolean b) {
        editable = b;
        label.setForeground(
            b ? lbColor : new Color(177, 44, 44)
        );
    }

    public void unset() {
        if(editable && set) {
            setText("0");
            resetBackgroud();
            label.setForeground(lbColor);
            set = false;
        }
    }

    public int getValue() {
        return Integer.parseInt(getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(editable) {
            listener.unsetAll();
            setText(String.valueOf(Integer.parseInt(source.getText())));
            set = true;
            setBackground(new Color(182, 132, 44, 133));
        }
    }

    public void lock() {
        if(set) {
            set = false;
            if(getValue() > 0) {
                setBackground(new Color(193, 215, 174, 60));
            } else {
                setBackground(new Color(220, 181, 181, 60));
            }
            setEnabled(false);
        }
        setEditable(false);
        label.setForeground(lbColor);
    }

    public interface ParanoiaSkillBroadcastListener {
        void unsetAll();
    }
}
