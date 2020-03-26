package paranoia.visuals.mechanics;

import javax.swing.JPanel;

public class Injury extends ParanoiaMechanic {

    public static final int INJURY_COUNT = 3;

    public Injury() {
        this(false);
    }

    Injury(Boolean active) {
        super(
            active,
            "mechanics/injury.png",
            "mechanics/injuryActive.png"
        );
    }

    public static JPanel createInjuryPanel(int active) {
        if( active > INJURY_COUNT) return null;
        return createMechanicPanel(active, Injury.class, INJURY_COUNT);
    }
}
