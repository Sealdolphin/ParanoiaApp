package paranoia.visuals.rnd;

public class MutationCard extends ParanoiaCard {

    private int actionOrder;

    public MutationCard(int id, int actionOrder) {
        super(CardType.MUTATION, id);
        this.actionOrder = actionOrder;
    }
}
