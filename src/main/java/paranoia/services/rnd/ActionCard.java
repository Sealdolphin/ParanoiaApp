package paranoia.services.rnd;

import javax.swing.JPanel;

public class ActionCard extends ParanoiaCard {

    private final Boolean reaction;
    private final Boolean action;

    public ActionCard(int id, Boolean action, Boolean reaction, int actionOrder) {
        super(CardType.ACTION, id, actionOrder);
        this.action = action;
        this.reaction = reaction;
    }

    @Override
    public String toString() {
        return "ACTION: id: " + getId() + " | order: " + actionOrder + " | reaction: " + reaction;
    }

    @Override
    public JPanel getVisual() {
        return this;
    }
}
