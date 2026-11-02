package it.ytnoos.lpx.api.check;

import java.util.Arrays;

public class RepeatedIntervalCounter extends IntervalCounter {

    private final int maxWarnings;

    private final boolean[] periodWarnings;

    private int period;

    public RepeatedIntervalCounter(int value, int value2, int value3, int maxWarnings) {
        super(value, value2);
        this.maxWarnings = maxWarnings;
        this.periodWarnings = new boolean[value3];
    }

    public RepeatedIntervalCounter(int value, int value2, int value3) {
        this(value, 616907311, value2, value3);
    }

    @Override
    public boolean increment(double counter) {
        if (this.maxCounter < 0L) {
            return false;
        }
        boolean matches = this.advancePeriodsIfNeeded(System.currentTimeMillis());
        this.counter += counter;
        return matches;
    }

    public boolean isOverLimit() {
        if (this.maxCounter < 0L) {
            return false;
        }
        this.advancePeriodsIfNeeded(System.currentTimeMillis());
        return this.counter > this.maxCounter;
    }

    @Override
    public double getValue() {
        this.advancePeriodsIfNeeded(System.currentTimeMillis());
        return this.counter;
    }

    private boolean advancePeriods(int value) {
        if (value >= this.periodWarnings.length) {
            this.resetWarnings();
            this.period = 0;
            this.counter = 0.0;
            return false;
        }
        for (int index = 0; index < value; index++) {
            if (this.counter > this.maxCounter) {
                this.periodWarnings[this.period] = true;
            }
            this.period++;
            if (this.period > this.periodWarnings.length - 1) {
                this.period = 0;
            }
            this.periodWarnings[this.period] = false;
            this.counter = 0.0;
        }
        if (this.countWarnings() >= this.maxWarnings) {
            this.resetWarnings();
            return true;
        } else {
            return false;
        }
    }

    public int getPeriod() {
        return this.period;
    }

    public int getWarnings() {
        this.advancePeriodsIfNeeded(System.currentTimeMillis());
        return this.countWarnings();
    }

    public int getMaxPeriods() {
        return this.periodWarnings.length;
    }

    public int getMaxWarnings() {
        return this.maxWarnings;
    }

    public void resetWarnings() {
        Arrays.fill(this.periodWarnings, false);
    }

    private boolean advancePeriodsIfNeeded(long value) {
        long value2;
        if ((value2 = value - this.startTime) < this.interval) {
            return false;
        }
        int value3 = (int) (value2 / this.interval);
        boolean matches = this.advancePeriods(value3);
        this.startTime = this.startTime + value3 * this.interval;
        return matches;
    }

    private int countWarnings() {
        int value = 0;
        boolean[] matchess = this.periodWarnings;
        int value2 = this.periodWarnings.length;
        for (int index = 0; index < value2; index++) {
            if (matchess[index]) {
                value++;
            }
        }
        return value;
    }
}
