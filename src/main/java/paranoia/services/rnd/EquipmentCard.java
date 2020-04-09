package paranoia.services.rnd;

import paranoia.core.cpu.ParanoiaAttribute;

import javax.swing.JPanel;

public class EquipmentCard extends ParanoiaCard {

    public enum EquipmentSize {
        SMALL,
        MEDIUM,
        LARGE,
        OVERSIZE
    }

    private final int level;
    private final ParanoiaAttribute modifier;
    private final EquipmentSize size;

    public EquipmentCard(int id, int actionOrder, ParanoiaAttribute modifier, int level, EquipmentSize size) {
        super(CardType.EQUIPMENT, id, actionOrder);
        this.modifier = modifier;
        this.level = level;
        this.size = size;
    }

    @Override
    public String toString() {
        return "EQ: id: " + getId() + " | order: " + actionOrder + " | size: " +
            size + " | lvl: " + level + " | modifier " + modifier.getName() + " (" + modifier.getValue() + ")";
    }

    @Override
    public JPanel getVisual() {
        return this;
    }
}
