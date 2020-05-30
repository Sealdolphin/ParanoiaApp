package paranoia.visuals.panels.acpf;

import paranoia.services.plc.LayoutManager;
import paranoia.visuals.custom.ParanoiaAttributeSpinner;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class ACPFOptimizePage extends JPanel implements ACPFPage {

    private int moxieLeft = 5;
    private int cloneLeft = 5;
    private List<ParanoiaAttributeSpinner> spinners = new ArrayList<>();


    public ACPFOptimizePage(ACPFPanel main) {
        setLayout(new BorderLayout());

        /*

        | *********** |
        |   ~~:  0    | label + value (2 labels)
        | ----------- |
        | ~+[]- ~+[]- | label + spinner (with value)
        ::::REPEAT:::::

        E: 4 panels in CENTER (BoxLayout)

         */
        MoxieListener l = new MoxieListener();

        spinners.add(ParanoiaAttributeSpinner.createSkillSpinner(1, l));
        spinners.add(ParanoiaAttributeSpinner.createSkillSpinner(3, l));
        spinners.add(ParanoiaAttributeSpinner.createSkillSpinner(-2, l));
        spinners.add(ParanoiaAttributeSpinner.createSkillSpinner(-1, l));
        spinners.add(ParanoiaAttributeSpinner.createSkillSpinner(5, l));
        add(LayoutManager.panelOf(
            spinners.toArray(new Component[0]), BoxLayout.PAGE_AXIS
        ), BorderLayout.CENTER);
    }

    private JPanel createSkillsTable() {

        return null;
    }

    private JPanel createStatsTable() {


        return null;
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
                spinners.forEach(spinner -> spinner.setLimit(true));
            }
            System.out.println(moxieLeft + " moxie left");
        }

        @Override
        public void stepDown() {
            moxieLeft++;
            spinners.forEach(spinner -> spinner.setLimit(false));
            System.out.println(moxieLeft + " moxie left");
        }
    }
}
