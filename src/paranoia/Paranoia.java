package paranoia;

import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ControlUnit;
import paranoia.visuals.RollMessage;

import java.util.Collections;

/**
 * The game itself
 */
public class Paranoia {

    public static void main(String[] args) {
        ControlUnit cpu = new ControlUnit();
        //CerebrealCoretech coreTech = new CerebrealCoretech();
        //coreTech.setVisible(true);

        Stat stat = Stat.BRAINS;
        Skill skill = Skill.ALPHA_COMPLEX;
        stat.setValue(3);
        skill.setValue(3);

        RollMessage message = new RollMessage(
                stat, true,
                skill, true,
                Collections.emptyList(),
                Collections.emptyList(),
                "Please roll with..."
        );
        message.setVisible(true);
    }


}
