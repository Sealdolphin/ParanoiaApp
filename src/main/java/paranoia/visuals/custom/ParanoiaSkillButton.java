package paranoia.visuals.custom;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParanoiaSkillButton extends ParanoiaButton implements ActionListener {

    private final JLabel source;

    public ParanoiaSkillButton(JLabel source) {
        super("0");
        this.source = source;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setText(source.getText());
    }
}
