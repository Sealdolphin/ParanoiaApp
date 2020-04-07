package paranoia.services.hpdmc.manager;

import paranoia.core.ICoreTechPart;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.rnd.ParanoiaCard;

import java.util.ArrayList;
import java.util.List;

public class CardManager implements ParanoiaManager<ParanoiaCard> {

    private List<ParanoiaCard> cards;
    private List<ParanoiaListener<ParanoiaCard>> cardListeners;

    public CardManager() {
        cards = new ArrayList<>();
        cardListeners = new ArrayList<>();
    }

    private void updateListeners() {
        cardListeners.forEach(listener -> listener.updateVisualDataChange(cards));
    }

    @Override
    public void addListener(ParanoiaListener<ParanoiaCard> listener) {
        cardListeners.add(listener);
    }

    @Override
    public void removeListener(ParanoiaListener<ParanoiaCard> listener) {
        cardListeners.remove(listener);
    }

    @Override
    public void addAsset(ICoreTechPart asset) {
        cards.add((ParanoiaCard) asset);
        updateListeners();
    }

    @Override
    public void removeAsset(ICoreTechPart asset) {
        ParanoiaCard card = (ParanoiaCard) asset;
        cards.remove(card);
        updateListeners();
    }
}
