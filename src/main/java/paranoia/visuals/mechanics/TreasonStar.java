package paranoia.visuals.mechanics;

import paranoia.visuals.custom.ParanoiaImage;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import static paranoia.Paranoia.getParanoiaResource;

public class TreasonStar extends ParanoiaImage {

    private Boolean active;

    public TreasonStar() {
        this(true);
    }

    public TreasonStar(Boolean active) {
        super(null);
        setPreferredSize(new Dimension(64,64));
        this.active = active;
        try { updateImage();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateImage() throws IOException {
        if(active)
            image = ImageIO.read(new File(getParanoiaResource("mechanics/treasonStar.png")));
        else
            image = ImageIO.read(new File(getParanoiaResource("mechanics/treasonStarEmpty.png")));
    }

    public Boolean isActive() {
        return active;
    }

    public void activate(Boolean active) {
        this.active = active;
        try { updateImage();
        } catch (IOException ignored) { }
    }
}
