package paranoia.visuals;


import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.util.List;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.CENTER;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class RollMessage extends JDialog {

    private List<Integer> positive;
    private List<Integer> negative;
    private JComboBox<Skill> skills;
    private JComboBox<Stat> stats;

    public RollMessage(
            Stat defaultStat,
            Boolean allowChangeSkill,
            Skill defaultSkill,
            Boolean allowChangeStat,
            List<Integer> positiveModifiers,
            List<Integer> negativeModifiers,
            String message
    ) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        //Set up values
        this.positive = positiveModifiers;
        this.negative = negativeModifiers;

        skills = new JComboBox<>(Skill.values());
        skills.setEnabled(allowChangeSkill);
        skills.setSelectedItem(defaultSkill);

        stats = new JComboBox<>(Stat.values());
        stats.setEnabled(allowChangeStat);
        stats.setSelectedItem(defaultStat);

        //Set Layout
        JLabel lbMessage = new JLabel("Please roll with...");
        JLabel lbDice = new JLabel("D.I.C.E.");
        JLabel lbText = new JLabel("Some modifier info");
        JLabel lbSkill = new JLabel(defaultSkill.getValue().toString());
        JLabel lbStat = new JLabel(defaultStat.getValue().toString());
        JLabel lbDiceValue = new JLabel(calculateDiceValue().toString());
        JButton btnRoll = new JButton("Roll");

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
                            .addComponent(lbDiceValue)
                    )
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
        setResizable(false);
    }

    private Integer calculateDiceValue() {
         int skillPoint = Skill.values()[skills.getSelectedIndex()].getValue();
         int statPoint = Stat.values()[stats.getSelectedIndex()].getValue();
         int positiveModifiers = positive.stream().mapToInt(Integer::intValue).sum();
         int negativeModifiers = negative.stream().mapToInt(Integer::intValue).sum();
         return skillPoint + statPoint + positiveModifiers - negativeModifiers;
    }
}
