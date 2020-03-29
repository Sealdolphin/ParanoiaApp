package paranoia.core.cpu;

import static paranoia.core.cpu.Stat.BRAINS;
import static paranoia.core.cpu.Stat.CHUTZPAH;
import static paranoia.core.cpu.Stat.MECHANICS;
import static paranoia.core.cpu.Stat.VIOLENCE;

public class Skill extends ParanoiaModifier{

    public static final Skill ATHLETICS = new Skill("Athletics", VIOLENCE);
    public static final Skill GUNS = new Skill("Guns", VIOLENCE);
    public static final Skill MELEE = new Skill("Melee", VIOLENCE);
    public static final Skill THROW = new Skill("Throw", VIOLENCE);

    public static final Skill SCIENCE = new Skill("Science", BRAINS);
    public static final Skill PSYCHOLOGY = new Skill("Psychology", BRAINS);
    public static final Skill BUREAUCRACY = new Skill("Bureaucracy", BRAINS);
    public static final Skill ALPHA_COMPLEX = new Skill("Alpha Complex", BRAINS);

    public static final Skill BLUFF = new Skill("Bluff", CHUTZPAH);
    public static final Skill CHARM = new Skill("Charm", CHUTZPAH);
    public static final Skill INTIMIDATE = new Skill("Intimidate", CHUTZPAH);
    public static final Skill STEALTH = new Skill("Stalth", CHUTZPAH);

    public static final Skill OPERATE = new Skill("Operate", MECHANICS);
    public static final Skill ENGINEER = new Skill("Engineer", MECHANICS);
    public static final Skill PROGRAM = new Skill("Program", MECHANICS);
    public static final Skill DEMOLITIONS = new Skill("Demolitions", MECHANICS);

    private Stat parentStat;

    public Skill(String name, Stat parentStat) {
        super(name);
        this.parentStat = parentStat;
    }

    public Stat getParent() {
        return parentStat;
    }

}
