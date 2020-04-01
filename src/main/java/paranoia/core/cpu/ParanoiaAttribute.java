package paranoia.core.cpu;

public class ParanoiaAttribute {

    private String name;
    private int value;

    public ParanoiaAttribute(String name) {
        this(name, 0);
    }

    public ParanoiaAttribute(String name, Integer value){
        this.value = value;
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
