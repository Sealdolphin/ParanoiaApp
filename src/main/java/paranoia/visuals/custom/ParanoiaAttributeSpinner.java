package paranoia.visuals.custom;

import paranoia.services.plc.AssetManager;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;

import static paranoia.services.plc.AssetManager.defaultButtonBackground;

public class ParanoiaAttributeSpinner extends JSpinner implements ChangeListener {

    public static final int MAX_STAT_FINALIZATION_VALUE = 3;
    public static final int MAX_SKILL_FINALIZATION_VALUE = 5;
    private final int defaultMaxValue;
    private final int startValue;
    private final String label;

    public static ParanoiaAttributeSpinner createStatSpinner(String label, int stat, ParanoiaSpinnerListener listener) {
        return new ParanoiaAttributeSpinner(label, stat, stat, Math.max(stat, MAX_STAT_FINALIZATION_VALUE), listener);
    }

    public static ParanoiaAttributeSpinner createSkillSpinner(String label, int skill, ParanoiaSpinnerListener listener) {
        return new ParanoiaAttributeSpinner(label, skill, skill, MAX_SKILL_FINALIZATION_VALUE, listener);
    }

    private ParanoiaAttributeSpinner(String label, int start, int minValue, int maxValue, ParanoiaSpinnerListener listener) {
        setModel(new ParanoiaNumberModel(start, minValue, maxValue,1, listener));
        setEditor(new DefaultEditor(this));
        ((DefaultEditor) getEditor()).getTextField().setFont(AssetManager.getFont(15));
        addChangeListener(this);
        this.defaultMaxValue = maxValue;
        this.startValue = start;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLimit(boolean enabled) {
        SpinnerNumberModel model = (SpinnerNumberModel) getModel();
        if(enabled) {
            model.setMaximum((int) model.getValue());
        } else {
            model.setMaximum(defaultMaxValue);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int value = (int) getValue();
        ((DefaultEditor) getEditor()).getTextField().setBackground(
            value == startValue ? defaultButtonBackground : new Color(118, 186, 129)
        );
    }

    private static class ParanoiaNumberModel extends SpinnerNumberModel {

        private final ParanoiaSpinnerListener listener;

        private ParanoiaNumberModel(int start, int minValue, int maxValue, int stepSize,
            ParanoiaSpinnerListener listener) {
            super(start, minValue, maxValue, stepSize);
            this.listener = listener;
        }

        @Override
        public void setValue(Object value) {
            int old = (int) getValue();
            int intValue = (int) value;
            if( old < intValue ) {
                listener.stepUp();
            } else if ( old > intValue ) {
                listener.stepDown();
            }
            super.setValue(value);
        }
    }

    public interface ParanoiaSpinnerListener {

        void stepUp();

        void stepDown();

    }

}
