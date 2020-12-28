package paranoia.services.technical;

import paranoia.core.ICoreTechPart;
import paranoia.services.hpdmc.manager.ParanoiaManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Type-safe implementation of Manager Map which stores Paranoia Managers.
 * Uses the Map implementation the managers are stored via key-value pairs,
 * where the keys are the subclasses used by the stored Paranoia Manager.
 */
public class ParanoiaManagerMap {

    private final Map<Class<?>, ParanoiaManager<?>> managerMap = new HashMap<>();

    /**
     * Used to put a Paranoia Manager to the Map. You must specify the sub-class as the key.
     * The given Paranoia Manager must have the same subclass the given subclass. The given class
     * have to implement the ICoreTech interface
     * @param klass A class object used as a key
     * @param manager A Paranoia Manager
     * @param <T> The specified subclass used by the Paranoia Manager
     */
    public <T extends ICoreTechPart> void put(Class<T> klass, ParanoiaManager<T> manager) {
        managerMap.put(klass, manager);
    }

    @SuppressWarnings("unchecked")  //Type check is ensured at putting operation.
    public <T extends ICoreTechPart> ParanoiaManager<T> get(Class<T> klass) {
        return (ParanoiaManager<T>) managerMap.get(klass);
    }

}
