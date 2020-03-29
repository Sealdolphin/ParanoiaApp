package paranoia.core;

import paranoia.services.hpdmc.CardDecoder;
import paranoia.visuals.rnd.ActionCard;
import paranoia.visuals.rnd.EquipmentCard;
import paranoia.visuals.rnd.MutationCard;

import java.io.IOException;
import java.util.LinkedList;

public abstract class Computer {

    private static LinkedList<ActionCard> actionCardDatabase;
    private static LinkedList<EquipmentCard> equipmentCardDatabase;
    private static LinkedList<MutationCard> mutationCardDatabase;

    public static void initDatabase() throws IOException {
        CardDecoder actionCardDecoder = new CardDecoder("data/action.csv");
        CardDecoder equipmentCardDecoder = new CardDecoder("data/equipment.csv");
        CardDecoder mutationCardDecoder = new CardDecoder("data/mutation.csv");

        actionCardDecoder.readDatabase();
        equipmentCardDecoder.readDatabase();
        mutationCardDecoder.readDatabase();

        actionCardDatabase = actionCardDecoder.decodeActionCards();
        equipmentCardDatabase = equipmentCardDecoder.decodeEquipmentCards();
        mutationCardDatabase = mutationCardDecoder.decodeMutationCards();
    }

    public static ActionCard getActionCard(int index) {
        return actionCardDatabase.get(index);
    }

    public static EquipmentCard getEquipmentCard(int index) {
        return equipmentCardDatabase.get(index);
    }

    public static MutationCard getMutationCard(int index) {
        return mutationCardDatabase.get(index);
    }

}
