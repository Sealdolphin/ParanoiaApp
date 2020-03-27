package paranoia.visuals.mechanics;

import paranoia.visuals.custom.ParanoiaImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static paranoia.Paranoia.getParanoiaResource;

public abstract class ParanoiaMechanic extends ParanoiaImage {

    private Boolean active;
    private String imagePath;
    private int size;
    String imagePathDisabled;

    ParanoiaMechanic(Boolean active, String imagePath, String imagePathDisabled, int size) {
        super(null);
        setPreferredSize(new Dimension(size,size));
        this.size = size;
        this.active = active;
        this.imagePath = imagePath;
        this.imagePathDisabled = imagePathDisabled;
        try { updateImage();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setSize(int size){
        this.size = size;
        setPreferredSize(new Dimension(size, size));
    }

    public Boolean isActive() {
        return active;
    }

    public void activate(Boolean active) {
        this.active = active;
        try { updateImage();
        } catch (IOException ignored) { }
    }

    private void updateImage() throws IOException {
        if(active)
            image = ImageIO.read(new File(getParanoiaResource(imagePath)));
        else
            image = ImageIO.read(new File(getParanoiaResource(imagePathDisabled)));
    }

    static <M extends ParanoiaMechanic> JPanel createMechanicPanel(
        int activated,
        Class<M> mechanic,
        int count
    ) {
        JPanel staticPanel = new JPanel();
        staticPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        if(count < activated) return staticPanel;
        try {
            for (int i = 0; i < activated; i++) {
                staticPanel.add(mechanic
                    .getConstructor(Boolean.class)
                    .newInstance(true)
                );
            }
            for (int i = 0; i < count - activated; i++) {
                staticPanel.add(mechanic
                    .getConstructor(Boolean.class)
                    .newInstance(false)
                );
            }
        } catch (IllegalAccessException | InstantiationException |
            NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return staticPanel;
    }

}
