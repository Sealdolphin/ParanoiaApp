package paranoia.visuals.rnd;

public class ActionCard extends ParanoiaCard {

    private Boolean reaction;
    private Boolean action;
    private int actionOrder;

    public ActionCard(int id, Boolean action, Boolean reaction, int actionOrder) {
        super(CardType.ACTION, id);
        this.action = action;
        this.reaction = reaction;
        this.actionOrder = actionOrder;
    }
}
