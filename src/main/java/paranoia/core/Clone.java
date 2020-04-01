package paranoia.core;

import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.visuals.clones.ClonePanel;
import paranoia.visuals.clones.SelfPanel;
import paranoia.visuals.panels.SkillPanel;
import paranoia.visuals.rnd.ParanoiaCard;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Map<String, ParanoiaAttribute> attributes = new HashMap<>();
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

        //Create Skills and Stats
        for (Stat stat : Stat.values())
            attributes.put(stat.toString(), new ParanoiaAttribute(stat.toString(), 0));

        for (Skill skill : Skill.values())
            attributes.put(skill.toString(), new ParanoiaAttribute(skill.toString(), 0));

        //TODO: remove later
        setUpSkillsNStats();

        info.put("CIVIC ZEAL", UNKNOWN);
        info.put("MARKET VALUE", UNKNOWN);
        info.put("XP POINTS", Integer.toString(xpPoints));
    }

    private void setUpSkillsNStats() {
        setAttribute(Stat.VIOLENCE.toString(), 3);
        setAttribute(Stat.BRAINS.toString(), 1);
        setAttribute(Stat.CHUTZPAH.toString(), 1);
        setAttribute(Stat.MECHANICS.toString(), 2);

        setAttribute(Skill.GUNS.toString(), 3);
        setAttribute(Skill.MELEE.toString(), 4);
        setAttribute(Skill.THROW.toString(), -2);
        setAttribute(Skill.ALPHA_COMPLEX.toString(), 1);
        setAttribute(Skill.BLUFF.toString(), 5);
        setAttribute(Skill.CHARM.toString(), -5);
        setAttribute(Skill.INTIMIDATE.toString(), -1);
        setAttribute(Skill.ENGINEER.toString(), -3);
        setAttribute(Skill.PROGRAM.toString(), 2);
        setAttribute(Skill.DEMOLITIONS.toString(), -4);
    }

    public void setAttribute(String name, int value) {
        attributes.get(name).setValue(value);
    }

    public Integer getAttribute(String name) {
        return attributes.get(name).getValue();
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

    public SkillPanel getSkillPanel() {
        return new SkillPanel(attributes);
    }
}
