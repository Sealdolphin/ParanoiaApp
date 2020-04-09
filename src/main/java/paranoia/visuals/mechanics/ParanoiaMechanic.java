package paranoia.visuals.mechanics;

import paranoia.visuals.ComponentName;
import paranoia.visuals.custom.ParanoiaImage;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import static paranoia.Paranoia.PARANOIA_BACKGROUND;

public abstract class ParanoiaMechanic extends ParanoiaImage {

    private Boolean active;
    private final BufferedImage imgActive;
    BufferedImage imgDisabled;
    static final int DEFAULT_SIZE = 32;

    ParanoiaMechanic(Boolean active, BufferedImage imgActive, BufferedImage imgDisabled, int size, String name) {
        super(imgActive);
        setPreferredSize(new Dimension(size,size));
        this.active = active;
        this.imgActive = imgActive;
        this.imgDisabled = imgDisabled;
        updateImage();
        setName(name);
    }

    public void setSize(int size){
        setPreferredSize(new Dimension(size, size));
    }

    public Boolean isActive() {
        return active;
    }

    public void activate(Boolean active) {
        this.active = active;
        updateImage();
    }

    public abstract ComponentName getPanelName();

    private void updateImage() {
        if(active)
            image = imgActive;
        else
            image = imgDisabled;
    }

    static <M extends ParanoiaMechanic> JPanel createMechanicPanel(
        int activated,
        Class<M> mechanic,
        int count,
        int size
    ) {
        JPanel staticPanel = new JPanel();
        staticPanel.setBackground(PARANOIA_BACKGROUND);
        staticPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        if(count < activated) return staticPanel;
        try {
            int order = 0;
            staticPanel.setName(mechanic
                .getConstructor(Boolean.class, Integer.class, Integer.class)
                .newInstance(true, size, 0).getPanelName().name());
            for (int i = 0; i < activated; i++) {
                staticPanel.add(mechanic
                    .getConstructor(Boolean.class, Integer.class, Integer.class)
                    .newInstance(true, size, order++)
                );
            }
            for (int i = 0; i < count - activated; i++) {
                staticPanel.add(mechanic
                    .getConstructor(Boolean.class, Integer.class, Integer.class)
                    .newInstance(false, size, order++)
                );
            }
        } catch (IllegalAccessException | InstantiationException |
            NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return staticPanel;
    }

}
