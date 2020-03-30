package paranoia.core.cpu;

public enum Stat {

    VIOLENCE("Violence"),
    BRAINS("Brains"),
    CHUTZPAH("Chutzpah"),
    MECHANICS("Mechanics");

    private String name;

    Stat(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
