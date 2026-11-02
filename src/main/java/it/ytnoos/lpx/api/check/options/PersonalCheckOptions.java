package it.ytnoos.lpx.api.check.options;

public class PersonalCheckOptions {

    private final CheckOptions checkOptions;

    private int vl;

    private double buffer;

    public PersonalCheckOptions(CheckOptions checkOptions2) {
        this.checkOptions = checkOptions2;
    }

    public CheckOptions getCheckOptions() {
        return this.checkOptions;
    }

    public int getVl() {
        return this.vl;
    }

    public void setVl(int vl) {
        this.vl = vl;
    }

    public void increaseVl() {
        this.vl++;
    }

    public double getBuffer() {
        return this.buffer;
    }

    public void setBuffer(double buffer) {
        this.buffer = buffer;
    }

    public void reset() {
        this.vl = 0;
        this.buffer = 0.0;
    }
}
