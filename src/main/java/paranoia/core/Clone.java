package paranoia.core;

import paranoia.visuals.clones.ClonePanel;
import paranoia.visuals.clones.SelfPanel;
import paranoia.visuals.mechanics.Moxie;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

public class Clone implements Cloneable, ICoreTechPart {
    private static final String UNKNOWN = "UNKNOWN";
    private final String name;
    private int treasonStars = 0;
    private int moxie = Moxie.MOXIE_COUNT;
    private int crossedOutMoxie = 0;
    private int injury = 0;
    private int xpPoints = 0;
    private final String sectorName;
    private final String gender;
    private SecurityClearance clearance;
    private int cloneID = 1;
    private final BufferedImage profilePicture;
    private final int playerId;

    private Map<String, String> info = new LinkedHashMap<>();

    public Clone(
        String name,
        String sector,
        SecurityClearance clearance,
        String gender,
        int playerId,
        BufferedImage image
    ) {
        this.name = name;
        this.sectorName = sector;
        this.clearance = clearance;
        this.gender = gender;
        this.profilePicture = image;
        this.playerId = playerId;

        info.put("CIVIC ZEAL", UNKNOWN);
        info.put("MARKET VALUE", UNKNOWN);
        info.put("XP POINTS", Integer.toString(xpPoints));
    }

    @Override
    public JPanel getVisual() {
        info.put("XP POINTS", Integer.toString(xpPoints));
        return new ClonePanel(
            profilePicture,
            getFullName(),
            info,
            clearance,
            treasonStars,
            injury,
            playerId
        );
    }

    public JPanel getSelfVisual() {
        return new SelfPanel(
            profilePicture,
            getFullName(),
            clearance,
            xpPoints,
            treasonStars,
            injury,
            moxie,
            crossedOutMoxie,
            playerId
        );
    }

    public int getPlayerId() {
        return playerId;
    }

    public void giveXpPoints(int xp) {
        xpPoints += xp;
    }

    public void setMoxie(int amount) {
        moxie = amount;
    }

    public void setTreasonStars(int amount) {
        treasonStars = amount;
    }

    public void setClearance(SecurityClearance clearance) {
        this.clearance = clearance;
    }

    public void setInjury(int injury) {
        this.injury = injury;
    }

    private String getFullName() {
        return name + "-" + clearance.getShort() + "-" + sectorName + "-" + cloneID;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Clone c = (Clone) super.clone();
        c.cloneID = cloneID + 1;
        return c;
    }

    public void crossOut() {
        crossedOutMoxie++;
    }
}
