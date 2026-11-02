package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class PositionPathTypes {

    private static final VersionedRegistry<PositionPathType<?>> REGISTRY =
            new VersionedRegistry<>("position_path_type");

    public static final PositionPathType<LinearPositionPath> LINEAR =
            define("linear", LinearPositionPath::read, LinearPositionPath::write);

    public static final PositionPathType<SteppedPositionPath> STEPPED =
            define("stepped", SteppedPositionPath::read, SteppedPositionPath::write);

    private PositionPathTypes() {}

    private static <T extends PositionPath> PositionPathType<T> define(
            String text, PacketWrapper.Reader<T> reader, PacketWrapper.Writer<T> writer) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticPositionPathType<>(
                                result,
                                ((PacketWrapper.Reader) reader),
                                ((PacketWrapper.Writer) writer)));
    }

    public static VersionedRegistry<PositionPathType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
