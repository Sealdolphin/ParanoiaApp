package paranoia.core.cpu;

public class Stat extends ParanoiaModifier{

    public static final Stat VIOLENCE = new Stat("Violence");
    public static final Stat BRAINS = new Stat("Brains");
    public static final Stat CHUTZPAH = new Stat("Chutzpah");
    public static final Stat MECHANICS = new Stat("Mechanics");

    public Stat(String name) {
        super(name);
    }
}
