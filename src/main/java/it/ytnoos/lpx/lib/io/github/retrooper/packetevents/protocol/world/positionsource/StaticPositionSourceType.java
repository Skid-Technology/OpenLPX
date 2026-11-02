package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticPositionSourceType<T extends PositionSource> extends AbstractMappedEntity
        implements PositionSourceType<T> {

    private final PacketWrapper.Reader<T> reader;

    private final PacketWrapper.Writer<T> writer;

    private final PositionSourceTypes.Decoder<T> decoder;

    private final PositionSourceTypes.Encoder<T> encoder;

    @Internal
    public StaticPositionSourceType(
            @Nullable TypesBuilderData typesBuilderData,
            PacketWrapper.Reader<T> reader2,
            PacketWrapper.Writer<T> writer2,
            PositionSourceTypes.Decoder<T> decoder2,
            PositionSourceTypes.Encoder<T> encoder2) {
        super(typesBuilderData);
        this.reader = reader2;
        this.writer = writer2;
        this.decoder = decoder2;
        this.encoder = encoder2;
    }

    @Override
    public T read(PacketWrapper<?> packetWrapper) {
        return this.reader.apply(packetWrapper);
    }

    @Override
    public void write(PacketWrapper<?> packetWrapper, T t) {
        this.writer.accept(packetWrapper, (T) t);
    }

    @Override
    public T decode(NBTCompound nBTCompound, ClientVersion clientVersion) {
        return this.decoder.decode(nBTCompound, clientVersion);
    }

    @Override
    public void encode(T t, ClientVersion clientVersion, NBTCompound nBTCompound) {
        this.encoder.encode((T) t, clientVersion, nBTCompound);
    }
}
