package paranoia.visuals.panels.acpf;

import daiv.networking.command.acpf.request.SkillRequest;
import daiv.networking.command.acpf.response.SkillResponse;
import daiv.ui.AssetManager;
import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.technical.CommandParser;
import paranoia.services.technical.networking.Network;
import paranoia.visuals.custom.ParanoiaAttributePanel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static daiv.ui.LayoutManager.panelOf;

public class ACPFStatPage extends JPanel implements
    ACPFPage,
    ParanoiaAttributePanel.ParanoiaSkillButtonListener,
    SkillResponse.ParanoiaSkillListener,
    ParanoiaListener<ParanoiaAttribute> {

    public static void main(String[] args) throws InterruptedException {
        JFrame f = new JFrame();
        CommandParser parser = new CommandParser();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.add(new ACPFPanel(new Network(parser)));
        f.pack();
        f.setVisible(true);
        Thread.sleep(8000);
        parser.parse(new SkillResponse("", 2, "0", "0"));
    }

    private final JLabel lbValue = new JLabel("");
    private final JTextArea lbChoose =  new JTextArea(2,5);
    private final JButton btnSend = new JButton("SEND");
    private final List<ParanoiaAttributePanel> attributePanels = new ArrayList<>();
    private boolean lastChoice = false;

    private static final String txtChoose = "Choose a skill with a value of";
    private static final String txtIdle = "Waiting for another player to choose...";

    public ACPFStatPage(ACPFPanel main) {
        setLayout(new BorderLayout());
        btnSend.setEnabled(false);
        btnSend.addActionListener( e -> {
            btnSend.setEnabled(false);
            ParanoiaAttributePanel selected = attributePanels.stream()
                .filter(ParanoiaAttributePanel::isSelected).findAny().orElse(null);
            if( selected == null) return;

            attributePanels.forEach(ParanoiaAttributePanel::reset);
            attributePanels.forEach(ParanoiaAttributePanel::lock);
            lbChoose.setText(txtIdle);
            System.out.println("Selected " + selected.getName());

            main.sendResponse(new SkillRequest(selected.getName()));
        });

        add(createInfoPanel(), BorderLayout.EAST);
        add(createStatsTable(), BorderLayout.CENTER);
        attributePanels.forEach(ParanoiaAttributePanel::lock);
        add(main.createButtonPanel(this, true, true), BorderLayout.SOUTH);
    }

    @Override
    public boolean validatePage() {
        return lastChoice;
    }

    private JPanel createStatsTable() {
        final int COLS = Stat.values().length;
        JPanel table = new JPanel(new GridLayout(0,COLS,5,0));
        for(Stat stat : Stat.values()) {
            ParanoiaAttributePanel statPanel = new ParanoiaAttributePanel(stat.toString(), 0);
            statPanel.setEnabled(false);
            table.add(statPanel);
        }
        for (int columns = 0; columns < COLS; columns++) {
            for (int i = 0; i < Skill.values().length; i += COLS) {
                String skill = Skill.values()[i + columns].toString();
                ParanoiaAttributePanel skillPanel = new ParanoiaAttributePanel(skill, 0);
                skillPanel.setListener(this);
                table.add(skillPanel);
                attributePanels.add(skillPanel);
            }
        }
        return table;
    }

    private JPanel createInfoPanel() {
        lbValue.setFont(AssetManager.getBoldFont(30));
        lbValue.setForeground(new Color(16, 27, 95));

        lbChoose.setText(txtIdle);
        lbChoose.setLineWrap(true);
        lbChoose.setWrapStyleWord(true);
        lbChoose.setOpaque(false);
        lbChoose.setEditable(false);
        lbChoose.setFont(AssetManager.getItalicFont(17));
        lbChoose.setMaximumSize(
            new Dimension(
                lbChoose.getMaximumSize().width,
                getFontMetrics(lbChoose.getFont()).getHeight() * lbChoose.getRows()
            )
        );
        JPanel infoPanel = panelOf(
            new Component[]{
                Box.createVerticalGlue(),
                lbChoose,
                lbValue,
                btnSend,
                Box.createVerticalGlue()
            }, BoxLayout.PAGE_AXIS
        );

        for (Component component : infoPanel.getComponents()) {
            if(component instanceof JComponent){
                ((JComponent) component).setAlignmentX(CENTER_ALIGNMENT);
            }
        }

        return infoPanel;
    }

//    private int calculateStat(Stat stat) {
//        return (int) skills.entrySet().stream()
//            .filter(
//                entry -> entry.getKey().getParent().equals(stat) &&
//                    entry.getValue().getValue() > 0
//            ).count();
//    }

    @Override
    public void skillChosen(String skill, int value, String who, String chose) {
        if(skill.isEmpty()) {
            lbChoose.setText(txtChoose);
            lbValue.setText(Integer.toString(value));
            attributePanels.forEach(ParanoiaAttributePanel::reset);
        }
    }

    @Override
    public void selectAttribute(ParanoiaAttributePanel panel) {
        attributePanels.forEach(ParanoiaAttributePanel::reset);
        panel.select();
        btnSend.setEnabled(true);
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaAttribute> updatedModel) {

    }

//    @Override
//    public void alert(int fillValue, Skill[] disabled, boolean lastChoice) {
//        lbValue.setText(String.valueOf(fillValue));
//        lbChoose.setText(txtChoose);
//        this.lastChoice = lastChoice;
//        attributes.forEach((key, value) -> {
//            value.setEditable(true);
//            for (Skill skill : disabled)
//                if(skill.equals(key))
//                    value.setEditable(false);
//        });
//    }
}
