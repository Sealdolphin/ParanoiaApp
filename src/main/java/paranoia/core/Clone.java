package paranoia.core;

import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.visuals.clones.ClonePanel;
import paranoia.visuals.clones.SelfPanel;
import paranoia.visuals.rnd.ParanoiaCard;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private List<Skill> skills = Arrays.asList(Skill.values());
    private List<Stat> stats = Arrays.asList(Stat.values());
    private List<ParanoiaCard> cards = new ArrayList<>();

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
        //TODO: remove later
        setUpSkillsNStats();

        info.put("CIVIC ZEAL", UNKNOWN);
        info.put("MARKET VALUE", UNKNOWN);
        info.put("XP POINTS", Integer.toString(xpPoints));
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

    public void addCard(ParanoiaCard card) {
        cards.add(card);
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

    public List<ParanoiaCard> getCards(ParanoiaCard.CardType cardType) {
        return cards.stream().filter(card -> card.getType().equals(cardType)).collect(Collectors.toList());
    }

    public List<ParanoiaCard> getMiscCards() {
        return  cards.stream().filter( card -> card.getType().equals(ParanoiaCard.CardType.SECRET_SOCIETY) ||
            card.getType().equals(ParanoiaCard.CardType.MUTATION) ||
            card.getType().equals(ParanoiaCard.CardType.BONUS_DUTY)).collect(Collectors.toList());
    }
}
