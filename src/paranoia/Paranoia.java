package paranoia;

import paranoia.core.Clone;
import paranoia.core.SecurityClearance;
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

        Clone clone = new Clone("SYD", "ULS", SecurityClearance.RED, 3);

        RollMessage message = new RollMessage(
                clone,
                Stat.BRAINS, true,
                Skill.ALPHA_COMPLEX, true,
                Collections.emptyList(),
                Collections.emptyList(),
                "Please roll with..."
        );
        message.setVisible(true);
    }


}
