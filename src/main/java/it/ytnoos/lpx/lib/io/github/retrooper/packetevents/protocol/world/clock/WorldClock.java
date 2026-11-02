package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface WorldClock extends MappedEntity, CopyableEntity<WorldClock>, DeepComparableEntity {

    NbtCodec<WorldClock> CODEC = NbtCodecs.forRegistry(WorldClocks.getRegistry());

    NbtCodec<WorldClock> DIRECT_CODEC =
            ((NbtCodec<WorldClock>) NbtCodecs.<WorldClock>forUnit(StaticWorldClock::new).codec());

    static WorldClock read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(WorldClocks.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, WorldClock worldClock) {
        packetWrapper.writeMappedEntity(worldClock);
    }
}
