package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticParticleType<T extends ParticleData> extends AbstractMappedEntity
        implements ParticleType<T> {

    private final PacketWrapper.Reader<T> reader;

    private final PacketWrapper.Writer<T> writer;

    private final ParticleTypes.Decoder<T> decoder;

    private final ParticleTypes.Encoder<T> encoder;

    @Internal
    public StaticParticleType(
            @Nullable TypesBuilderData typesBuilderData,
            PacketWrapper.Reader<T> reader2,
            PacketWrapper.Writer<T> writer2,
            ParticleTypes.Decoder<T> decoder2,
            ParticleTypes.Encoder<T> encoder2) {
        super(typesBuilderData);
        this.reader = reader2;
        this.writer = writer2;
        this.decoder = decoder2;
        this.encoder = encoder2;
    }

    @Override
    public T readData(PacketWrapper<?> packetWrapper) {
        return this.reader.apply(packetWrapper);
    }

    @Override
    public void writeData(PacketWrapper<?> packetWrapper, T t) {
        if (this.writer != null) {
            this.writer.accept(packetWrapper, (T) t);
        } else if (!t.isEmpty()) {
            throw new UnsupportedOperationException(
                    "Trying to write non-empty data for " + this.getName());
        }
    }

    @Override
    public T decodeData(NBTCompound nBTCompound, ClientVersion clientVersion) {
        return this.decoder.decode(nBTCompound, clientVersion);
    }

    @Override
    public void encodeData(T t, ClientVersion clientVersion, NBTCompound nBTCompound) {
        if (this.encoder != null) {
            this.encoder.encode((T) t, clientVersion, nBTCompound);
        } else if (!t.isEmpty()) {
            throw new UnsupportedOperationException(
                    "Trying to encode non-empty data for " + this.getName());
        }
    }
}
