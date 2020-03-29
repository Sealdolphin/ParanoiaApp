package paranoia.visuals.mechanics;

import paranoia.services.hpdmc.ResourceManager;

import javax.swing.JPanel;

public class TreasonStar extends ParanoiaMechanic {

    public static final int TREASON_STAR_COUNT = 5;

    public TreasonStar(Boolean active, Integer size) {
        super(
            active,
            ResourceManager.getResource(ResourceManager.ResourceIcon.TREASON_STAR),
            ResourceManager.getResource(ResourceManager.ResourceIcon.TREASON_STAR_OFF),
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
