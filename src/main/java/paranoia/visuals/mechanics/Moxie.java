package paranoia.visuals.mechanics;

import paranoia.services.hpdmc.ResourceManager;
import paranoia.visuals.ComponentName;

import javax.swing.JPanel;
import java.awt.GridLayout;

public class Moxie extends ParanoiaMechanic {

    public static final int MOXIE_COUNT = 8;
    private Boolean disabled = false;

    public Moxie(Boolean active, Integer size, Integer order) {
        super(
            active,
            ResourceManager.getResource(ResourceManager.ResourceIcon.MOXIE),
            ResourceManager.getResource(ResourceManager.ResourceIcon.MOXIE_OFF),
            size,
            ComponentName.MOXIE + order.toString()
        );
    }

    public void crossOut() {
        disabled = true;
        activate(false);
    }

    @Override
    public void activate(Boolean active) {
        if(disabled) imgDisabled = ResourceManager.getResource(ResourceManager.ResourceIcon.MOXIE_CROSSED);
        super.activate(active && !disabled);
    }

    @Override
    public ComponentName getPanelName() {
        return ComponentName.MOXIE_PANEL;
    }

    public static JPanel createMoxiePanel(int active, int crossedOut) {
        return createMoxiePanel(active, crossedOut, DEFAULT_SIZE);
    }

    public static JPanel createMoxiePanel(int active, int crossedOut, int size) {
        if(active > MOXIE_COUNT) return null;
        JPanel moxiePanel = createMechanicPanel(active, Moxie.class, MOXIE_COUNT, size);
        moxiePanel.setLayout(new GridLayout(2, 4, 2, 2));
        //Cross out moxies
        for (int i = 0; i < crossedOut ; i++) {
            Moxie m = (Moxie) moxiePanel.getComponent(MOXIE_COUNT - i - 1);
            m.crossOut();
        }
        return moxiePanel;
    }

}
