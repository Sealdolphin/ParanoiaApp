package paranoia.core.cpu;

public enum Skill {

    ATHLETICS("Athletics", Stat.VIOLENCE),
    GUNS("Guns", Stat.VIOLENCE),
    MELEE("Melee", Stat.VIOLENCE),
    THROW("Throw", Stat.VIOLENCE),

    SCIENCE("Science", Stat.BRAINS),
    PSYCHOLOGY("Psychology", Stat.BRAINS),
    BUREAUCRACY("Bureaucracy", Stat.BRAINS),
    ALPHA_COMPLEX("Alpha Complex", Stat.BRAINS),

    BLUFF("Bluff", Stat.CHUTZPAH),
    CHARM("Charm", Stat.CHUTZPAH),
    INTIMIDATE("Intimidate", Stat.CHUTZPAH),
    STEALTH("Stealth", Stat.CHUTZPAH),

    OPERATE("Operate", Stat.MECHANICS),
    ENGINEER("Engineer", Stat.MECHANICS),
    PROGRAM("Program", Stat.MECHANICS),
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
}
