package paranoia;

import paranoia.core.Computer;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.services.plc.ResourceManager;
import paranoia.services.rnd.ParanoiaCard;
import paranoia.services.technical.CommandParser;
import paranoia.services.technical.networking.Network;
import paranoia.visuals.ComponentName;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import static paranoia.core.cpu.ParanoiaAttribute.getSkill;
import static paranoia.core.cpu.ParanoiaAttribute.getStat;

/**
 * The game itself
 */
public class Paranoia {

    public static final Color PARANOIA_BACKGROUND = new Color(255, 255, 255);
    public static final String version = "version v.alpha";

    public static void main(String[] args) {

        try {
            ResourceManager.loadResources();    //not this!! This is important
            ParanoiaCard.loadAllCardAssets();
            Computer.initDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new ControlUnit(new Network(new CommandParser()));
    }

    //TODO: temporary
    public static void setUpSkillsNStats(ControlUnit cpu) {
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
