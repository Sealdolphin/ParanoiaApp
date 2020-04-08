package paranoia.core;

import paranoia.visuals.clones.ClonePanel;
import paranoia.visuals.clones.SelfPanel;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

public class Clone implements Cloneable, ICoreTechPart {
    private static final String UNKNOWN = "UNKNOWN";
    private String name;
    private int treasonStars;
    private int moxie;
    private int crossedOutMoxie;
    private int injury;
    private int xpPoints;
    private String sectorName;
    private String gender;
    private SecurityClearance clearance;
    private int cloneID;
    private BufferedImage profilePicture;

    private Map<String, String> info = new LinkedHashMap<>();

    public Clone(
        String name,
        String sector,
        SecurityClearance clearance,
        int treasonStars,
        BufferedImage image
    ) {
        this.name = name;
        this.sectorName = sector;
        this.clearance = clearance;
        this.treasonStars = treasonStars;
        this.moxie = 7;
        this.crossedOutMoxie = 0;
        this.injury = 0;
        this.gender = "MALE";
        this.cloneID = 1;
        this.profilePicture = image;

        info.put("CIVIC ZEAL", UNKNOWN);
        info.put("MARKET VALUE", UNKNOWN);
        info.put("XP POINTS", Integer.toString(xpPoints));
    }

    @Override
    public JPanel getVisual() {
        return new ClonePanel(
            profilePicture,
            getFullName(),
            info,
            clearance,
            treasonStars,
            injury
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
            crossedOutMoxie
        );
    }

    private String getFullName() {
        return name + "-" + clearance.getShort() + "-" + sectorName + "-" + cloneID;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Clone c = (Clone) super.clone();
        c.cloneID = c.cloneID + 1;
        return c;
    }
}
