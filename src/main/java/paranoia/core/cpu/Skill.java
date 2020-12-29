package paranoia.core.cpu;

/**
 * Skill enumerations in TL order (Top-to-bottom, Left-to-Right)
 */
public enum Skill {

    ATHLETICS("Athletics", Stat.VIOLENCE),
    SCIENCE("Science", Stat.BRAINS),
    BLUFF("Bluff", Stat.CHUTZPAH),
    OPERATE("Operate", Stat.MECHANICS),

    GUNS("Guns", Stat.VIOLENCE),
    PSYCHOLOGY("Psychology", Stat.BRAINS),
    CHARM("Charm", Stat.CHUTZPAH),
    ENGINEER("Engineer", Stat.MECHANICS),

    MELEE("Melee", Stat.VIOLENCE),
    BUREAUCRACY("Bureaucracy", Stat.BRAINS),
    INTIMIDATE("Intimidate", Stat.CHUTZPAH),
    PROGRAM("Program", Stat.MECHANICS),

    THROW("Throw", Stat.VIOLENCE),
    ALPHA_COMPLEX("Alpha Complex", Stat.BRAINS),
    STEALTH("Stealth", Stat.CHUTZPAH),
    DEMOLITIONS("Demolitions", Stat.MECHANICS);

    private final String name;
    private final Stat parentStat;

    Skill(String name, Stat parentStat) {
        this.name = name;
        this.parentStat = parentStat;
    }

    public Stat getParent() {
        return parentStat;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Skill getSkillByName(String name) {
        Skill target = null;
        for (Skill skill : values()) {
            if(skill.name.equals(name)){
                target = skill;
                break;
            }
        }
        return target;
    }

    public ParanoiaAttribute createAttribute(int value) {
        return new ParanoiaAttribute(name, value);
    }
}
