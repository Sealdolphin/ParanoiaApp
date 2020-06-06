package paranoia.helper;

import paranoia.services.hpdmc.ParanoiaListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class ParanoiaListenerMock<T> implements ParanoiaListener<T> {

    private List<T> collection;

    public int getSize() {
        return collection.size();
    }

    public Stream<T> getStream() {
        return collection.stream();
    }

    @Override
    public void updateVisualDataChange(Collection<T> updatedModel) {
        collection = new ArrayList<>(updatedModel);
    }

    public T get(int i) {
        return collection.get(i);
    }

}
