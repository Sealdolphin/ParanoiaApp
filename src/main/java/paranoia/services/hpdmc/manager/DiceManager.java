package paranoia.services.hpdmc.manager;

import paranoia.core.SecurityClearance;
import paranoia.core.cpu.DiceRoll;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.visuals.animation.ParanoiaDice;
import paranoia.visuals.panels.DicePanel;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiceManager {

    private final int node;
    private final ParanoiaDice[] dice;
    private final List<ParanoiaListener<ParanoiaDice>> diceListeners = new ArrayList<>();

    public DiceManager(int node, SecurityClearance color) {
        this.node = node;
        dice = new ParanoiaDice[Math.abs(node) + 1];
        for (int i = 0; i < dice.length - 1; i++) {
            dice[i] = new ParanoiaDice(color);
        }
        dice[dice.length - 1] = new ParanoiaDice(color, true);
    }

    public JPanel getDicePanel(int width, int size) {
        DicePanel dicePanel = new DicePanel(width, size);
        diceListeners.add(dicePanel);
        return dicePanel;
    }

    public JPanel getDicePanel() {
        DicePanel dicePanel = new DicePanel();
        diceListeners.add(dicePanel);
        return dicePanel;
    }

    public void roll() {
        for (ParanoiaDice die : dice) {
            die.rollDice();
        }
        diceListeners.forEach(l -> l.updateVisualDataChange(Arrays.asList(dice)));
    }

    public DiceRoll getResult() {
        int sucess = 0;
        boolean computer = false;
        for (ParanoiaDice die : dice) {
            if(die.isSuccess()) {
                sucess++;
            } else if(die.isComputer()) {
                computer = true;
            } else if(node < 0) {
                sucess--;
            }
        }
        return new DiceRoll(sucess, computer);
    }

}
