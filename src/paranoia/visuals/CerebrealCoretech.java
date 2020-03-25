package paranoia.visuals;

import paranoia.core.Clone;
import paranoia.core.SecurityClearance;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

import static java.awt.BorderLayout.NORTH;

public class CerebrealCoretech extends JFrame {

    public CerebrealCoretech() {
        //Setup metadata
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paranoia");
        setMinimumSize(new Dimension(320, 280));

        //Setup visuals
        //Layout
        setLayout(new BorderLayout());

        //TODO: temporary: create one Clone
        Clone clone = new Clone("SYD", "ULS", SecurityClearance.RED, 5);
        add(clone.getVisual(), NORTH);
    }



}
