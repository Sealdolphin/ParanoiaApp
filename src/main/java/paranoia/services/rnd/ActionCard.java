package paranoia.services.rnd;

public class ActionCard extends ParanoiaCard {

    private Boolean reaction;
    private Boolean action;

    public ActionCard(int id, Boolean action, Boolean reaction, int actionOrder) {
        super(CardType.ACTION, id, actionOrder);
        this.action = action;
        this.reaction = reaction;
    }

    @Override
    public String toString() {
        return "ACTION: id: " + getId() + " | order: " + actionOrder + " | reaction: " + reaction;
    }
}
