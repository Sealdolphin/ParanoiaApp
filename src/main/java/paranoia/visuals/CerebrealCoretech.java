package paranoia.visuals;

import paranoia.core.Clone;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;

public class CerebrealCoretech extends JFrame {

    public CerebrealCoretech() {
        //Setup metadata
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paranoia");
        setMinimumSize(new Dimension(300,300));

        //Setup visuals
        setBackground(new Color(59, 59, 59));
        //Assemble
        pack();
        setLocationRelativeTo(null);
    }

    public void addClone(Clone clone) {
        add(clone.getVisual());
        pack();
    }

}
