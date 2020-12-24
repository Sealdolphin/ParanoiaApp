package paranoia.core;

import javax.swing.JPanel;

public class ParanoiaPlayer implements ICoreTechPart {

    private final String name;

    public ParanoiaPlayer(String name) {
        this.name = name;
    }

    @Override
    public JPanel getVisual() {
        return null;
    }

    public String getName() {
        return name;
    }
}
