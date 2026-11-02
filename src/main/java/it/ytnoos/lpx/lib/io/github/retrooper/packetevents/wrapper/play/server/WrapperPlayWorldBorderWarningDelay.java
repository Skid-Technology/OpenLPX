package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.VersionComparison;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayWorldBorderWarningDelay
        extends PacketWrapper<WrapperPlayWorldBorderWarningDelay> {

    private long delay;

    public WrapperPlayWorldBorderWarningDelay(long delay) {
        super(PacketType.Play.Server.WORLD_BORDER_WARNING_DELAY);
        this.delay = delay;
    }

    public WrapperPlayWorldBorderWarningDelay(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Override
    public void read() {
        this.delay =
                this.readMultiVersional(
                        VersionComparison.NEWER_THAN_OR_EQUALS,
                        ServerVersion.V_1_19,
                        PacketWrapper::readVarInt,
                        PacketWrapper::readVarLong);
    }

    @Override
    public void write() {
        this.writeMultiVersional(
                VersionComparison.NEWER_THAN_OR_EQUALS,
                ServerVersion.V_1_19,
                this.delay,
                (result, result2) -> result.writeVarInt(Math.toIntExact(result2)),
                PacketWrapper::writeVarLong);
    }

    public void copy(WrapperPlayWorldBorderWarningDelay wrapperPlayWorldBorderWarningDelay) {
        this.delay = wrapperPlayWorldBorderWarningDelay.delay;
    }
}
