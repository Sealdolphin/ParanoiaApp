package paranoia.visuals.mechanics;

import javax.swing.JPanel;

public class TreasonStar extends ParanoiaMechanic {

    public static final int TREASON_STAR_COUNT = 5;

    public TreasonStar() {
        this(true);
    }

    public TreasonStar(Boolean active) {
        super(
            active,
            "mechanics/treasonStar.png",
            "mechanics/treasonStarEmpty.png"
        );
    }

    public static JPanel createTreasonStarPanel(int active) {
        if( active > TREASON_STAR_COUNT) return null;
        return createMechanicPanel(active, TreasonStar.class, TREASON_STAR_COUNT);
    }

}
