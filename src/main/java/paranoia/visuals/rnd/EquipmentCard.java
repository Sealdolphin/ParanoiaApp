package paranoia.visuals.rnd;

import paranoia.core.cpu.ParanoiaModifier;
public class EquipmentCard extends ParanoiaCard {

    public enum EquipmentSize {
        SMALL,
        MEDIUM,
        LARGE,
        OVERSIZE
    }

    private int level;
    private ParanoiaModifier modifier;
    private EquipmentSize size;

    public EquipmentCard(int id, int actionOrder, ParanoiaModifier modifier, int level, EquipmentSize size) {
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
}
