package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.path;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DebugNode {

    private final Vector3i pos;

    private final float walkedDistance;

    private final float costMalus;

    private final boolean closed;

    private final DebugPathType type;

    private final float f;

    public DebugNode(
            Vector3i vector3i,
            float walkedDistance,
            float costMalus,
            boolean closed,
            DebugPathType debugPathType,
            float f) {
        this.pos = vector3i;
        this.walkedDistance = walkedDistance;
        this.costMalus = costMalus;
        this.closed = closed;
        this.type = debugPathType;
        this.f = f;
    }

    public static DebugNode read(PacketWrapper<?> packetWrapper) {
        Vector3i vector3i =
                new Vector3i(
                        packetWrapper.readInt(), packetWrapper.readInt(), packetWrapper.readInt());
        float value = packetWrapper.readFloat();
        float value2 = packetWrapper.readFloat();
        boolean matches = packetWrapper.readBoolean();
        DebugPathType debugPathType = packetWrapper.readEnum(DebugPathType.values());
        float value3 = packetWrapper.readFloat();
        return new DebugNode(vector3i, value, value2, matches, debugPathType, value3);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugNode debugNode) {
        packetWrapper.writeInt(debugNode.pos.x);
        packetWrapper.writeInt(debugNode.pos.y);
        packetWrapper.writeInt(debugNode.pos.z);
        packetWrapper.writeFloat(debugNode.walkedDistance);
        packetWrapper.writeFloat(debugNode.costMalus);
        packetWrapper.writeBoolean(debugNode.closed);
        packetWrapper.writeEnum(debugNode.type);
        packetWrapper.writeFloat(debugNode.f);
    }

    public Vector3i getPos() {
        return this.pos;
    }

    public float getWalkedDistance() {
        return this.walkedDistance;
    }

    public float getCostMalus() {
        return this.costMalus;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public DebugPathType getType() {
        return this.type;
    }

    public float getF() {
        return this.f;
    }
}
