package paranoia.services.rnd;

public class MutationCard extends ParanoiaCard {

    public MutationCard(int id, int actionOrder) {
        super(CardType.MUTATION, id, actionOrder);
    }

    @Override
    public String toString() {
        return "Mutation: id: " + getId() + " | order: " + actionOrder;
    }
}
