package paranoia.services.hpdmc.manager;

import paranoia.core.ICoreTechPart;
import paranoia.core.cpu.Mission;
import paranoia.services.hpdmc.ParanoiaListener;

import java.util.ArrayList;
import java.util.List;

public class MissionManager implements ParanoiaManager<Mission> {

    private List<Mission> missionFeed;
    private List<ParanoiaListener<Mission>> missionListeners;

    public MissionManager() {
        missionFeed = new ArrayList<>();
        missionListeners = new ArrayList<>();
    }

    public void updateMissionStatus(int missionId, Mission.MissionStatus status) {
        Mission picked = getMissionById(missionId);
        if(picked == null) return;
        switch (status) {
            case FAILED:
                picked.fail();
                break;
            case COMPLETED:
                picked.complete();
                break;
            case ACCEPTED:
                break;
        }
        updateListeners();
    }

    private void updateListeners() {
        missionListeners.forEach(listener -> listener.updateVisualDataChange(missionFeed));
    }

    private Mission getMissionById(int missionId) {
        return missionFeed.stream().filter(m -> m.getId().equals(missionId)).findAny().orElse(null);
    }

    public void addListener(ParanoiaListener<Mission> listener) {
        missionListeners.add(listener);
    }

    public void removeListener(ParanoiaListener<Mission> listener) {
        missionListeners.remove(listener);
    }

    @Override
    public void addAsset(ICoreTechPart mission) {
        missionFeed.add((Mission) mission);
        updateListeners();
    }

    @Override
    public void removeAsset(ICoreTechPart asset) {
        Mission mission = (Mission) asset;
        missionFeed.remove(mission);
        updateListeners();
    }

}
