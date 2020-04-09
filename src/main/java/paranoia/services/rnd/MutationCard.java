package paranoia.services.rnd;

import javax.swing.JPanel;

public class MutationCard extends ParanoiaCard {

    public MutationCard(int id, int actionOrder) {
        super(CardType.MUTATION, id, actionOrder);
    }

    @Override
    public String toString() {
        return "Mutation: id: " + getId() + " | order: " + actionOrder;
    }

    @Override
    public JPanel getVisual() {
        return this;
    }
}
