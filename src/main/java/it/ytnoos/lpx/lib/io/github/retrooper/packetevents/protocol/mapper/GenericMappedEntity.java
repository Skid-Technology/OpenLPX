package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

@Deprecated
public class GenericMappedEntity implements StaticMappedEntity {

    private final int id;

    public GenericMappedEntity(ClientVersion clientVersion, int value) {
        this(value);
    }

    public GenericMappedEntity(int id) {
        this.id = id;
    }

    public static GenericMappedEntity getById(ClientVersion clientVersion, int value) {
        return new GenericMappedEntity(clientVersion, value);
    }

    public static GenericMappedEntity read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(GenericMappedEntity::new);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, GenericMappedEntity genericMappedEntity) {
        packetWrapper.writeMappedEntity(genericMappedEntity);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public ResourceLocation getName() {
        throw new UnsupportedOperationException();
    }
}
