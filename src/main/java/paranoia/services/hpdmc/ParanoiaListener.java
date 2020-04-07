package paranoia.services.hpdmc;

import java.util.Collection;

public interface ParanoiaListener<T> {

    void updateVisualDataChange(Collection<T> updatedModel);

}
