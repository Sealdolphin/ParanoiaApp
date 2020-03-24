package paranoia.core;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Clone {
    private static final String UNKNOWN = "UNKNOWN";
    private String name;
    private int treasonStars;
    private String sectorName;
    private String gender;
    private SecurityClearance clearance;
    private int cloneID;

    private Map<String, String> info = new HashMap<>();

    public Clone(String name, String sector, SecurityClearance clearance, int treasonStars) {
        this.name = name;
        this.sectorName = sector;
        this.clearance = clearance;
        this.treasonStars = treasonStars;
        this.gender = "MALE";
        this.cloneID = 1;

        info.put("CIVIC ZEAL", UNKNOWN);
        info.put("MARKET VALUE", UNKNOWN);
    }

    public JPanel getVisual() {
        JPanel visual = new JPanel();
        visual.setLayout(new BoxLayout(visual, BoxLayout.PAGE_AXIS));
        visual.setSize(new Dimension(200,200));
        visual.add(new JLabel("/// CITIZEN: " + getFullName()));
        info.forEach( (key, value) -> {
            visual.add(new JLabel("/// " + key + ": " + value));
        });

        return visual;
    }

    private String getFullName() {
        return name + "-" + clearance.getShort() + "-" + sectorName + "-" + cloneID;
    }
}
