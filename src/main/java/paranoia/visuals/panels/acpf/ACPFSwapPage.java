package paranoia.visuals.panels.acpf;

import paranoia.core.cpu.Stat;
import paranoia.services.plc.AssetManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Stack;

import static paranoia.services.plc.LayoutManager.panelOf;

public class ACPFSwapPage extends JPanel implements ACPFPage {

    private final JButton btnReady = new JButton("READY");
    private final JButton btnUndo = new JButton("UNDO");
    private final JLabel[] modifiedStats = new JLabel[Stat.values().length];
    private final Stack<JButton> btnDowns = new Stack<>();

    public ACPFSwapPage(ACPFPanel main) {
        setLayout(new BorderLayout());

        btnReady.setAlignmentX(CENTER_ALIGNMENT);
        btnReady.setEnabled(false);
        btnReady.addActionListener( e -> {
            //swap bottom panel
        });

        btnUndo.setAlignmentX(CENTER_ALIGNMENT);
        btnUndo.setEnabled(false);
        btnUndo.addActionListener( e -> {
            btnDowns.pop().setEnabled(true);
            modifiedStats[btnDowns.size()].setText("");
            btnUndo.setEnabled(!btnDowns.isEmpty());
            btnReady.setEnabled(false);
        });

        JLabel lbTitle = new JLabel("Replace the stats of <PLAYERNAME>", SwingConstants.CENTER);
        lbTitle.setFont(AssetManager.getBoldFont(20));

        add(lbTitle, BorderLayout.NORTH);
        add(createSwapPanel(), BorderLayout.CENTER);
        add(createInfoPanel(), BorderLayout.EAST);
        add(main.createButtonPanel(this, true, false), BorderLayout.SOUTH);
    }

    private JPanel createSwapPanel() {
        JPanel swap = new JPanel();
        swap.setLayout(new BoxLayout(swap, BoxLayout.LINE_AXIS));
        swap.add(Box.createGlue());
        int i = 0;
        for (Stat stat : Stat.values()) {
            //Stat name
            JLabel lbStat = new JLabel(stat.toString(), SwingConstants.CENTER);
            lbStat.setFont(AssetManager.getFont(20, true, true, false));
            lbStat.setAlignmentX(CENTER_ALIGNMENT);
            //Original value
            JLabel lbOriginal = new JLabel(String.valueOf(stat.ordinal()), SwingConstants.CENTER);
            lbOriginal.setFont(AssetManager.getBoldFont(18));
            lbOriginal.setAlignmentX(CENTER_ALIGNMENT);
            //Modified value
            JLabel lbModified = new JLabel("", SwingConstants.CENTER);
            lbModified.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            lbModified.setFont(AssetManager.getBoldFont(18));
            lbModified.setMaximumSize(new Dimension(40,40));
            lbModified.setAlignmentX(CENTER_ALIGNMENT);
            modifiedStats[i++] = lbModified;
            //Button
            JButton btnDown = new AssetManager.ParanoiaArrow(BasicArrowButton.SOUTH);
            btnDown.addActionListener( e -> modifyNext(Integer.parseInt(lbOriginal.getText()), btnDown));
            btnDown.setAlignmentX(CENTER_ALIGNMENT);
            swap.add(
                panelOf(new Component[]{
                    lbStat,
                    Box.createRigidArea(new Dimension(15,15)),
                    lbOriginal,
                    btnDown,
                    Box.createRigidArea(new Dimension(15,30)),
                    lbModified,
                }, BoxLayout.PAGE_AXIS)
            );
            swap.add(Box.createGlue());
        }
        return swap;
    }

    private JPanel createInfoPanel() {
        return panelOf(
            new Component[]{
                Box.createVerticalGlue(),
                btnUndo,
                Box.createVerticalGlue(),
                btnReady,
                Box.createVerticalGlue()
            },
            BoxLayout.PAGE_AXIS
        );
    }

    private void modifyNext(int value, JButton button) {
        modifiedStats[btnDowns.size()].setText(String.valueOf(value));
        btnUndo.setEnabled(true);
        button.setEnabled(false);
        btnDowns.add(button);
        btnReady.setEnabled(btnDowns.size() == Stat.values().length);
    }

    @Override
    public boolean validatePage() {
        return true;
    }
}
