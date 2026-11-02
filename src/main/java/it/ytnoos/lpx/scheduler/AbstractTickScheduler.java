package it.ytnoos.lpx.scheduler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class AbstractTickScheduler implements TickScheduler {

    private final Set<TickScheduler.TickCallback> values;

    private int tick;

    public AbstractTickScheduler() {
        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();
        this.values = copyOnWriteArraySet;
        this.tick = 0;
    }

    protected void handleTime() {
        for (TickScheduler.TickCallback tickCallback : this.values) {
            {
                tickCallback.update(this.tick);
            }
        }
        AbstractTickScheduler abstractTickScheduler = this;
        abstractTickScheduler.tick++;
    }

    @Override
    public int calculateLimit() {
        return this.tick;
    }

    @Override
    public void handleValue(Object result) {
        this.values.add((TickScheduler.TickCallback) result);
    }

    @Override
    public void handleValue2(Object result) {
        this.values.remove(result);
    }
}
