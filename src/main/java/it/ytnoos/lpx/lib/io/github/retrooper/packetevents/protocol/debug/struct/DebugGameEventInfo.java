package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DebugGameEventInfo {

    private final ResourceLocation event;

    private final Vector3d pos;

    public DebugGameEventInfo(ResourceLocation resourceLocation, Vector3d vector3d) {
        this.event = resourceLocation;
        this.pos = vector3d;
    }

    public static DebugGameEventInfo read(PacketWrapper<?> packetWrapper) {
        ResourceLocation resourceLocation = ResourceLocation.read(packetWrapper);
        Vector3d vector3d = Vector3d.read(packetWrapper);
        return new DebugGameEventInfo(resourceLocation, vector3d);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, DebugGameEventInfo debugGameEventInfo) {
        ResourceLocation.write(packetWrapper, debugGameEventInfo.event);
        Vector3d.write(packetWrapper, debugGameEventInfo.pos);
    }

    public ResourceLocation getEvent() {
        return this.event;
    }

    public Vector3d getPos() {
        return this.pos;
    }
}
