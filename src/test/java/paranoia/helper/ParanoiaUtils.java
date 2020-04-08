package paranoia.helper;

import paranoia.core.Computer;
import paranoia.core.cpu.Mission;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.services.rnd.ParanoiaCard;
import paranoia.visuals.ComponentName;

import java.util.Random;

public class ParanoiaUtils {

    public static final int testStars = 2;
    public static final int testCards = 4;

    //Cards
    public int[] actionCards;
    public int[] equipmentCards;
    public int mutationCard;
    public int secretSocietyCard;
    public int bonusDutyCard;
    //Missions
    public Mission[] testMissions;

    public ParanoiaUtils(ControlUnit cpu) {

        //Setup Cards
        actionCards = new int[testCards];
        equipmentCards = new int[testCards];
        mutationCard = new Random().nextInt(ParanoiaCard.MUTATION_CARDS);
        secretSocietyCard = new Random().nextInt(ParanoiaCard.SECRET_SOCIETY_CARDS);
        bonusDutyCard = new Random().nextInt(ParanoiaCard.BONUS_DUTY_CARDS);

        for (int i = 0; i < testCards; i++) {
            actionCards[i] = new Random().nextInt(ParanoiaCard.ACTION_CARDS);
            equipmentCards[i] = new Random().nextInt(ParanoiaCard.EQUIPMENT_CARDS);
            cpu.updateAsset(Computer.getActionCard(actionCards[i]),
                ComponentName.ACTION_CARD_PANEL);
            cpu.updateAsset(Computer.getEquipmentCard(equipmentCards[i]),
                ComponentName.EQUIPMENT_CARD_PANEL);
        }

        cpu.updateAsset(Computer.getMutationCard(mutationCard),
            ComponentName.MISC_CARD_PANEL);
        cpu.updateAsset(Computer.getSecretSocietyCard(secretSocietyCard),
            ComponentName.MISC_CARD_PANEL);
        cpu.updateAsset(Computer.getBonusDutyCard(bonusDutyCard),
            ComponentName.MISC_CARD_PANEL);

        //Missions
        testMissions = new Mission[4];
        for (int i = 0; i < testMissions.length; i++) {
            Mission.MissionPriority priority = i == testMissions.length - 1 ?
                Mission.MissionPriority.REQUIRED : Mission.MissionPriority.OPTIONAL;
            testMissions[i] = new Mission(
                new Random().nextInt(), "Test Mission",
                "This is a test", priority
            );
            cpu.updateAsset(testMissions[i], ComponentName.MISSION_PANEL);
        }
    }

}
