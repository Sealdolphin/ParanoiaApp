package paranoia.visuals.panels.acpf;

import daiv.networking.command.ParanoiaCommand;
import daiv.ui.AssetManager;
import paranoia.services.technical.networking.Network;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Insets;

import static daiv.ui.LayoutManager.panelOf;

public class ACPFPanel extends JPanel {

    private final CardLayout layout = new CardLayout();
    private final Network network;

    public ACPFPanel(Network network) {
        setLayout(layout);
        this.network = network;

        ACPFStatPage statPage = new ACPFStatPage(this);
        ACPFSwapPage swapPage = new ACPFSwapPage(this);
        ACPFGeneralPage generalPage = new ACPFGeneralPage(this);
        ACPFOptimizePage optimizePage = new ACPFOptimizePage(this);

        //Adding pages
        add(generalPage);
        add(statPage);
        add(swapPage);
        add(optimizePage);
        add(createFinalPage());
        layout.first(this);
        network.getParser().setSkillListener(statPage);
    }

    public void lockPanel(){
        layout.last(this);
    }

    private JPanel createFinalPage() {
        JPanel finalPage = new JPanel(new BorderLayout());
        finalPage.setLayout(new BoxLayout(finalPage, BoxLayout.PAGE_AXIS));

        JTextArea text = new JTextArea();
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setOpaque(false);
        text.setHighlighter(null);
        text.setFont(AssetManager.getItalicFont(25));
        text.setText(
            "Your response has been sent to Friend Computer. " +
                "Please wait until your clone is being created. " +
                "Thank you for your cooperation, citizen. " +
                "Remember, Happiness is mandatory!"
        );
        text.setMargin(new Insets(120,40,20,40));
        finalPage.add(text, BorderLayout.CENTER);
        return finalPage;
    }

    public void sendResponse(ParanoiaCommand command) {
        if(network == null) return;
        network.sendCommand(command);
    }

    public JPanel createButtonPanel(ACPFPage page, boolean hasPrev, boolean hasNext) {
        JButton btnPrev = createPrevButton();
        JButton btnNext = createNextButton(page);
        btnNext.setEnabled(hasNext);
        btnPrev.setEnabled(hasPrev);
        return panelOf(new Component[]{
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

//    public ReorderCommand.ParanoiaReorderListener getReorderListener() {
//        return reorderListener;
//    }
//
//    public DefineCommand.ParanoiaDefineListener getDefineListener() {
//        return defineListener;
//    }
}
