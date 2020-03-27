package paranoia.visuals.mechanics;

import javax.swing.JPanel;

public class TreasonStar extends ParanoiaMechanic {

    public static final int TREASON_STAR_COUNT = 5;

    public TreasonStar() {
        this(true, 16);
    }

    public TreasonStar(Boolean active) {
        this(active, 16);
    }

    public TreasonStar(Boolean active, int size) {
        super(
            active,
            "mechanics/treasonStar.png",
            "mechanics/treasonStarEmpty.png",
            size
        );
    }

    public static JPanel createTreasonStarPanel(int active) {
        if( active > TREASON_STAR_COUNT) return null;
        return createMechanicPanel(active, TreasonStar.class, TREASON_STAR_COUNT);
    }

}
