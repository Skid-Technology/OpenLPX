package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

public class KnownPack {

    private final String namespace;

    private final String id;

    private final String version;

    public KnownPack(String namespace, String id, String version) {
        this.namespace = namespace;
        this.id = id;
        this.version = version;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getId() {
        return this.id;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof KnownPack)) {
            return false;
        } else {
            KnownPack knownPack = (KnownPack) result;
            if (!this.namespace.equals(knownPack.namespace)) {
                return false;
            } else {
                return !this.id.equals(knownPack.id)
                        ? false
                        : this.version.equals(knownPack.version);
            }
        }
    }

    @Override
    public int hashCode() {
        int value = this.namespace.hashCode();
        value = 31 * value + this.id.hashCode();
        return 31 * value + this.version.hashCode();
    }

    @Override
    public String toString() {
        return "KnownPack{namespace='"
                + this.namespace
                + '\''
                + ", id='"
                + this.id
                + '\''
                + ", version='"
                + this.version
                + '\''
                + '}';
    }
}
