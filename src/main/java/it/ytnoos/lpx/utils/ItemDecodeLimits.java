package it.ytnoos.lpx.utils;

import it.ytnoos.lpx.api.player.ProtocolPlayer;

public final class ItemDecodeLimits {

    private final int limit;

    private final int limit2;

    private ItemDecodeLimits(int limit, int limit2) {
        this.limit = limit;
        this.limit2 = limit2;
    }

    public static ItemDecodeLimits defaults() {
        return new ItemDecodeLimits(4096, 2048);
    }

    public int calculateLimit(ProtocolPlayer protocolPlayer) {
        return protocolPlayer.isLegacyClientOnHashedServer() ? this.limit2 : this.limit;
    }
}
