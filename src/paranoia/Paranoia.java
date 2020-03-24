package paranoia;

import paranoia.services.hpdmc.ControlUnit;
import paranoia.visuals.CerebrealCoretech;

/**
 * The game itself
 */
public class Paranoia {

    public static void main(String[] args) {
        ControlUnit cpu = new ControlUnit();
        CerebrealCoretech coreTech = new CerebrealCoretech();
        coreTech.setVisible(true);
    }


}
