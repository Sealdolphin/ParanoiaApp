package paranoia.visuals.clones;

import paranoia.core.SecurityClearance;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;

public class CloneInfoPanel extends JTextArea {

    private Boolean detailsPanel;

    public CloneInfoPanel(Map<String, String> info, SecurityClearance clearance) {
        this(info, clearance, false);
    }

    public CloneInfoPanel(Map<String, String> info, SecurityClearance clearance, Boolean details) {
        setBackground(details ? clearance.getBackgroundColor() : clearance.getColor());
        setForeground(clearance.getFontColor());
        setEditable(false);
        setFont(new Font("Arial", Font.BOLD, 15));
        String text = info.entrySet().stream().map( entry ->
            "///" + entry.getKey() + ": " + entry.getValue()
        ).reduce("", (zero, str) -> zero + str + System.lineSeparator());
        setText(text.trim());
        //Setting up some visuals
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        detailsPanel = details;
    }

    @Override
    public Dimension getPreferredSize() {
        if(detailsPanel)
            return super.getPreferredSize();
        else
            return new Dimension(super.getPreferredSize().width, 5);
    }
}
