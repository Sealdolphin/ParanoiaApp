package paranoia.visuals.mechanics;

import paranoia.services.plc.ResourceManager;
import paranoia.visuals.ComponentName;

import javax.swing.JPanel;

public class Injury extends ParanoiaMechanic {

    public static final int INJURY_COUNT = 3;

    public Injury(Boolean active, Integer size, Integer order) {
        super(
            active,
            ResourceManager.getResource(ResourceManager.ResourceIcon.INJURY),
            ResourceManager.getResource(ResourceManager.ResourceIcon.INJURY_OFF),
            size,
            ComponentName.INJURY  + order.toString()
        );
    }

    public static JPanel createInjuryPanel(int active) {
        return createInjuryPanel(active, DEFAULT_SIZE);
    }

    public static JPanel createInjuryPanel(int active, int size) {
        if( active > INJURY_COUNT) return null;
        return createMechanicPanel(active, Injury.class, INJURY_COUNT, size);
    }

    @Override
    public ComponentName getPanelName() {
        return ComponentName.INJURY_PANEL;
    }
}
