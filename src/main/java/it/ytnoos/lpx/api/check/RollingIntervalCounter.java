package it.ytnoos.lpx.api.check;

public final class RollingIntervalCounter {

    private final double maxCounter;

    private final long interval;

    private final long intervalNanos;

    private long[] sampleTimes;

    private double[] sampleValues;

    private int headIndex;

    private int tailIndex;

    private double counter;

    private long lastFlag;

    private boolean overLimit;

    public RollingIntervalCounter(long value) {
        this(value, 1000L);
    }

    public RollingIntervalCounter(long maxCounter, long interval) {
        if (interval <= 0L) {
            throw new IllegalArgumentException("interval must be positive");
        }
        this.maxCounter = maxCounter;
        this.interval = interval;
        this.intervalNanos = interval * 1000000L;
        this.sampleTimes = new long[8];
        this.sampleValues = new double[8];
    }

    public boolean increment() {
        return this.increment(1.0);
    }

    public boolean increment(double value) {
        if (this.maxCounter < 0.0) {
            return false;
        }
        long value2 = System.nanoTime();
        this.evictExpired(value2);
        this.addSample(value2, value);
        boolean matches;
        boolean matches2 = (matches = this.counter > this.maxCounter) && !this.overLimit;
        this.overLimit = matches;
        if (matches2) {
            this.lastFlag = System.currentTimeMillis();
        }
        return matches2;
    }

    public boolean isOverLimit() {
        if (this.maxCounter < 0.0) {
            return false;
        }
        this.evictExpired(System.nanoTime());
        this.overLimit = this.counter > this.maxCounter;
        return this.overLimit;
    }

    public long getInterval() {
        return this.interval;
    }

    public double getValue() {
        this.evictExpired(System.nanoTime());
        return this.counter;
    }

    public double getMaxCounter() {
        return this.maxCounter;
    }

    public boolean hasFlaggedRecently() {
        return this.hasFlaggedRecently(this.interval);
    }

    public boolean hasFlaggedRecently(long value) {
        return System.currentTimeMillis() - this.lastFlag < value;
    }

    private void evictExpired(long value) {
        long value2 = value - this.intervalNanos;
        while (this.headIndex != this.tailIndex && this.sampleTimes[this.headIndex] < value2) {
            this.counter = this.counter - this.sampleValues[this.headIndex];
            this.sampleValues[this.headIndex] = 0.0;
            this.headIndex++;
            if (this.headIndex >= this.sampleTimes.length) {
                this.headIndex = 0;
            }
        }
        if (this.counter <= 0.0) {
            this.counter = 0.0;
        }
    }

    private void addSample(long value, double counter) {
        if (!(counter <= 0.0)) {
            int value3;
            if ((value3 = incrementIndex(this.tailIndex, this.sampleTimes.length))
                    == this.headIndex) {
                this.resize();
                value3 = incrementIndex(this.tailIndex, this.sampleTimes.length);
            }
            this.sampleTimes[this.tailIndex] = value;
            this.sampleValues[this.tailIndex] = counter;
            this.tailIndex = value3;
            this.counter += counter;
        }
    }

    private void resize() {
        int size2 = this.size();
        int value;
        long[] values = new long[value = this.sampleTimes.length * 2];
        double[] values2 = new double[value];
        for (int index = 0; index < size2; index++) {
            int value2 = (this.headIndex + index) % this.sampleTimes.length;
            values[index] = this.sampleTimes[value2];
            values2[index] = this.sampleValues[value2];
        }
        this.sampleTimes = values;
        this.sampleValues = values2;
        this.headIndex = 0;
        this.tailIndex = size2;
    }

    private int size() {
        return this.tailIndex >= this.headIndex
                ? this.tailIndex - this.headIndex
                : this.tailIndex + (this.sampleTimes.length - this.headIndex);
    }

    private static int incrementIndex(int value, int value2) {
        return value + 1 >= value2 ? 0 : value + 1;
    }
}
