package paranoia.services.hpdmc.manager;

import paranoia.core.ICoreTechPart;
import paranoia.services.hpdmc.ParanoiaListener;

public interface ParanoiaManager<T extends ICoreTechPart> {

    void addListener(ParanoiaListener<T> listener);

    void removeListener(ParanoiaListener<T> listener);

    void updateAsset(ICoreTechPart asset);

    void removeAsset(ICoreTechPart asset);

}
