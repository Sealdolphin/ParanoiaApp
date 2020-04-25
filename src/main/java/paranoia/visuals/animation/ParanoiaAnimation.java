package paranoia.visuals.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ParanoiaAnimation {

    private final BufferedImage[] frames;
    private BufferedImage activeFrame = null;
    private int frameID = 0;

    public ParanoiaAnimation(BufferedImage image, int width) {
        int numberOfFrames = image.getWidth() / width;
        frames = new BufferedImage[numberOfFrames];

        for (int i = 0; i < frames.length; i++) {
            BufferedImage crop = image.getSubimage(i * width,0 , width, image.getHeight());
            BufferedImage copy = new BufferedImage(crop.getWidth(), crop.getHeight(), BufferedImage.TYPE_INT_RGB);
            copy.createGraphics().drawImage(crop, 0,0, null);
            frames[i] = copy;
        }
    }

    public ParanoiaAnimation(ArrayList<BufferedImage> images) {
        this.frames = new BufferedImage[images.size()];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = images.get(i);
        }
    }

    public BufferedImage getFrame(int i){
        return frames[i % frames.length];
    }

    public BufferedImage getActiveFrame() {
        return activeFrame;
    }

    public void animate() {
        activeFrame = frames[frameID++];
        if(frameID > frames.length) {
            frameID = frameID % frames.length;
        }
    }

}
