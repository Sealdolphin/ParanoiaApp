package paranoia.visuals.panels.acpf;

import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.plc.AssetManager;
import paranoia.visuals.custom.ParanoiaButton;
import paranoia.visuals.custom.ParanoiaSkillButton;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Random;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;
import static paranoia.services.plc.LayoutManager.createGrid;

public class ACPFStatPage extends JPanel implements
    ACPFPage, ParanoiaSkillButton.ParanoiaSkillBroadcastListener {

    private final JLabel lbValue = new JLabel("-3");
    private final JLabel lbMoxie = new JLabel("8");
    private final JLabel lbClones = new JLabel("6");
    private final JButton btnSend = new JButton("SEND");
    private final HashMap<Skill, ParanoiaSkillButton> attributes = new HashMap<>();
    //private int clonesLeft = 6;
    //private int moxieLeft = 8;

    public ACPFStatPage(ACPFPanel main) {
        setLayout(new BorderLayout());
        btnSend.setEnabled(false);
        btnSend.addActionListener( e -> {
            attributes.forEach((s, b) -> b.lock());
            btnSend.setEnabled(false);
            lbValue.setText(String.valueOf(new Random().nextInt(10) - 5)); //TODO: temp
        });
        for (Skill skill : Skill.values()) {
            attributes.put(skill, new ParanoiaSkillButton(lbValue, this));
        }
        add(createInfoPanel(), BorderLayout.EAST);
        add(createStatsTable(), BorderLayout.CENTER);
        add(main.createButtonPanel(this, true, false), BorderLayout.SOUTH);
    }

    @Override
    public boolean validatePage() {
        return true;
    }

    private JPanel createStatsTable() {
        JPanel table = new JPanel(new GridBagLayout());
        final int COLS = Stat.values().length;
        for (int i = 0; i < attributes.size() + COLS; i++) {
            JLabel label;
            ParanoiaButton btnValue;
            if(i < COLS) {
                label = new JLabel(Stat.values()[i].toString());
                label.setFont(AssetManager.getBoldFont(20));
                btnValue = new ParanoiaButton("0");
                btnValue.setEnabled(false);
            } else {
                int get = (i % COLS) * COLS + ((i - COLS) / COLS);
                Skill skill = Skill.values()[get];
                label = new JLabel(skill.toString());
                label.setFont(AssetManager.getFont(15));
                btnValue = attributes.get(skill);
            }
            btnValue.setBorderPainted(false);
            table.add(label, createGrid(15,10,0,10).at(i % COLS, 2 * (i / COLS)).anchor(CENTER).get());
            table.add(btnValue, createGrid(0,0,0,0).at(i % COLS, 2 * (i / COLS) + 1).fill(BOTH).get());
        }
        return table;
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();

        lbValue.setFont(AssetManager.getBoldFont(30));
        lbValue.setForeground(new Color(16, 27, 95));
        JLabel lbCloneText = new JLabel("Clones left:");
        JLabel lbMoxieText = new JLabel("Moxie left:");
        lbCloneText.setFont(AssetManager.getBoldFont(15));
        lbMoxieText.setFont(AssetManager.getBoldFont(15));
        lbClones.setFont(AssetManager.getBoldFont(20));
        lbClones.setForeground(new Color(16, 95 ,20));
        lbMoxie.setFont(AssetManager.getBoldFont(20));
        lbMoxie.setForeground(new Color(16, 95 ,20));

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.add(panelOf(new JComponent[]{lbCloneText, lbClones}));
        infoPanel.add(panelOf(new JComponent[]{lbMoxieText, lbMoxie}));
        JTextArea lbChoose = new JTextArea(2,5);
        lbChoose.setText("Choose a skill with a value of");
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
        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(lbChoose);
        infoPanel.add(lbValue);
        infoPanel.add(btnSend);
        infoPanel.add(Box.createVerticalGlue());

        for (Component component : infoPanel.getComponents()) {
            if(component instanceof JComponent){
                ((JComponent) component).setAlignmentX(CENTER_ALIGNMENT);
            }
        }

        return infoPanel;
    }

    private static JPanel panelOf(JComponent[] components) {
        return panelOf(components, BoxLayout.LINE_AXIS);
    }

    private static JPanel panelOf(JComponent[] components, int layout) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, layout));
        for (JComponent c : components) {
            panel.add(c);
            panel.add(Box.createHorizontalGlue());
        }
        return panel;
    }

    private int calculateStat(Stat stat) {
        return 0;
    }

    @Override
    public void unsetAll() {
        btnSend.setEnabled(true);
        attributes.forEach((key, value) -> value.unset());
    }
}
