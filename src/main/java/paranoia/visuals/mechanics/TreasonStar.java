package paranoia.visuals.mechanics;

import paranoia.core.ICoreTechPart;
import paranoia.services.plc.ResourceManager;
import paranoia.visuals.ComponentName;

import javax.swing.JPanel;

public class TreasonStar extends ParanoiaMechanic implements ICoreTechPart {

    public static final int TREASON_STAR_COUNT = 5;

    public TreasonStar(Boolean active, Integer size, String order) {
        super(
            active,
            ResourceManager.getResource(ResourceManager.ResourceIcon.TREASON_STAR),
            ResourceManager.getResource(ResourceManager.ResourceIcon.TREASON_STAR_OFF),
            size,
            ComponentName.TREASON_STAR + order
        );
    }

    public static JPanel createTreasonStarPanel(int active, int id) {
        return createTreasonStarPanel(active, id, DEFAULT_SIZE);
    }

    public static JPanel createTreasonStarPanel(int active, int id, int size) {
        if( active > TREASON_STAR_COUNT) return null;
        return createMechanicPanel(active, TreasonStar.class, TREASON_STAR_COUNT, size, id);
    }

    @Override
    public ComponentName getPanelName() {
        return ComponentName.TREASON_STAR_PANEL;
    }

    @Override
    public JPanel getVisual() {
        return this;
    }
}
