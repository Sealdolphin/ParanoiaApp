package paranoia.visuals.panels;

import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.visuals.ComponentName;
import paranoia.visuals.animation.ParanoiaDice;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;

public class DicePanel extends JPanel implements ParanoiaListener<ParanoiaDice> {

    public static final int DEFAULT_DICE = 5;
    public static final int DEFAULT_DICE_GAP = 5;
    public static final int DEFAULT_DICE_SIZE = 64;
    private JPanel dicePanel;
    private final int size;
    private final int width;

    public DicePanel() {
        this(DEFAULT_DICE, DEFAULT_DICE_SIZE);
    }

    public DicePanel(int width, int size) {
        this.size = size;
        this.width = width;
        recreateDicePanel(width);
    }

    private void recreateDicePanel(int width) {
        dicePanel = new JPanel();
        dicePanel.setLayout(new GridLayout(0, width, DEFAULT_DICE_GAP, DEFAULT_DICE_GAP));
        dicePanel.setBounds(DEFAULT_DICE_GAP, DEFAULT_DICE_GAP, DEFAULT_DICE_GAP, DEFAULT_DICE_GAP);
        dicePanel.setName(ComponentName.DICE_PANEL.name());
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaDice> updatedModel) {
        removeAll();
        recreateDicePanel(width);
        for (ParanoiaDice die : updatedModel) {
            JPanel visual = die.getVisual();
            visual.setPreferredSize(new Dimension(size, size));
            dicePanel.add(visual);
        }
        add(dicePanel);
    }
}
