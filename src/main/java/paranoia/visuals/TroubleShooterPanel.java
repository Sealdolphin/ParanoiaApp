package paranoia.visuals;

import paranoia.core.Clone;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import java.util.List;

public class TroubleShooterPanel extends JPanel {

    public TroubleShooterPanel(List<Clone> troubleShooters) {
        FlowLayout panelLayout = new FlowLayout();
        panelLayout.setHgap(25);
        panelLayout.setVgap(15);
        setOpaque(false);
        setLayout(panelLayout);
        troubleShooters.forEach( clone -> add(clone.getVisual()));
    }

    public JScrollPane getScrollPane() {
        return new JScrollPane(
            this,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
    }
}
