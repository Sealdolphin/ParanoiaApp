package paranoia.visuals.rnd;

import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;

public class EquipmentCard extends ParanoiaCard {

    public enum EquipmentSize {
        SMALL,
        MEDIUM,
        LARGE,
        OVERSIZE
    }

    private int level;
    private Stat statModifier = null;
    private Skill skillModifier = null;
    private EquipmentSize size;

    public EquipmentCard(int id, int actionOrder, Skill modifier, int level, EquipmentSize size) {
        super(CardType.EQUIPMENT, id, actionOrder);
        this.skillModifier = modifier;
        this.level = level;
        this.size = size;
    }

    public EquipmentCard(int id, int actionOrder, Stat modifier, int level, EquipmentSize size) {
        super(CardType.EQUIPMENT, id, actionOrder);
        this.statModifier = modifier;
        this.level = level;
        this.size = size;
    }

    @Override
    public String toString() {
        return "EQ: id: " + getId() + " | order: " + actionOrder + " | size: " + size + " | lvl: " + level + " | modifier " + statModifier;
    }
}
