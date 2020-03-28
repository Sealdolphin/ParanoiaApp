package paranoia.visuals;

import paranoia.core.Clone;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.FlowLayout;

public class CerebrealCoretech extends JFrame {

    public CerebrealCoretech() {
        //Setup metadata
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paranoia");

        setLayout(new FlowLayout());

        //Setup visuals
        setBackground(new Color(59, 59, 59));
        //Assemble
        pack();
        setLocationRelativeTo(null);
    }

    public void setSelf(Clone clone) {
        JPanel panel = clone.getSelfVisual();
        add(panel);
        pack();
    }

    public void addClone(Clone clone) {
        JPanel panel = clone.getVisual();
        add(panel);
        pack();
    }

}
