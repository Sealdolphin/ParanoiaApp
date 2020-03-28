package paranoia.visuals.mechanics;

import javax.swing.JPanel;

public class Injury extends ParanoiaMechanic {

    public static final int INJURY_COUNT = 3;

    public Injury() {
        this(false, DEFAULT_SIZE);
    }

    public Injury(Boolean active) {
        this(active, DEFAULT_SIZE);
    }

    public Injury(Boolean active, Integer size) {
        super(
            active,
            "mechanics/injuryActive.png",
            "mechanics/injury.png",
            size
        );
    }

    public static JPanel createInjuryPanel(int active) {
        return createInjuryPanel(active, DEFAULT_SIZE);
    }

    public static JPanel createInjuryPanel(int active, int size) {
        if( active > INJURY_COUNT) return null;
        return createMechanicPanel(active, Injury.class, INJURY_COUNT, size);
    }
}
