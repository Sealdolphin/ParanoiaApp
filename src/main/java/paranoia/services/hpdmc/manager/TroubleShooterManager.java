package paranoia.services.hpdmc.manager;

import paranoia.core.Clone;
import paranoia.core.ICoreTechPart;
import paranoia.core.SecurityClearance;
import paranoia.services.hpdmc.ParanoiaListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TroubleShooterManager implements ParanoiaManager<Clone>{

    private final Collection<Clone> troubleShooters = new ArrayList<>();
    private final List<ParanoiaListener<Clone>> listeners = new ArrayList<>();

    public void setInjury(int id, int level) {
        findCloneById(id).setInjury(level);
        updateListeners();
    }

    public void setTreasonStars(int id, int stars) {
        findCloneById(id).setTreasonStars(stars);
        updateListeners();
    }

    public void giveXPPoints(int id, int xp) {
        findCloneById(id).giveXpPoints(xp);
        updateListeners();
    }

    public void setSecurityClearance(int id, SecurityClearance clearance) {
        findCloneById(id).setClearance(clearance);
        updateListeners();
    }

    private Clone findCloneById(int id) {
        return troubleShooters.stream().filter(c -> c.getPlayerId() == id).findAny().orElse(null);
    }

    private void updateListeners() {
        listeners.forEach(l -> l.updateVisualDataChange(troubleShooters));
    }

    @Override
    public void addListener(ParanoiaListener<Clone> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ParanoiaListener<Clone> listener) {
        listeners.remove(listener);
    }

    @Override
    public void updateAsset(ICoreTechPart asset) {
        Clone clone = (Clone) asset;
        troubleShooters.add(clone);
        updateListeners();
    }

    @Override
    public void removeAsset(ICoreTechPart asset) {
        Clone clone = (Clone) asset;
        troubleShooters.remove(clone);
        updateListeners();
    }
}
