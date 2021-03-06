package paranoia.core;

import paranoia.services.hpdmc.CardDecoder;
import paranoia.services.rnd.ActionCard;
import paranoia.services.rnd.BonusDutyCard;
import paranoia.services.rnd.EquipmentCard;
import paranoia.services.rnd.MutationCard;
import paranoia.services.rnd.SecretSocietyCard;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static paranoia.services.rnd.ParanoiaCard.BONUS_DUTY_CARDS;
import static paranoia.services.rnd.ParanoiaCard.SECRET_SOCIETY_CARDS;

public abstract class Computer {

    private static LinkedList<ActionCard> actionCardDatabase;
    private static LinkedList<EquipmentCard> equipmentCardDatabase;
    private static LinkedList<MutationCard> mutationCardDatabase;
    private static LinkedList<SecretSocietyCard> secretSocietyCardDatabase;
    private static LinkedList<BonusDutyCard> bonusDutyCardDatabase;

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
        secretSocietyCardDatabase = CardDecoder.decodeCardsGeneral(SECRET_SOCIETY_CARDS, SecretSocietyCard.class);
        bonusDutyCardDatabase = CardDecoder.decodeCardsGeneral(BONUS_DUTY_CARDS, BonusDutyCard.class);
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

    public static SecretSocietyCard getSecretSocietyCard(int index) {
        return secretSocietyCardDatabase.get(index);
    }

    public static BonusDutyCard getBonusDutyCard(int index) {
        return bonusDutyCardDatabase.get(index);
    }

    public static <T> T randomItem(T[] array) {
        int random = new Random().nextInt(array.length);
        return array[random];
    }

    public static <T> T randomItem(List<T> list) {
        int random = new Random().nextInt(list.size());
        return list.get(random);
    }

    public static boolean coinFlip() {
        return randomItem(new Boolean[]{true, false});
    }
}
