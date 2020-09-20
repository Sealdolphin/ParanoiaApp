package paranoia.visuals.animation;

import daiv.ui.visuals.ParanoiaImage;
import paranoia.Paranoia;
import paranoia.core.ICoreTechPart;
import paranoia.core.SecurityClearance;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ParanoiaDice implements ICoreTechPart {

    private final boolean computerDice;
    private final ParanoiaAnimation diceAnimation;
    private int value;

    public ParanoiaDice(SecurityClearance color) {
        this(color, false);
    }

    public ParanoiaDice(SecurityClearance color, boolean computerDice) {
        this.computerDice = computerDice;
        BufferedImage dice = new BufferedImage(1,1, Image.SCALE_SMOOTH);
        try {
            dice = ImageIO.read(new File(
                Paranoia.getParanoiaResource("other/ParanoiaCube" + color.name() + ".png")
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParanoiaAnimation frames = new ParanoiaAnimation(dice, 128);
        ArrayList<BufferedImage> diceFrames = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            diceFrames.add(frames.getFrame(i));
        }
        if(computerDice) {
            diceFrames.add(frames.getFrame(6));
        } else {
            diceFrames.add(frames.getFrame(5));
        }

        diceAnimation = new ParanoiaAnimation(diceFrames);
        rollDice();
    }

    public void rollDice() {
        value = new Random().nextInt(6) + 1;
    }

    public void roll(int value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return value > 4 && !isComputer();
    }

    public boolean isComputer() {
        return computerDice && value == 6;
    }

    @Override
    public JPanel getVisual() {
        return new ParanoiaImage(diceAnimation.getFrame(value - 1));
    }
}
