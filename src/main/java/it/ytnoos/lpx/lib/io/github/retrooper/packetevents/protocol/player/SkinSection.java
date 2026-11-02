package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

public final class SkinSection {

    public static final SkinSection CAPE = new SkinSection(1);

    public static final SkinSection JACKET = new SkinSection(2);

    public static final SkinSection LEFT_SLEEVE = new SkinSection(4);

    public static final SkinSection RIGHT_SLEEVE = new SkinSection(8);

    public static final SkinSection LEFT_PANTS = new SkinSection(16);

    public static final SkinSection RIGHT_PANTS = new SkinSection(32);

    public static final SkinSection HAT = new SkinSection(64);

    public static final SkinSection ALL =
            CAPE.combine(JACKET)
                    .combine(LEFT_SLEEVE)
                    .combine(RIGHT_SLEEVE)
                    .combine(LEFT_PANTS)
                    .combine(RIGHT_PANTS)
                    .combine(HAT);

    private final byte mask;

    public SkinSection(int value) {
        this.mask = (byte) value;
    }

    public SkinSection combine(SkinSection skinSection) {
        return new SkinSection(this.mask | skinSection.mask);
    }

    public byte getMask() {
        return this.mask;
    }

    public boolean isSet(byte value) {
        return (value & this.mask) != 0;
    }

    public byte set(byte value, boolean matches) {
        if (matches) {
            value = (byte) (value | this.mask);
        } else {
            value = (byte) (value & ~this.mask);
        }
        return value;
    }
}
