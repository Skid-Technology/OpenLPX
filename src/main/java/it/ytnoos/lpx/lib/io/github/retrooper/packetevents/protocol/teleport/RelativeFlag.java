package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.teleport;

public final class RelativeFlag {

    public static final RelativeFlag NONE = new RelativeFlag(0);

    public static final RelativeFlag X = new RelativeFlag(1);

    public static final RelativeFlag Y = new RelativeFlag(2);

    public static final RelativeFlag Z = new RelativeFlag(4);

    public static final RelativeFlag YAW = new RelativeFlag(8);

    public static final RelativeFlag PITCH = new RelativeFlag(16);

    public static final RelativeFlag DELTA_X = new RelativeFlag(32);

    public static final RelativeFlag DELTA_Y = new RelativeFlag(64);

    public static final RelativeFlag DELTA_Z = new RelativeFlag(128);

    public static final RelativeFlag ROTATE_DELTA = new RelativeFlag(256);

    private final int mask;

    public RelativeFlag(int mask) {
        this.mask = mask;
    }

    public RelativeFlag and(RelativeFlag relativeFlag) {
        return new RelativeFlag(this.mask & relativeFlag.mask);
    }

    public RelativeFlag or(RelativeFlag relativeFlag) {
        return new RelativeFlag(this.mask | relativeFlag.mask);
    }

    public boolean has(RelativeFlag relativeFlag) {
        return this.has(relativeFlag.mask);
    }

    public boolean has(int value) {
        return (value & this.mask) != 0;
    }

    public RelativeFlag set(RelativeFlag relativeFlag, boolean matches) {
        return this.set(relativeFlag.mask, matches);
    }

    public RelativeFlag set(int value, boolean matches) {
        int value2 = matches ? this.mask | value : this.mask & ~value;
        return new RelativeFlag(value2);
    }

    @Deprecated
    public RelativeFlag combine(RelativeFlag relativeFlag) {
        return this.or(relativeFlag);
    }

    @Deprecated
    public boolean isSet(byte value) {
        return this.has(value);
    }

    @Deprecated
    public byte set(byte value, boolean matches) {
        return (byte) this.set((int) value, matches).mask;
    }

    public byte getMask() {
        return (byte) this.mask;
    }

    public int getFullMask() {
        return this.mask;
    }
}
