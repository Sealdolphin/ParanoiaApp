package paranoia.core;

import paranoia.services.hpdmc.CardDecoder;
import paranoia.visuals.rnd.ActionCard;
import paranoia.visuals.rnd.BonusDutyCard;
import paranoia.visuals.rnd.EquipmentCard;
import paranoia.visuals.rnd.MutationCard;
import paranoia.visuals.rnd.SecretSocietyCard;

import java.io.IOException;
import java.util.LinkedList;

import static paranoia.visuals.rnd.ParanoiaCard.BONUS_DUTY_CARDS;
import static paranoia.visuals.rnd.ParanoiaCard.SECRET_SOCIETY_CARDS;

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
}
