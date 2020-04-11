package paranoia;

import paranoia.core.Clone;
import paranoia.core.Computer;
import paranoia.core.SecurityClearance;
import paranoia.core.cpu.Mission;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.services.hpdmc.manager.AttributeManager;
import paranoia.services.hpdmc.manager.MissionManager;
import paranoia.services.hpdmc.manager.TroubleShooterManager;
import paranoia.services.plc.ResourceManager;
import paranoia.services.rnd.ParanoiaCard;
import paranoia.visuals.ComponentName;
import paranoia.visuals.messages.RollMessage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static paranoia.core.cpu.ParanoiaAttribute.getSkill;
import static paranoia.core.cpu.ParanoiaAttribute.getStat;

/**
 * The game itself
 */
public class Paranoia {

    public static final Color PARANOIA_BACKGROUND = new Color(255, 255, 255);

    public static void main(String[] args) {

        //TODO: remove later
        BufferedImage img0 = null;
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        BufferedImage img3 = null;
        BufferedImage img4 = null;
        //Show Picture
        try {
            ResourceManager.loadResources();    //not this!! This is important
            ParanoiaCard.loadAllCardAssets();
            Computer.initDatabase();

            img0 = ImageIO.read(new File(getParanoiaResource("clones/clone0.png")));
            img1 = ImageIO.read(new File(getParanoiaResource("clones/clone1.png")));
            img2 = ImageIO.read(new File(getParanoiaResource("clones/clone2.png")));
            img3 = ImageIO.read(new File(getParanoiaResource("clones/clone3.png")));
            img4 = ImageIO.read(new File(getParanoiaResource("clones/clone4.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Clone clone0 = new Clone("SYD", "ULS", SecurityClearance.INFRARED, "MALE", 0, img0);
        Clone clone1 = new Clone("CHRIS", "AFG", SecurityClearance.ORANGE, "MALE" ,1, img1);
        Clone clone2 = new Clone("ROZ", "HYT", SecurityClearance.RED, "MALE", 2, img2);
        Clone clone3 = new Clone("JOE", "RTE", SecurityClearance.BLUE, "MALE", 3, img3);
        Clone clone4 = new Clone("CARA", "RLY", SecurityClearance.YELLOW, "MALE", 4, img4);

        ControlUnit cpu = new ControlUnit(clone4);
        JFrame coreTech = cpu.getVisuals();
        coreTech.setExtendedState(Frame.MAXIMIZED_BOTH);

        //TroubleShooters
        cpu.updateAsset(clone0, ComponentName.TROUBLESHOOTER_PANEL);
        cpu.updateAsset(clone1, ComponentName.TROUBLESHOOTER_PANEL);
        cpu.updateAsset(clone2, ComponentName.TROUBLESHOOTER_PANEL);
        cpu.updateAsset(clone3, ComponentName.TROUBLESHOOTER_PANEL);
        //TroubleShooter actions
        TroubleShooterManager manager =
            (TroubleShooterManager) cpu.getManager(ComponentName.TROUBLESHOOTER_PANEL);
        manager.setInjury(0, 2);
        manager.setTreasonStars(0,3);
        manager.giveXPPoints(0,320);

        manager.setInjury(1, 1);
        manager.setTreasonStars(1,2);
        manager.giveXPPoints(1, 430);

        manager.setInjury(2, 3);
        manager.setTreasonStars(2,1);
        manager.giveXPPoints(2,153);

        //Self actions
        TroubleShooterManager selfManager =
            (TroubleShooterManager) cpu.getManager(ComponentName.SELF_PANEL);
        selfManager.setInjury(1);
        selfManager.setTreasonStars(2);
        selfManager.giveXPPoints(4321);
        selfManager.setMoxie(6);
        selfManager.crossOutMoxie();
        selfManager.setSecurityClearance(SecurityClearance.VIOLET);

        //Action cards
        cpu.updateAsset(Computer.getActionCard(3), ComponentName.ACTION_CARD_PANEL);
        cpu.updateAsset(Computer.getActionCard(7), ComponentName.ACTION_CARD_PANEL);
        cpu.updateAsset(Computer.getActionCard(11), ComponentName.ACTION_CARD_PANEL);
        cpu.updateAsset(Computer.getActionCard(25), ComponentName.ACTION_CARD_PANEL);
        //Equipment cards
        cpu.updateAsset(Computer.getEquipmentCard(0), ComponentName.EQUIPMENT_CARD_PANEL);
        cpu.updateAsset(Computer.getEquipmentCard(6), ComponentName.EQUIPMENT_CARD_PANEL);
        cpu.updateAsset(Computer.getEquipmentCard(18), ComponentName.EQUIPMENT_CARD_PANEL);
        cpu.updateAsset(Computer.getEquipmentCard(3), ComponentName.EQUIPMENT_CARD_PANEL);
        //Misc cards
        cpu.updateAsset(Computer.getMutationCard(3), ComponentName.MISC_CARD_PANEL);
        cpu.updateAsset(Computer.getSecretSocietyCard(3), ComponentName.MISC_CARD_PANEL);
        cpu.updateAsset(Computer.getBonusDutyCard(3), ComponentName.MISC_CARD_PANEL);
        //Mission
        cpu.updateAsset(new Mission(0, "Secure the package", ""), ComponentName.MISSION_PANEL);
        cpu.updateAsset(new Mission(1, "Disable terrorist bomb", ""), ComponentName.MISSION_PANEL);
        cpu.updateAsset(new Mission(2, "Don't let the Commies take the package",
            "", Mission.MissionPriority.OPTIONAL), ComponentName.MISSION_PANEL);
        //Setup missions
        ((MissionManager)cpu.getManager(ComponentName.MISSION_PANEL))
            .updateMissionStatus(0, Mission.MissionStatus.COMPLETED);
        ((MissionManager)cpu.getManager(ComponentName.MISSION_PANEL))
            .updateMissionStatus(2, Mission.MissionStatus.FAILED);
        //Set up attributes
        setUpSkillsNStats(cpu);

        coreTech.setVisible(true);

        //TODO: remove later
        Map<String, Integer> positive = new HashMap<>();
        Map<String, Integer> negative = new HashMap<>();

        positive.put("The GM likes your style", 1);
        positive.put("Action card", 2);
        negative.put("Injury", 2);

        RollMessage message = new RollMessage(
                clone0,
            (AttributeManager) cpu.getManager(ComponentName.SKILL_PANEL),
                Stat.BRAINS, true,
                Skill.ALPHA_COMPLEX, true,
                positive, negative,
                "Please roll with..."
        );

//        message.setVisible(true);

    }

    private static void setUpSkillsNStats(ControlUnit cpu) {
        cpu.updateAsset(getStat(Stat.VIOLENCE, 3), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getStat(Stat.BRAINS, 1), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getStat(Stat.CHUTZPAH, 1), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getStat(Stat.MECHANICS, 2), ComponentName.SKILL_PANEL);

        cpu.updateAsset(getSkill(Skill.GUNS, 3), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getSkill(Skill.MELEE, 4), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getSkill(Skill.THROW, -2), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getSkill(Skill.ALPHA_COMPLEX, 1), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getSkill(Skill.BLUFF, 5), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getSkill(Skill.CHARM, -5), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getSkill(Skill.INTIMIDATE, -1), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getSkill(Skill.ENGINEER, -3), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getSkill(Skill.PROGRAM, 2), ComponentName.SKILL_PANEL);
        cpu.updateAsset(getSkill(Skill.DEMOLITIONS, -4), ComponentName.SKILL_PANEL);
    }

    public static String getParanoiaResource(String path) throws IOException {
        URL url = Paranoia.class.getClassLoader().getResource(path);
        if(url != null) {
            return url.getFile();
        } else {
            throw new IOException("Could not load file: " + path);
        }
    }

}
