package paranoia.visuals.panels.acpf;

import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.plc.AssetManager;
import paranoia.services.technical.command.ModifyCommand;
import paranoia.visuals.custom.ParanoiaAttributeSpinner;
import paranoia.visuals.mechanics.Moxie;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.LINE_END;
import static java.awt.GridBagConstraints.PAGE_START;
import static java.awt.GridBagConstraints.RELATIVE;
import static java.awt.GridBagConstraints.REMAINDER;
import static paranoia.services.plc.LayoutManager.createGrid;
import static paranoia.visuals.custom.ParanoiaAttributeSpinner.createSkillSpinner;
import static paranoia.visuals.custom.ParanoiaAttributeSpinner.createStatSpinner;

public class ACPFOptimizePage extends JPanel implements ACPFPage {

    private int moxieLeft = MAX_SPENT_CLONE;
    private int cloneLeft = MAX_SPENT_CLONE;
    private static final int MAX_SPENT_MOXIE = 5;
    private static final int MAX_SPENT_CLONE = 5;
    private final List<ParanoiaAttributeSpinner> skillSpinners = new ArrayList<>();
    private final List<ParanoiaAttributeSpinner> statSpinners = new ArrayList<>();
    private final JLabel lbMoxie = new JLabel();
    private final JLabel lbClone = new JLabel();


    public ACPFOptimizePage(ACPFPanel main) {
        setLayout(new BorderLayout());

        lbMoxie.setText(String.valueOf(moxieLeft));
        lbMoxie.setForeground(new Color(50, 177, 118));
        lbMoxie.setFont(AssetManager.getBoldFont(25));
        JLabel lbMoxieText = new JLabel("Moxie left:");
        lbMoxieText.setFont(AssetManager.getFont(18, true, true, true));

        lbClone.setText(String.valueOf(cloneLeft));
        lbClone.setForeground(new Color(50, 177, 118));
        lbClone.setFont(AssetManager.getBoldFont(25));
        JLabel lbCloneText = new JLabel("Clones left:");
        lbCloneText.setFont(AssetManager.getFont(18, true, true, true));

        JButton btnSend = new JButton("Send to Alpha Complex");
        btnSend.addActionListener(e -> sendResponse(main));

        JPanel centerTable = new JPanel(new GridBagLayout());
        centerTable.add(lbCloneText, createGrid().at(0,0,2,1).anchor(LINE_END).get());
        centerTable.add(lbClone, createGrid().at(2,0,2,1).anchor(CENTER).get());
        addStatsTable(centerTable);
        centerTable.add(lbMoxieText, createGrid().at(0,3,2,1).anchor(LINE_END).get());
        centerTable.add(lbMoxie, createGrid().at(2,3,2,1).anchor(CENTER).get());
        addSkillsTable(centerTable);
        centerTable.add(btnSend, createGrid().at(0, RELATIVE, REMAINDER, 1).fill(BOTH).get());

        add(centerTable, BorderLayout.CENTER);
        add(main.createButtonPanel(this, true, false), BorderLayout.SOUTH);
    }

    private void addSkillsTable(JPanel panel) {
        int startY = 4;
        MoxieListener moxieListener = new MoxieListener();
        for (Skill skill : Skill.values()){
            JLabel lbSkill = new JLabel(skill.toString());
            lbSkill.setFont(AssetManager.getFont(20));
            ParanoiaAttributeSpinner spinner = createSkillSpinner(skill.toString(), 0, moxieListener);
            skillSpinners.add(spinner);
            int x = skill.ordinal() / 4;
            int y = skill.ordinal() % 4;
            panel.add(lbSkill, createGrid().at(x, 2 * y + startY).anchor(PAGE_START).get());
            panel.add(spinner, createGrid().at(x, 2 * y + 1 + startY).fill(BOTH).get());
        }
    }

    private void sendResponse(ACPFPanel main) {
        int moxie = Moxie.MOXIE_COUNT - MAX_SPENT_MOXIE + moxieLeft;
        int cloneID = MAX_SPENT_CLONE - cloneLeft;
        main.sendResponse(new ModifyCommand(ModifyCommand.Modifiable.MAX_MOXIE, moxie, null, null));
        main.sendResponse(new ModifyCommand(ModifyCommand.Modifiable.CLONE, cloneID, null, null));
        for (ParanoiaAttributeSpinner skillSpinner : skillSpinners) {
            main.sendResponse(new ModifyCommand(
                ModifyCommand.Modifiable.SKILL,
                (Integer) skillSpinner.getValue(),
                skillSpinner.getLabel(),
                null
            ));
        }
        for (ParanoiaAttributeSpinner statSpinner : statSpinners) {
            main.sendResponse(new ModifyCommand(
                ModifyCommand.Modifiable.STAT,
                (Integer) statSpinner.getValue(),
                statSpinner.getLabel(),
                null
            ));
        }
        main.lockPanel();
    }

    private void addStatsTable(JPanel panel) {
        int startY = 1;
        CloneListener cloneListener = new CloneListener();
        for (Stat stat : Stat.values()) {
            JLabel lbStat = new JLabel(stat.toString());
            lbStat.setFont(AssetManager.getBoldFont(20));
            ParanoiaAttributeSpinner spinner = createStatSpinner(stat.toString(), 0, cloneListener);
            statSpinners.add(spinner);
            panel.add(lbStat, createGrid(5,5,5,5).at(stat.ordinal(), startY).anchor(PAGE_START).get());
            panel.add(spinner, createGrid(5,5,5,5).at(stat.ordinal(), startY + 1).fill(BOTH).get());
        }
    }

    @Override
    public boolean validatePage() {
        return false;
    }

    private class MoxieListener implements ParanoiaAttributeSpinner.ParanoiaSpinnerListener {

        @Override
        public void stepUp() {
            moxieLeft--;
            if(moxieLeft <= 0) {
                skillSpinners.forEach(spinner -> spinner.setLimit(true));
            }
            lbMoxie.setText(String.valueOf(moxieLeft));
        }

        @Override
        public void stepDown() {
            moxieLeft++;
            skillSpinners.forEach(spinner -> spinner.setLimit(false));
            lbMoxie.setText(String.valueOf(moxieLeft));
        }
    }

    private class CloneListener implements ParanoiaAttributeSpinner.ParanoiaSpinnerListener {

        @Override
        public void stepUp() {
            cloneLeft--;
            if(cloneLeft <= 0) {
                statSpinners.forEach(spinner -> spinner.setLimit(true));
            }
            lbClone.setText(String.valueOf(cloneLeft));
        }

        @Override
        public void stepDown() {
            cloneLeft++;
            statSpinners.forEach(spinner -> spinner.setLimit(false));
            lbClone.setText(String.valueOf(cloneLeft));
        }
    }
}
