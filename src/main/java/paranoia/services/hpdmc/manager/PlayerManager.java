package paranoia.services.hpdmc.manager;

import paranoia.core.ICoreTechPart;
import paranoia.core.ParanoiaPlayer;
import paranoia.services.hpdmc.ParanoiaListener;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager implements ParanoiaManager<ParanoiaPlayer> {

    List<ParanoiaPlayer> players = new ArrayList<>();
    List<ParanoiaListener<ParanoiaPlayer>> listeners = new ArrayList<>();

    @Override
    public void addListener(ParanoiaListener<ParanoiaPlayer> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ParanoiaListener<ParanoiaPlayer> listener) {
        listeners.remove(listener);
    }

    @Override
    public void updateAsset(ICoreTechPart asset) {
        players.add((ParanoiaPlayer) asset);
        listeners.forEach(l -> l.updateVisualDataChange(players));
    }

    @Override
    public void removeAsset(ICoreTechPart asset) {
        ParanoiaPlayer player = (ParanoiaPlayer) asset;
        players.remove(player);
        listeners.forEach(l -> l.updateVisualDataChange(players));
    }


}
