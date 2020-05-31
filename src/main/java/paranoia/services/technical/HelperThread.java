package paranoia.services.technical;

import java.util.function.Function;

public class HelperThread<T> extends Thread {

    private T value = null;
    private final Function<Void, T> function;
    private final Object lock;

    public HelperThread(Function<Void, T> function, Object lock) {
        this.function = function;
        this.lock = lock;
    }

    @Override
    public void run() {
        value = function.apply(null);
        synchronized (lock) {
            lock.notify();
        }
    }

    public synchronized T getValue() {
        return value;
    }
}
