package paranoia.visuals.panels.acpf;

import paranoia.services.plc.AssetManager;
import paranoia.services.plc.LayoutManager;
import paranoia.services.technical.Network;
import paranoia.services.technical.command.DefineCommand;
import paranoia.services.technical.command.ParanoiaCommand;
import paranoia.services.technical.command.ReorderCommand;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.CardLayout;
import java.awt.Component;

public class ACPFPanel extends JPanel {

    private final CardLayout layout = new CardLayout();
    private final DefineCommand.ParanoiaDefineListener defineListener;
    private final ReorderCommand.ParanoiaReorderListener reorderListener;
    private final Network network;

    public ACPFPanel(Network network) {
        setLayout(layout);
        this.network = network;

        ACPFStatPage statPage = new ACPFStatPage(this);
        defineListener = statPage;
        ACPFSwapPage swapPage = new ACPFSwapPage(this);
        reorderListener = swapPage;
        //Adding pages
        add(new ACPFGeneralPage(this));
        add(statPage);
        add(swapPage);
        add(new ACPFOptimizePage(this));
        layout.first(this);
    }

    public void sendResponse(ParanoiaCommand command) {
        network.sendMessage(command.toJsonObject().toString());
    }

    public JPanel createButtonPanel(ACPFPage page, boolean hasPrev, boolean hasNext) {
        JButton btnPrev = createPrevButton();
        JButton btnNext = createNextButton(page);
        btnNext.setEnabled(hasNext);
        btnPrev.setEnabled(hasPrev);
        return LayoutManager.panelOf(new Component[]{
            Box.createHorizontalGlue(),
            btnPrev,
            btnNext
        }, BoxLayout.LINE_AXIS);
    }

    private JButton createPrevButton() {
        JButton btnPrev = new AssetManager.ParanoiaArrow(BasicArrowButton.WEST);
        btnPrev.addActionListener(e -> layout.previous(this));
        return btnPrev;
    }

    private JButton createNextButton(ACPFPage page) {
        JButton btnNext = new AssetManager.ParanoiaArrow(BasicArrowButton.EAST);
        btnNext.addActionListener(e -> {
            if(page.validatePage())
                layout.next(this);
        });
        return btnNext;
    }

    public ReorderCommand.ParanoiaReorderListener getReorderListener() {
        return reorderListener;
    }

    public DefineCommand.ParanoiaDefineListener getDefineListener() {
        return defineListener;
    }
}
