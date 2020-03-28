package paranoia.visuals.rnd;

import paranoia.visuals.custom.ParanoiaImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static paranoia.Paranoia.getParanoiaResource;

public abstract class ParanoiaCard extends ParanoiaImage {

    public static final int ACTION_CARDS = 4;
    public static final int MUTATION_CARDS = 0;
    public static final int EQUIPMENT_CARDS = 4;
    public static final int SECRET_SOCIETY_CARDS = 0;
    public static final int BONUS_DUTY_CARDS = 0;

    private static List<BufferedImage> actionCards = new ArrayList<>();
    private static List<BufferedImage> equipmentCards = new ArrayList<>();
    private static List<BufferedImage> secretSocietyCards = new ArrayList<>();
    private static List<BufferedImage> mutationCards = new ArrayList<>();
    private static List<BufferedImage> bonusDutyCards = new ArrayList<>();

    private static void loadActionCards() throws IOException {
        for (int i = 0; i < ACTION_CARDS; i++) {
            actionCards.add(i, ImageIO.read(new File(getParanoiaResource("cards/action/a" + i + ".png"))));
        }
    }
    private static void loadMutationCards() throws IOException {
        for (int i = 0; i < MUTATION_CARDS; i++) {
            mutationCards.add(i, ImageIO.read(new File(getParanoiaResource("cards/mutation/m" + i + ".png"))));
        }
    }
    private static void loadBonusDutyCards() throws IOException {
        for (int i = 0; i < BONUS_DUTY_CARDS; i++) {
            bonusDutyCards.add(i, ImageIO.read(new File(getParanoiaResource("cards/bonus_duty/bd" + i + ".png"))));
        }
    }
    private static void loadSecretSocietyCards() throws IOException {
        for (int i = 0; i < SECRET_SOCIETY_CARDS; i++) {
            secretSocietyCards.add(i, ImageIO.read(new File(getParanoiaResource("cards/secret_society/ss" + i + ".png"))));
        }
    }
    private static void loadEquipmentCards() throws IOException {
        for (int i = 0; i < EQUIPMENT_CARDS; i++) {
            equipmentCards.add(i, ImageIO.read(new File(getParanoiaResource("cards/equipment/eq" + i + ".png"))));
        }
    }

    public static void loadAllCardAssets() throws IOException {
        loadActionCards();
        loadBonusDutyCards();
        loadEquipmentCards();
        loadMutationCards();
        loadSecretSocietyCards();
    }

    public enum CardType {
        ACTION,
        MUTATION,
        EQUIPMENT,
        BONUS_DUTY,
        SECRET_SOCIETY
    }

    private CardType type;

    ParanoiaCard(CardType type, int id) {
        super(getCardImage(type, id), true);
        this.type = type;
    }

    private static BufferedImage getCardImage(CardType type, int id) {
        switch (type) {
            case ACTION:
                if (id >= ACTION_CARDS) return null;
                return actionCards.get(id);
            case MUTATION:
                if (id >= MUTATION_CARDS) return null;
                return mutationCards.get(id);
            case EQUIPMENT:
                if (id >= EQUIPMENT_CARDS) return null;
                return equipmentCards.get(id);
            case BONUS_DUTY:
                if (id >= BONUS_DUTY_CARDS) return null;
                return bonusDutyCards.get(id);
            case SECRET_SOCIETY:
                if (id >= SECRET_SOCIETY_CARDS) return null;
                return secretSocietyCards.get(id);
            default:
                return null;
        }
    }
}
