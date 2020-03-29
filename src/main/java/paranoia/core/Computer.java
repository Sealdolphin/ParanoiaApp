package paranoia.core;

import paranoia.services.hpdmc.CardDecoder;
import paranoia.visuals.rnd.ActionCard;
import paranoia.visuals.rnd.EquipmentCard;
import paranoia.visuals.rnd.MutationCard;

import java.io.IOException;
import java.util.LinkedList;

import static paranoia.Paranoia.getParanoiaResource;

public abstract class Computer {

    private static LinkedList<ActionCard> actionCardDatabase;
    private static LinkedList<EquipmentCard> equipmentCardDatabase;
    private static LinkedList<MutationCard> mutationCardDatabase;

    public static void initDatabase() throws IOException {
        CardDecoder actionCardDecoder = new CardDecoder(getParanoiaResource("data/action.csv"));
        CardDecoder equipmentCardDecoder = new CardDecoder(getParanoiaResource("data/equipment.csv"));
        CardDecoder mutationCardDecoder = new CardDecoder(getParanoiaResource("data/mutation.csv"));

        actionCardDatabase = actionCardDecoder.decodeActionCards();
        equipmentCardDatabase = equipmentCardDecoder.decodeEquipmentCards();
        mutationCardDatabase = mutationCardDecoder.decodeMutationCards();
    }

    public static ActionCard getActionCard(int index) {
        return actionCardDatabase.get(index);
    }

}
