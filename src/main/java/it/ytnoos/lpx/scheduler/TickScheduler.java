package it.ytnoos.lpx.scheduler;

public interface TickScheduler extends AutoCloseable {

    void start();

    @Override
    void close();

    int calculateLimit();

    void handleValue(Object result);

    void handleValue2(Object result);

    interface TickCallback {

        void update(int tick);
    }
}
