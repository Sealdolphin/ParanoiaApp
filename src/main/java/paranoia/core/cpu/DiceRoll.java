package paranoia.core.cpu;

public class DiceRoll {

    private final int success;
    private final boolean computer;

    public DiceRoll(int success, boolean computer) {
        this.success = success;
        this.computer = computer;
    }

    public boolean isComputer() {
        return computer;
    }

    public int getSuccess() {
        return success;
    }
}
