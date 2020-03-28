package paranoia.visuals.mechanics;

import javax.swing.JPanel;

public class TreasonStar extends ParanoiaMechanic {

    public static final int TREASON_STAR_COUNT = 5;

    public TreasonStar() {
        this(true, DEFAULT_SIZE);
    }

    public TreasonStar(Boolean active) {
        this(active, DEFAULT_SIZE);
    }

    public TreasonStar(Boolean active, Integer size) {
        super(
            active,
            "mechanics/treasonStar.png",
            "mechanics/treasonStarEmpty.png",
            size
        );
    }

    public static JPanel createTreasonStarPanel(int active) {
        return createTreasonStarPanel(active, DEFAULT_SIZE);
    }

    public static JPanel createTreasonStarPanel(int active, int size) {
        if( active > TREASON_STAR_COUNT) return null;
        return createMechanicPanel(active, TreasonStar.class, TREASON_STAR_COUNT, size);
    }

}
