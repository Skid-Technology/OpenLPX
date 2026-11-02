package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Configuration.Client;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.UUID;

public class WrapperConfigClientResourcePackStatus
        extends PacketWrapper<WrapperConfigClientResourcePackStatus> {

    private UUID packId;

    private WrapperConfigClientResourcePackStatus.Result result;

    public WrapperConfigClientResourcePackStatus(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperConfigClientResourcePackStatus(
            WrapperConfigClientResourcePackStatus.Result result) {
        this(UUID.randomUUID(), result);
    }

    public WrapperConfigClientResourcePackStatus(
            UUID packId, WrapperConfigClientResourcePackStatus.Result result) {
        super(Client.RESOURCE_PACK_STATUS);
        this.packId = packId;
        this.result = result;
    }

    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            this.packId = this.readUUID();
        }
        this.result = WrapperConfigClientResourcePackStatus.Result.VALUES[this.readVarInt()];
    }

    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            this.writeUUID(this.packId);
        }
        this.writeVarInt(this.result.ordinal());
    }

    public void copy(WrapperConfigClientResourcePackStatus wrapperConfigClientResourcePackStatus) {
        this.packId = wrapperConfigClientResourcePackStatus.packId;
        this.result = wrapperConfigClientResourcePackStatus.result;
    }

    public UUID getPackId() {
        return this.packId;
    }

    public void setPackId(UUID packId) {
        this.packId = packId;
    }

    public WrapperConfigClientResourcePackStatus.Result getResult() {
        return this.result;
    }

    public void setResult(WrapperConfigClientResourcePackStatus.Result result) {
        this.result = result;
    }

    public enum Result {
        SUCCESSFULLY_LOADED,
        DECLINED,
        FAILED_DOWNLOAD,
        ACCEPTED,
        DOWNLOADED,
        INVALID_URL,
        FAILED_RELOAD,
        DISCARDED;

        public static final WrapperConfigClientResourcePackStatus.Result[] VALUES = values();
    }
}
