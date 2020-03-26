package paranoia.core.cpu;

public enum Skill{
    ATHLETICS,
    GUNS,
    MELEE,
    THROW,
    SCIENCE,
    PSYCHOLOGY,
    BUREAUCRACY,
    ALPHA_COMPLEX,
    BLUFF,
    CHARM,
    INTIMIDATE,
    STEALTH,
    OPERATE,
    ENGINEER,
    PROGRAM,
    DEMOLITIONS;

    private int value = 0;

    public Integer getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
