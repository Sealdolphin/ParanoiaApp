package paranoia.services.hpdmc.manager;

import paranoia.core.Clone;
import paranoia.core.ICoreTechPart;
import paranoia.core.SecurityClearance;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.technical.command.ModifyCommand;
import paranoia.visuals.mechanics.Moxie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TroubleShooterManager implements
    ParanoiaManager<Clone>, ModifyCommand.ParanoiaModifyListener {

    private final Collection<Clone> troubleShooters = new ArrayList<>();
    private final List<ParanoiaListener<Clone>> listeners = new ArrayList<>();
    private static final int SELF_ID = -1;

    public void setInjury(int level) {
        setInjury(SELF_ID, level);
    }

    public void setInjury(int id, int level) {
        findCloneById(id).setInjury(level);
        updateListeners();
    }

    public void setTreasonStars(int stars) {
        setTreasonStars(SELF_ID, stars);
    }

    public void setTreasonStars(int id, int stars) {
        findCloneById(id).setTreasonStars(stars);
        updateListeners();
    }

    public void giveXPPoints(int xp) {
        giveXPPoints(SELF_ID, xp);
    }

    public void giveXPPoints(int id, int xp) {
        findCloneById(id).giveXpPoints(xp);
        updateListeners();
    }

    public void setMoxie(int moxie) {
        findCloneById(SELF_ID).setMoxie(moxie);
        updateListeners();
    }

    public void crossOutMoxie() {
        findCloneById(SELF_ID).crossOut();
        updateListeners();
    }

    public void setSecurityClearance(SecurityClearance clearance) {
        setSecurityClearance(SELF_ID, clearance);
    }

    public void setSecurityClearance(int id, SecurityClearance clearance) {
        findCloneById(id).setClearance(clearance);
        updateListeners();
    }

    private Clone findCloneById(int id) {
        if(id == SELF_ID) return troubleShooters.iterator().next();
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

    public SecurityClearance getClearance() {
        return findCloneById(SELF_ID).getClearance();
    }

    @Override
    public void modify(ModifyCommand.Modifiable attribute, int value, Object details) {
        switch (attribute) {
            case MOXIE:
                setMoxie(value);
                break;
            case TREASON_STAR:
                setTreasonStars(value);
                break;
            case MAX_MOXIE:
                int crossOut = Moxie.MOXIE_COUNT - value;
                for (int i = 0; i < crossOut; i++) {
                    crossOutMoxie();
                }
                break;
            case CLONE:
                Clone lastClone = troubleShooters.iterator().next();
                for (int i = 0; i < value; i++) {
                    lastClone = (Clone) lastClone.clone();
                }
                troubleShooters.clear();
                troubleShooters.add(lastClone);
                break;
            default:
                break;
        }
    }
}
