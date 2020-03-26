package paranoia.visuals.mechanics;

import javax.swing.JPanel;

public class Moxie extends ParanoiaMechanic {

    public static final int MOXIE_COUNT = 8;
    private Boolean disabled = false;

    public Moxie() {
        this(true, 32);
    }

    public Moxie(Boolean active) {
        this(active, 32);
    }

    public Moxie(Boolean active, int size) {
        super(
            active,
            "mechanics/moxieFilled.png",
            "mechanics/moxie.png",
            size
        );
    }

    public void crossOut() {
        disabled = true;
        activate(false);
    }

    @Override
    public void activate(Boolean active) {
        if(disabled) imagePathDisabled = "mechanics/moxieCrossed.png";
        super.activate(active && !disabled);
    }

    public static JPanel createMoxiePanel(int active, int crossedOut) {
        if(active > MOXIE_COUNT) return null;
        JPanel moxiePanel = createMechanicPanel(active, Moxie.class, MOXIE_COUNT);
        //Cross out moxies
        for (int i = 0; i < crossedOut ; i++) {
            Moxie m = (Moxie) moxiePanel.getComponent(MOXIE_COUNT - i - 1);
            m.crossOut();
        }
        return moxiePanel;
    }

}
