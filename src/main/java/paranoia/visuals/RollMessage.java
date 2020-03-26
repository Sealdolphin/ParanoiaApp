package paranoia.visuals;


import paranoia.core.Clone;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.visuals.custom.ParanoiaButton;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.CENTER;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class RollMessage extends JDialog {

    private Map<String, Integer> positive;
    private Map<String, Integer> negative;
    private JComboBox<Skill> skills;
    private JComboBox<Stat> stats;
    private Clone clone;

    public RollMessage(
            Clone clone,
            Stat defaultStat,
            Boolean allowChangeSkill,
            Skill defaultSkill,
            Boolean allowChangeStat,
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
        this.clone = clone;

        Map<TextAttribute, Integer> fontAttributes = new HashMap<>();
        fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        Font boldFont20 = new Font("Arial", Font.BOLD, 20);
        Font boldFont30 = new Font("Arial", Font.PLAIN, 30);
        Font boldFont15 = new Font("Arial", Font.BOLD, 15).deriveFont(fontAttributes);

        //Properties
        JTextPane lbText = new JTextPane();
        JLabel lbMessage = new JLabel(message);
        lbMessage.setFont(boldFont30);
        JLabel lbDice = new JLabel("NODE");
        lbDice.setFont(boldFont15);
        //Dice value
        JLabel lbDiceValue = new JLabel();
        lbDiceValue.setFont(boldFont20);
        //Button
        ParanoiaButton btnRoll = new ParanoiaButton("Roll");
        btnRoll.setFont(boldFont20);
        btnRoll.setBackground(new Color(166,0, 6));
        btnRoll.setHoverBG(new Color(166, 70, 73));
        btnRoll.setPressedBG(new Color(255, 126, 136));
        btnRoll.setForeground(Color.WHITE);
        btnRoll.addActionListener( event -> dispose());
        //Selected Skill
        JLabel lbSkill = new JLabel();
        lbSkill.setFont(boldFont20);
        skills = new JComboBox<>(clone.getSkills());
        skills.setFont(boldFont20);
        skills.setEnabled(allowChangeSkill);
        skills.addActionListener( event -> {
            Skill selected = (Skill) skills.getSelectedItem();
            assert selected != null;
            lbSkill.setText(selected.getValue().toString());
            lbText.setToolTipText(updateTooltipText());
            lbDiceValue.setText(calculateDiceValue().toString());
        });
        //Selected Stat
        JLabel lbStat = new JLabel();
        lbStat.setFont(boldFont20);
        stats = new JComboBox<>(clone.getStats());
        stats.setFont(boldFont20);
        stats.setEnabled(allowChangeStat);
        stats.addActionListener( event -> {
            Stat selected = (Stat) stats.getSelectedItem();
            assert selected != null;
            lbStat.setText(selected.getValue().toString());
            lbText.setToolTipText(updateTooltipText());
            lbDiceValue.setText(calculateDiceValue().toString());
        });
        //Set initial selection
        skills.setSelectedItem(defaultSkill);
        stats.setSelectedItem(defaultStat);
        //Modifiers text
        lbText.setContentType("text/html");
        lbText.setText(updateModifierText());
        lbText.setToolTipText(updateTooltipText());
        lbText.setEditable(false);
        lbText.setOpaque(false);

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

        //Assemble layout
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private String updateTooltipText() {
        int skillPoint = clone.getSkills()[skills.getSelectedIndex()].getValue();
        int statPoint = clone.getStats()[stats.getSelectedIndex()].getValue();

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
         int skillPoint = clone.getSkills()[skills.getSelectedIndex()].getValue();
         int statPoint = clone.getStats()[stats.getSelectedIndex()].getValue();
         int positiveModifiers = positive.values().stream().mapToInt(Integer::intValue).sum();
         int negativeModifiers = negative.values().stream().mapToInt(Integer::intValue).sum();
         return skillPoint + statPoint + positiveModifiers - negativeModifiers;
    }
}