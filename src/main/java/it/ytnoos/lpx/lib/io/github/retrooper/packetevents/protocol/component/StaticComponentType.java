package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class StaticComponentType<T> extends AbstractMappedEntity implements ComponentType<T> {

    @Nullable private final PacketWrapper.Reader<T> reader;

    @Nullable private final PacketWrapper.Writer<T> writer;

    @Nullable private final ComponentType.Decoder<T> decoder;

    @Nullable private final ComponentType.Encoder<T> encoder;

    @Internal
    public StaticComponentType(
            @Nullable TypesBuilderData typesBuilderData,
            @Nullable PacketWrapper.Reader<T> reader,
            @Nullable PacketWrapper.Writer<T> writer) {
        this(typesBuilderData, reader, writer, null, null);
    }

    @Internal
    public StaticComponentType(
            @Nullable TypesBuilderData typesBuilderData,
            @Nullable ComponentType.Decoder<T> decoder,
            @Nullable ComponentType.Encoder<T> encoder) {
        this(typesBuilderData, null, null, decoder, encoder);
    }

    @Internal
    public StaticComponentType(
            @Nullable TypesBuilderData typesBuilderData,
            @Nullable PacketWrapper.Reader<T> reader2,
            @Nullable PacketWrapper.Writer<T> writer2,
            @Nullable ComponentType.Decoder<T> decoder2,
            @Nullable ComponentType.Encoder<T> encoder2) {
        super(typesBuilderData);
        this.reader = reader2;
        this.writer = writer2;
        this.decoder = decoder2;
        this.encoder = encoder2;
    }

    @Override
    public T read(PacketWrapper<?> packetWrapper) {
        return this.reader != null ? this.reader.apply(packetWrapper) : null;
    }

    @Override
    public void write(PacketWrapper<?> packetWrapper, T t) {
        if (this.writer != null) {
            this.writer.accept(packetWrapper, (T) t);
        }
    }

    @Override
    public T decode(NBT nBT, ClientVersion clientVersion) {
        if (this.decoder != null) {
            return this.decoder.decode(nBT, clientVersion);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public NBT encode(T t, ClientVersion clientVersion) {
        if (this.encoder != null) {
            return this.encoder.encode((T) t, clientVersion);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public <Z> ComponentType<Z> legacyMap(Function<T, Z> function, Function<Z, T> function2) {
        PacketWrapper.Reader reader2;
        if (this.reader != null) {
            reader2 =
                    result ->
                            ((Function) function)
                                    .apply(this.reader.apply(((PacketWrapper<?>) result)));
        } else {
            reader2 = null;
        }
        PacketWrapper.Reader reader3 = reader2;
        PacketWrapper.Writer writer2;
        if (this.writer != null) {
            writer2 =
                    (result, result2) ->
                            this.writer.accept(
                                    ((PacketWrapper<?>) result),
                                    (T) ((Function) function2).apply(result2));
        } else {
            writer2 = null;
        }
        PacketWrapper.Writer writer3 = writer2;
        ComponentType.Decoder decoder2;
        if (this.decoder != null) {
            decoder2 =
                    (result, result2) ->
                            ((Function) function).apply(this.decoder.decode(result, result2));
        } else {
            decoder2 = null;
        }
        ComponentType.Decoder decoder3 = decoder2;
        ComponentType.Encoder encoder2;
        if (this.encoder != null) {
            encoder2 =
                    (result, result2) ->
                            this.encoder.encode((T) ((Function) function2).apply(result), result2);
        } else {
            encoder2 = null;
        }
        ComponentType.Encoder encoder3 = encoder2;
        return new StaticComponentType(this.data, reader3, writer3, decoder3, encoder3);
    }
}
