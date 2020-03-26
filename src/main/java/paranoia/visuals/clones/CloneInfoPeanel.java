package paranoia.visuals.clones;

import paranoia.core.SecurityClearance;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;

public class CloneInfoPeanel extends JTextArea {

    public CloneInfoPeanel(Map<String, String> info, SecurityClearance clearance) {
        setBackground(clearance.getColor());
        setForeground(clearance.getFontColor());
        setEditable(false);
        setFont(new Font("Monospaced", Font.BOLD, 30));
        String text = info.entrySet().stream().map( entry ->
            "///" + entry.getKey() + ": " + entry.getValue()
        ).reduce("", (zero, str) -> zero + str + System.lineSeparator());
        setText(text);
        //Setting up some visuals
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, 50);
    }
}
