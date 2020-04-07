package paranoia.services.hpdmc;

import paranoia.core.Clone;
import paranoia.core.ICoreTechPart;
import paranoia.services.hpdmc.manager.MissionManager;
import paranoia.services.hpdmc.manager.ParanoiaManager;
import paranoia.visuals.CerebrealCoretech;
import paranoia.visuals.ComponentName;

import javax.swing.JFrame;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls the core game elements - GameMaster interface
 */
public class ControlUnit {

    CerebrealCoretech visuals;
    private Map<ComponentName, ParanoiaManager<? extends ICoreTechPart>> managerMap;

    public ControlUnit(Clone clone) {
        managerMap = new HashMap<>();
        managerMap.put(ComponentName.MISSION_PANEL, new MissionManager());
        //Setup managers

        visuals = new CerebrealCoretech(clone, this);
    }

    @SuppressWarnings("rawtypes")
    public ParanoiaManager getManager(ComponentName name) {
        return managerMap.get(name);
    }

    public JFrame getVisuals() {
        return visuals;
    }

    public void updateAsset(ICoreTechPart asset, ComponentName name) {
        managerMap.get(name).addAsset(asset);
    }

}
