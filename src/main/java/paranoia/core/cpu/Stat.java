package paranoia.core.cpu;

public enum Stat {
    VIOLENCE,
    BRAINS,
    CHUTZPAH,
    MECHANICS;

    private int value;

    public Integer getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
