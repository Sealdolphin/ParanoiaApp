package paranoia.visuals.panels.acpf;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.CardLayout;

public class ACPFPanel extends JPanel {

    private final CardLayout layout = new CardLayout();

    public ACPFPanel() {
        setLayout(layout);

        //Adding pages
        add(new ACPFGeneralPage(this));
        add(new ACPFStatPage());
        layout.first(this);
    }

    public JPanel createButtonPanel(ACPFPage page, boolean hasPrev, boolean hasNext) {
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.LINE_AXIS));
        JButton btnPrev = createPrevButton();
        JButton btnNext = createNextButton(page);
        btnNext.setEnabled(hasNext);
        btnPrev.setEnabled(hasPrev);
        panelButtons.add(Box.createHorizontalGlue());
        panelButtons.add(btnPrev);
        panelButtons.add(btnNext);
        return panelButtons;
    }

    private JButton createPrevButton() {
        JButton btnPrev = new JButton("<");
        btnPrev.addActionListener(e -> layout.previous(this));
        return btnPrev;
    }

    private JButton createNextButton(ACPFPage page) {
        JButton btnNext = new JButton(">");
        btnNext.addActionListener(e -> {
            if(page.validatePage())
                layout.next(this);
        });
        return btnNext;
    }

}
