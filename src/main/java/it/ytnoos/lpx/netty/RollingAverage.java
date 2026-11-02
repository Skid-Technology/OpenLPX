package it.ytnoos.lpx.netty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class RollingAverage {

    private final BigDecimal[] samples;

    private final int size;

    private int index = 0;

    private int count = 0;

    private static final String TEXT = "size must be > 0";

    private static final int AVERAGE_SCALE = 30;

    public RollingAverage(int size) {
        if (size <= 0) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(TEXT);
            throw illegalArgumentException;
        }
        this.size = size;
        this.samples = new BigDecimal[size];
    }

    public void add(BigDecimal bigDecimal) {
        this.samples[this.index] = bigDecimal;
        this.index = (this.index + 1) % this.size;
        if (this.count < this.size) {
            RollingAverage rollingAverage = this;
            rollingAverage.count++;
        }
    }

    public double getAverage() {
        if (this.count == 0) return 0.0;
        BigDecimal total = BigDecimal.ZERO;
        for (int sample = 0; sample < this.count; sample++) total = total.add(this.samples[sample]);
        return total.divide(BigDecimal.valueOf(this.count), AVERAGE_SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public BigDecimal getMin() {
        if (this.count == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = this.samples[0];
        for (int index = 1; index < this.count; index++) {
            if (this.samples[index].compareTo(bigDecimal) < 0) {
                bigDecimal = this.samples[index];
            }
        }
        return bigDecimal;
    }

    public BigDecimal getMax() {
        if (this.count == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = this.samples[0];
        for (int index = 1; index < this.count; index++) {
            if (this.samples[index].compareTo(bigDecimal) > 0) {
                bigDecimal = this.samples[index];
            }
        }
        return bigDecimal;
    }

    public BigDecimal getPercentile(double value) {
        if (this.count == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal[] bigDecimals = new BigDecimal[this.count];
        System.arraycopy(this.samples, 0, bigDecimals, 0, this.count);
        Arrays.sort(bigDecimals);
        int value2 = (int) Math.ceil(value / 100.0 * this.count);
        return bigDecimals[Math.max(0, Math.min(value2 - 1, this.count - 1))];
    }

    public int getCount() {
        return this.count;
    }
}
