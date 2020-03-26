package paranoia.visuals.mechanics;

import javax.swing.JPanel;

public class Injury extends ParanoiaMechanic {

    public static final int INJURY_COUNT = 3;

    public Injury() {
        this(false, 32);
    }

    public Injury(Boolean active) {
        this(active, 32);
    }

    public Injury(Boolean active, int size) {
        super(
            active,
            "mechanics/injuryActive.png",
            "mechanics/injury.png",
            size
        );
        setSize(64);
    }

    public static JPanel createInjuryPanel(int active) {
        if( active > INJURY_COUNT) return null;
        return createMechanicPanel(active, Injury.class, INJURY_COUNT);
    }
}
