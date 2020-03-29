package paranoia.core.cpu;

public class Skill extends ParanoiaModifier{

    private Stat parentStat;

    public Skill(String name, Stat parentStat) {
        super(name);
        this.parentStat = parentStat;
    }

    public Stat getParent() {
        return parentStat;
    }

}
