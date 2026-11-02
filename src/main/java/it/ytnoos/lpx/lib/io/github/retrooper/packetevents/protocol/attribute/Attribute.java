package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;

public interface Attribute extends MappedEntity {

    NbtCodec<Attribute> CODEC = NbtCodecs.forRegistry(Attributes.getRegistry());

    @Override
    default ResourceLocation getName() {
        return this.getName(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion());
    }

    ResourceLocation getName(ClientVersion clientVersion);

    default double sanitizeValue(double value) {
        return this.sanitizeValue(
                value, PacketEvents.getAPI().getServerManager().getVersion().toClientVersion());
    }

    default double sanitizeValue(double value, ClientVersion clientVersion) {
        return !Double.isNaN(value)
                ? MathUtil.clamp(value, this.getMinValue(), this.getMaxValue())
                : this.getMinValue();
    }

    double getDefaultValue();

    double getMinValue();

    double getMaxValue();
}
