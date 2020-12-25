package paranoia.services.hpdmc.manager;

import paranoia.core.ICoreTechPart;
import paranoia.core.ParanoiaPlayer;
import paranoia.services.hpdmc.ParanoiaListener;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager implements
    ParanoiaManager<ParanoiaPlayer>
{
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
        ParanoiaPlayer player = (ParanoiaPlayer) asset;
        if (hasPlayer(player)) {
            ParanoiaPlayer valid = getSameID(player);
            players.remove(valid);
        }
        players.add(player);
        listeners.forEach(l -> l.updateVisualDataChange(players));
    }

    @Override
    public void removeAsset(ICoreTechPart asset) {
        ParanoiaPlayer player = (ParanoiaPlayer) asset;
        players.remove(player);
        listeners.forEach(l -> l.updateVisualDataChange(players));
    }

    private ParanoiaPlayer getSameID(ParanoiaPlayer player) {
        return players.stream().filter(p -> p.getID() == player.getID()).findAny().orElse(null);
    }

    private boolean hasPlayer(ParanoiaPlayer player) {
        return players.stream().anyMatch(p -> p.getID() == player.getID());
    }

    public void clear() {
        players.clear();
    }

}
