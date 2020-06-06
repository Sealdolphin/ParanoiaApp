package paranoia.visuals.panels.acpf;

import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.plc.AssetManager;
import paranoia.services.technical.command.DefineCommand;
import paranoia.visuals.custom.ParanoiaSkillButton;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.HashMap;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;
import static paranoia.services.plc.LayoutManager.createGrid;
import static paranoia.services.plc.LayoutManager.panelOf;

public class ACPFStatPage extends JPanel implements
    ACPFPage, ParanoiaSkillButton.ParanoiaSkillBroadcastListener,
    DefineCommand.ParanoiaDefineListener {

    private final JLabel lbValue = new JLabel("");
    private final JTextArea lbChoose =  new JTextArea(2,5);
    private final JButton btnSend = new JButton("SEND");
    private final HashMap<Skill, ParanoiaSkillButton> attributes = new HashMap<>();
    private final HashMap<Stat, JLabel> stats = new HashMap<>();
    private boolean lastChoice = false;

    private static final String txtChoose = "Choose a skill with a value of";
    private static final String txtIdle = "Waiting for another player to choose...";

    public ACPFStatPage(ACPFPanel main) {
        setLayout(new BorderLayout());
        btnSend.setEnabled(false);
        btnSend.addActionListener( e -> {
            attributes.values().forEach(ParanoiaSkillButton::lock);
            btnSend.setEnabled(false);
            stats.forEach((stat, label) -> label.setText(String.valueOf(calculateStat(stat))));
            lbValue.setText("");
            lbChoose.setText(txtIdle);
        });
        for (Stat stat : Stat.values()) {
            stats.put(stat, new JLabel("0", SwingConstants.CENTER));
        }

        add(createInfoPanel(), BorderLayout.EAST);
        add(createStatsTable(), BorderLayout.CENTER);
        add(main.createButtonPanel(this, true, true), BorderLayout.SOUTH);
    }

    @Override
    public boolean validatePage() {
        return lastChoice;
    }

    private JPanel createStatsTable() {
        JPanel table = new JPanel(new GridBagLayout());
        final int COLS = Stat.values().length;
        for (int i = 0; i < Skill.values().length + COLS; i++) {
            JLabel label;
            JComponent attributeValue;
            if(i < COLS) {
                Stat stat = Stat.values()[i];
                label = new JLabel(stat.toString());
                label.setFont(AssetManager.getBoldFont(20));
                attributeValue = stats.get(stat);
            } else {
                int get = (i % COLS) * COLS + ((i - COLS) / COLS);
                Skill skill = Skill.values()[get];
                label = new JLabel(skill.toString());
                label.setFont(AssetManager.getFont(15));
                ParanoiaSkillButton btn = new ParanoiaSkillButton(lbValue, label, this);
                attributes.put(skill, btn);
                btn.lock();
                btn.setBorderPainted(false);
                attributeValue = btn;
            }
            table.add(label, createGrid(15,10,0,10).at(i % COLS, 2 * (i / COLS)).anchor(CENTER).get());
            table.add(attributeValue, createGrid(0,0,0,0).at(i % COLS, 2 * (i / COLS) + 1).fill(BOTH).get());
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

    private int calculateStat(Stat stat) {
        return (int) attributes.entrySet().stream()
            .filter(
                entry -> entry.getKey().getParent().equals(stat) &&
                    entry.getValue().getValue() > 0
            ).count();
    }

    @Override
    public void unsetAll() {
        btnSend.setEnabled(true);
        attributes.values().forEach(ParanoiaSkillButton::unset);
    }

    @Override
    public void alert(int fillValue, Skill[] disabled, boolean lastChoice) {
        lbValue.setText(String.valueOf(fillValue));
        lbChoose.setText(txtChoose);
        this.lastChoice = lastChoice;
        attributes.forEach((key, value) -> {
            value.setEditable(true);
            for (Skill skill : disabled)
                if(skill.equals(key))
                    value.setEditable(false);
        });
    }
}
