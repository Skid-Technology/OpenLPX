package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.poi.PoiType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.poi.PoiTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DebugPoiInfo {

    private final Vector3i pos;

    private final PoiType poiType;

    private final int freeTicketCount;

    public DebugPoiInfo(Vector3i vector3i, PoiType poiType2, int freeTicketCount) {
        this.pos = vector3i;
        this.poiType = poiType2;
        this.freeTicketCount = freeTicketCount;
    }

    public static DebugPoiInfo read(PacketWrapper<?> packetWrapper) {
        Vector3i vector3i = packetWrapper.readBlockPosition();
        PoiType poiType = packetWrapper.readMappedEntity(PoiTypes.getRegistry());
        int value = packetWrapper.readVarInt();
        return new DebugPoiInfo(vector3i, poiType, value);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugPoiInfo debugPoiInfo) {
        packetWrapper.writeBlockPosition(debugPoiInfo.pos);
        packetWrapper.writeMappedEntity(debugPoiInfo.poiType);
        packetWrapper.writeVarInt(debugPoiInfo.freeTicketCount);
    }

    public Vector3i getPos() {
        return this.pos;
    }

    public PoiType getPoiType() {
        return this.poiType;
    }

    public int getFreeTicketCount() {
        return this.freeTicketCount;
    }
}
