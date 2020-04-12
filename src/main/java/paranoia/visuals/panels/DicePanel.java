package paranoia.visuals.panels;

import paranoia.core.SecurityClearance;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.visuals.animation.ParanoiaDice;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;

public class DicePanel extends JPanel implements ParanoiaListener<ParanoiaDice> {

    public static final int DEFAULT_DICE = 5;
    public static final int DEFAULT_DICE_GAP = 5;
    public static final int DEFAULT_DICE_SIZE = 64;
    private final int size;

    public DicePanel() {
        this(DEFAULT_DICE, DEFAULT_DICE_SIZE);
    }

    public DicePanel(int width, int size) {
        this.size = size;
        setLayout(new GridLayout(0, width, DEFAULT_DICE_GAP, DEFAULT_DICE_GAP));
        setBounds(DEFAULT_DICE_GAP, DEFAULT_DICE_GAP, DEFAULT_DICE_GAP, DEFAULT_DICE_GAP);
        ParanoiaDice placeholder = new ParanoiaDice(SecurityClearance.ULTRAVIOLET, true);
        placeholder.roll(6);
        JPanel visual = placeholder.getVisual();
        visual.setPreferredSize(new Dimension(size, size));
        add(visual);
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaDice> updatedModel) {
        removeAll();
        for (ParanoiaDice die : updatedModel) {
            JPanel visual = die.getVisual();
            visual.setPreferredSize(new Dimension(size, size));
            add(visual);
        }
        updateUI();
    }
}
