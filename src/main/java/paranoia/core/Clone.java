package paranoia.core;

import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.visuals.clones.ClonePanel;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clone implements Cloneable {
    private static final String UNKNOWN = "UNKNOWN";
    private String name;
    private int treasonStars;
    private int injury;
    private String sectorName;
    private String gender;
    private SecurityClearance clearance;
    private int cloneID;
    private BufferedImage profilePicture;

    private Map<String, String> info = new HashMap<>();
    private List<Skill> skills = Arrays.asList(Skill.values());
    private List<Stat> stats = Arrays.asList(Stat.values());

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
        this.injury = 0;
        this.gender = "MALE";
        this.cloneID = 1;
        this.profilePicture = image;
        //TODO: do not need it
        setUpSkillsNStats();

        info.put("CIVIC ZEAL", UNKNOWN);
        info.put("MARKET VALUE", UNKNOWN);
    }

    private void setUpSkillsNStats() {
        setStat(Stat.VIOLENCE, 3);
        setStat(Stat.BRAINS, 1);
        setStat(Stat.CHUTZPAH, 1);
        setStat(Stat.MECHANICS, 2);

        setSkill(Skill.GUNS, 3);
        setSkill(Skill.MELEE, 4);
        setSkill(Skill.THROW, -2);
        setSkill(Skill.ALPHA_COMPLEX, 1);
        setSkill(Skill.BLUFF, 5);
        setSkill(Skill.CHARM, -5);
        setSkill(Skill.INTIMIDATE, -1);
        setSkill(Skill.ENGINEER, -3);
        setSkill(Skill.PROGRAM, 2);
        setSkill(Skill.DEMOLITIONS, -4);
    }

    public void setSkill(Skill skill, int value) {
        skills.get(skill.ordinal()).setValue(value);
    }

    public void setStat(Stat stat, int value) {
        stats.get(stat.ordinal()).setValue(value);
    }

    public Skill[] getSkills() {
        return skills.toArray(new Skill[0]);
    }

    public Stat[] getStats() {
        return stats.toArray(new Stat[0]);
    }

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

    private String getFullName() {
        return name + "-" + clearance.getShort() + "-" + sectorName + "-" + cloneID;
    }
}
