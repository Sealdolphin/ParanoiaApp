package paranoia.core.cpu;

public enum Stat {

    VIOLENCE("Violence"),
    BRAINS("Brains"),
    CHUTZPAH("Chutzpah"),
    MECHANICS("Mechanics");

    private final String name;

    Stat(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public ParanoiaAttribute createAttribute(int value) {
        return new ParanoiaAttribute(name, value);
    }
}
