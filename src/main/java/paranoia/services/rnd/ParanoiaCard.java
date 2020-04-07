package paranoia.services.rnd;

import paranoia.core.ICoreTechPart;
import paranoia.visuals.custom.ParanoiaImage;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static paranoia.Paranoia.getParanoiaResource;

//TODO: do not extend from Image, make it a private attribute instead!!
public abstract class ParanoiaCard extends ParanoiaImage implements ICoreTechPart {

    public static final int ACTION_CARDS = 48;
    public static final int MUTATION_CARDS = 16;
    public static final int EQUIPMENT_CARDS = 22;
    public static final int SECRET_SOCIETY_CARDS = 14;
    public static final int BONUS_DUTY_CARDS = 6;

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
    private static void loadEquipmentCards() throws IOException {
        for (int i = 0; i < EQUIPMENT_CARDS; i++) {
            equipmentCards.add(i, ImageIO.read(new File(getParanoiaResource("cards/equipment/eq" + i + ".png"))));
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

    public static void loadAllCardAssets() throws IOException {
        loadActionCards();
        loadEquipmentCards();
        loadMutationCards();
        loadBonusDutyCards();
        loadSecretSocietyCards();
    }

    public enum CardType {
        ACTION,
        MUTATION,
        EQUIPMENT,
        BONUS_DUTY,
        SECRET_SOCIETY
    }

    int actionOrder;
    private int id;
    private CardType type;
    private String title;
    private String description;

    ParanoiaCard(CardType type, int id, int actionOrder) {
        super(getCardImage(type, id), true);
        setPreferredSize(new Dimension(300,450));
        this.type = type;
        this.actionOrder = actionOrder;
        this.id = id;
        setName(type.name() + id);
    }

    public abstract String toString();

    public int getId() {
        return id;
    }

    public CardType getType(){
        return type;
    }

    private static BufferedImage getCardImage(CardType type, int id) {
        try {
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
        } catch (IndexOutOfBoundsException ignored) {
            return null;
        }
    }
}
