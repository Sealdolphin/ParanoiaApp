package paranoia.core.cpu;

public abstract class ParanoiaModifier {

    private String name;
    private int value = 0;

    public ParanoiaModifier(String name){
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getName() {
        return name;
    }

}
