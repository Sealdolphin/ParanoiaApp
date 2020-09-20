package paranoia.visuals.messages;


import paranoia.core.SecurityClearance;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.services.hpdmc.ParanoiaController;
import paranoia.services.hpdmc.manager.AttributeManager;
import paranoia.services.hpdmc.manager.DiceManager;
import paranoia.services.hpdmc.manager.TroubleShooterManager;
import paranoia.services.plc.AssetManager;
import paranoia.visuals.ComponentName;
import paranoia.visuals.custom.ParanoiaButton;
import paranoia.visuals.panels.OperationPanel;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.CENTER;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class RollMessage extends JDialog {

    private final AttributeManager manager;
    private final Map<String, Integer> positive;
    private final Map<String, Integer> negative;
    private final JComboBox<Skill> skills = new JComboBox<>(Skill.values());
    private final JComboBox<Stat> stats = new JComboBox<>(Stat.values());
    private final ParanoiaButton btnRoll = new ParanoiaButton("Roll");
    private final JLabel lbDiceValue = new JLabel();
    private final JTextPane lbText = new JTextPane();
    private final JLabel lbMessage;
    private final JLabel lbDice = new JLabel("NODE");
    private final JLabel lbSkill = new JLabel();
    private final JLabel lbStat = new JLabel();

    public RollMessage(
            ControlUnit cpu,
            Stat defaultStat,
            Boolean allowChangeStat,
            Skill defaultSkill,
            Boolean allowChangeSkill,
            Map<String, Integer> positiveModifiers,
            Map<String, Integer> negativeModifiers,
            String message
    ) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setTitle("A message from the Gamemaster");
        //Set up values
        this.positive = positiveModifiers;
        this.negative = negativeModifiers;
        this.manager = (AttributeManager) cpu.getManager(ComponentName.SKILL_PANEL);
        SecurityClearance clearance = ((TroubleShooterManager) cpu
            .getManager(ComponentName.SELF_PANEL))
            .getClearance();

        Font boldFont15 = AssetManager.getFont(15, true, false, true);
        Font boldFont20 = AssetManager.getBoldFont(20);
        Font boldFont30 = AssetManager.getBoldFont(30);

        //Properties
        lbMessage = new JLabel(message);
        lbMessage.setFont(boldFont30);
        lbDice.setFont(boldFont15);
        lbDiceValue.setFont(boldFont20);
        lbDiceValue.setName("lbNode");
        btnRoll.setName("btnRoll");
        //Other properties
        setupRollButton(boldFont20, cpu.getOperationPanel(), clearance, cpu);
        setupSkill(boldFont20, allowChangeSkill);
        setupStat(boldFont20, allowChangeStat);
        //Set initial selection
        skills.setSelectedItem(defaultSkill);
        stats.setSelectedItem(defaultStat);
        setupModifierText();
        //Assemble layout
        assembleLayout();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void setupModifierText() {
        //Modifiers text
        lbText.setContentType("text/html");
        lbText.setText(updateModifierText());
        lbText.setToolTipText(updateTooltipText());
        lbText.setEditable(false);
        lbText.setOpaque(false);
    }

    private void setupStat(Font font, Boolean allowChange) {
        //Selected Stat
        lbStat.setFont(font);
        stats.setFont(font);
        stats.setEnabled(allowChange);
        stats.addActionListener( event -> updateStats(stats.getSelectedItem(), lbStat));
        stats.setName("cbStat");
        lbStat.setName("lbStat");
    }

    private void setupSkill(Font font, boolean allowChange) {
        //Selected Skill
        lbSkill.setFont(font);
        skills.setFont(font);
        skills.setEnabled(allowChange);
        skills.addActionListener( event -> updateStats(skills.getSelectedItem(), lbSkill));
        skills.setName("cbSkill");
        lbSkill.setName("lbSkill");
    }

    private void setupRollButton(Font font,
                                 OperationPanel panel,
                                 SecurityClearance clearance,
                                 ParanoiaController controller
    ) {
        //Button
        btnRoll.setFont(font);
        btnRoll.setBackground(new Color(166,0, 6));
        btnRoll.setForeground(Color.WHITE);
        btnRoll.addActionListener( event -> roll(panel, clearance, controller) );
    }

    private void assembleLayout() {
        //Set Layout
        GroupLayout root = new GroupLayout(getContentPane());
        getContentPane().setLayout(root);
        root.setAutoCreateGaps(true);
        root.setAutoCreateContainerGaps(true);
        //Set Horizontal groups
        root.setHorizontalGroup(root.createSequentialGroup()
            .addGroup(
                root.createParallelGroup(LEADING)
                    .addComponent(lbMessage)
                    .addGroup(
                        root.createSequentialGroup()
                            .addComponent(skills)
                            .addComponent(lbSkill)
                            .addComponent(stats)
                            .addComponent(lbStat)
                    )
                    .addComponent(lbText)
            )
            .addGap(20)
            .addGroup(
                root.createParallelGroup(CENTER)
                    .addComponent(lbDice)
                    .addComponent(lbDiceValue)
                    .addComponent(btnRoll)
            )
        );
        //Set Vertical groups
        root.setVerticalGroup(root.createSequentialGroup()
            .addGroup(
                root.createSequentialGroup()
                    .addGroup(
                        root.createParallelGroup(BASELINE)
                            .addComponent(lbMessage)
                    )
                    .addGap(25)
                    .addComponent(lbDiceValue)
                    .addGroup(
                        root.createParallelGroup(BASELINE)
                            .addComponent(skills)
                            .addComponent(lbSkill)
                            .addComponent(stats)
                            .addComponent(lbStat)
                            .addComponent(lbDice)
                    )
                    .addGroup(
                        root.createParallelGroup(BASELINE)
                            .addComponent(lbText)
                            .addComponent(btnRoll)
                    )
            )
        );
    }

    private void roll(OperationPanel panel, SecurityClearance clearance, ParanoiaController controller) {
        DiceManager diceManager = new DiceManager(calculateDiceValue(), clearance);
        JPanel dicePanel = diceManager.getDicePanel();
        diceManager.roll();
//        controller.sendCommand(new DiceCommand(diceManager.getResult()));
        panel.activatePanel(dicePanel, ComponentName.DICE_PANEL.name());
        this.dispose();
    }

    private String updateTooltipText() {
        if (
            skills.getSelectedItem() == null ||
                stats.getSelectedItem() == null
        ) return "Select an item";

        int skillPoint = manager.getAttribute(skills.getSelectedItem().toString());
        int statPoint = manager.getAttribute(stats.getSelectedItem().toString());

        String positives = positive.entrySet().stream().map(entry ->
            entry.getKey() + ": " + getHTMLColoredValue(entry.getValue()))
            .reduce("", (total, str) -> total + str + "<br>");
        String negatives = negative.entrySet().stream().map(entry ->
            entry.getKey() + ": " + getHTMLColoredValue(-entry.getValue()))
            .reduce("", (total, str) -> total + str + "<br>");

        return "<html> " +
            "<font face = \"Arial\" size=\"5\">" +
            "<b>Skills and Stats:</b><br>" +
            "Skill: " + getHTMLColoredValue(skillPoint) + "<br>" +
            "Stat: " + getHTMLColoredValue(statPoint) +
            "<br><b>Positive modifiers:</b><br>" +
            positives +
            "<b>Negative modifiers:</b><br>" +
            negatives +
            "<hr><font face = \"Arial\" size=\"5\">" +
            "<b>Total: " + getHTMLColoredValue(calculateDiceValue()) +
            "</b></html>";
    }

    private String getHTMLColoredValue(int value){
        return "<font color=\"" +
            ((value < 0) ? "red"  : "green") +
            "\">" + value + "</font>";
    }

    private String updateModifierText() {
        int positiveModifiers = positive.values().stream().mapToInt(Integer::intValue).sum();
        int negativeModifiers = negative.values().stream().mapToInt(Integer::intValue).sum();
        return "<html> " +
            "<font face = \"Arial\" size=\"5\">" +
            "<b>Positive</b> modifiers: " + getHTMLColoredValue(positiveModifiers) +
            "<br>" +
            "<b>Negative</b> modifiers " + getHTMLColoredValue(-negativeModifiers) +
            "</font>" +
            "</html>";
    }

    private Integer calculateDiceValue() {
        if (
            skills.getSelectedItem() == null ||
                stats.getSelectedItem() == null
        ) return 0;

        int skillPoint = manager.getAttribute(skills.getSelectedItem().toString());
        int statPoint = manager.getAttribute(stats.getSelectedItem().toString());
        int positiveModifiers = positive.values().stream().mapToInt(Integer::intValue).sum();
        int negativeModifiers = negative.values().stream().mapToInt(Integer::intValue).sum();
        return skillPoint + statPoint + positiveModifiers - negativeModifiers;
    }

    private void updateStats(Object selection, JLabel label) {
        if( selection == null) return;
        label.setText(manager.getAttribute(selection.toString()).toString());
        lbText.setToolTipText(updateTooltipText());
        lbDiceValue.setText(calculateDiceValue().toString());
    }
}
