package it.ytnoos.lpx.lib.kyori.adventure.nbt;

final class EndBinaryTagImpl extends AbstractBinaryTag implements EndBinaryTag {

    static final EndBinaryTagImpl INSTANCE = new EndBinaryTagImpl();

    @Override
    public boolean equals(Object result) {
        return this == result;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
