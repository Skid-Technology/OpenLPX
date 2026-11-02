package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.mapdecoration.MapDecorationType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.mapdecoration.MapDecorationTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WrapperPlayServerMapData extends PacketWrapper<WrapperPlayServerMapData> {

    private int mapId;

    private byte scale;

    private boolean trackingPosition;

    private boolean locked;

    @Nullable private List<WrapperPlayServerMapData.MapDecoration> decorations;

    private int columns;

    private int rows;

    private int x;

    private int z;

    private byte @Nullable [] data;

    public WrapperPlayServerMapData(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerMapData(
            int value, byte value2, @Nullable List<WrapperPlayServerMapData.MapDecoration> values) {
        this(value, value2, false, values);
    }

    public WrapperPlayServerMapData(
            int value,
            byte value2,
            boolean matches,
            @Nullable List<WrapperPlayServerMapData.MapDecoration> values) {
        this(value, value2, false, matches, values, 0, 0, 0, 0, null);
    }

    public WrapperPlayServerMapData(
            int mapId,
            byte scale,
            boolean trackingPosition,
            boolean locked,
            @Nullable List<WrapperPlayServerMapData.MapDecoration> decorations,
            int columns,
            int rows,
            int x,
            int z,
            byte @Nullable [] data) {
        super(PacketType.Play.Server.MAP_DATA);
        this.mapId = mapId;
        this.scale = scale;
        this.trackingPosition = trackingPosition;
        this.locked = locked;
        this.decorations = decorations;
        this.columns = columns;
        this.rows = rows;
        this.x = x;
        this.z = z;
        this.data = data;
    }

    @Override
    public void read() {
        this.mapId = this.readVarInt();
        this.scale = this.readByte();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)
                && this.serverVersion.isOlderThan(ServerVersion.V_1_17)) {
            this.trackingPosition = this.readBoolean();
        }
        this.locked =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14) && this.readBoolean();
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_17) || this.readBoolean()) {
            this.decorations = this.readList(WrapperPlayServerMapData.MapDecoration::read);
        }
        this.columns = this.readUnsignedByte();
        if (this.columns > 0) {
            this.rows = this.readUnsignedByte();
            this.x = this.readUnsignedByte();
            this.z = this.readUnsignedByte();
            this.data = this.readByteArray();
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.mapId);
        this.writeByte(this.scale);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)
                && this.serverVersion.isOlderThan(ServerVersion.V_1_17)) {
            this.writeBoolean(this.trackingPosition);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
            this.writeBoolean(this.locked);
        }
        if (this.decorations != null) {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
                this.writeBoolean(true);
            }
            this.writeList(this.decorations, WrapperPlayServerMapData.MapDecoration::write);
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            this.writeBoolean(false);
        } else {
            this.writeVarInt(0);
        }
        if (this.data != null) {
            this.writeByte(this.columns);
            if (this.columns > 0) {
                this.writeByte(this.rows);
                this.writeByte(this.x);
                this.writeByte(this.z);
                this.writeByteArray(this.data);
            }
        } else {
            this.writeByte(0);
        }
    }

    public void copy(WrapperPlayServerMapData wrapperPlayServerMapData) {
        this.mapId = wrapperPlayServerMapData.mapId;
        this.scale = wrapperPlayServerMapData.scale;
        this.trackingPosition = wrapperPlayServerMapData.trackingPosition;
        this.locked = wrapperPlayServerMapData.locked;
        this.decorations = wrapperPlayServerMapData.decorations;
        this.columns = wrapperPlayServerMapData.columns;
        this.rows = wrapperPlayServerMapData.rows;
        this.x = wrapperPlayServerMapData.x;
        this.z = wrapperPlayServerMapData.z;
        this.data = wrapperPlayServerMapData.data;
    }

    public int getMapId() {
        return this.mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public byte getScale() {
        return this.scale;
    }

    public void setScale(byte scale) {
        this.scale = scale;
    }

    public boolean isTrackingPosition() {
        return this.trackingPosition;
    }

    public void setTrackingPosition(boolean trackingPosition) {
        this.trackingPosition = trackingPosition;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Nullable
    public List<WrapperPlayServerMapData.MapDecoration> getDecorations() {
        return this.decorations;
    }

    public void setDecorations(@Nullable List<WrapperPlayServerMapData.MapDecoration> decorations) {
        this.decorations = decorations;
    }

    public int getColumns() {
        return this.columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public byte @Nullable [] getData() {
        return this.data;
    }

    public void setData(byte @Nullable [] data) {
        this.data = data;
    }

    public static class MapDecoration {

        private MapDecorationType type;

        private byte x;

        private byte y;

        private byte direction;

        @Nullable private Component displayName;

        public MapDecoration(
                MapDecorationType mapDecorationType,
                byte x,
                byte y,
                byte direction,
                @Nullable Component component) {
            this.type = mapDecorationType;
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.displayName = component;
        }

        public static WrapperPlayServerMapData.MapDecoration read(PacketWrapper<?> packetWrapper) {
            boolean matches;
            byte value =
                    (matches =
                                    packetWrapper
                                            .getServerVersion()
                                            .isNewerThanOrEquals(ServerVersion.V_1_13))
                            ? 0
                            : packetWrapper.readByte();
            MapDecorationType mapDecorationType =
                    !matches
                            ? MapDecorationTypes.getById(
                                    packetWrapper.getServerVersion().toClientVersion(),
                                    value >> 4 & 15)
                            : packetWrapper.readMappedEntity(MapDecorationTypes::getById);
            byte value2 = packetWrapper.readByte();
            byte value3 = packetWrapper.readByte();
            byte value4 = (byte) ((matches ? packetWrapper.readByte() : value) & 15);
            Component component =
                    matches ? packetWrapper.readOptional(PacketWrapper::readComponent) : null;
            return new WrapperPlayServerMapData.MapDecoration(
                    mapDecorationType, value2, value3, value4, component);
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                WrapperPlayServerMapData.MapDecoration mapDecoration) {
            boolean matches;
            if (matches =
                    packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_13)) {
                packetWrapper.writeMappedEntity(mapDecoration.getType());
            } else {
                int id =
                        mapDecoration
                                .getType()
                                .getId(packetWrapper.getServerVersion().toClientVersion());
                packetWrapper.writeByte((id & 15) << 4 | mapDecoration.getDirection() & 15);
            }
            packetWrapper.writeByte(mapDecoration.getX());
            packetWrapper.writeByte(mapDecoration.getY());
            if (matches) {
                packetWrapper.writeByte(mapDecoration.getDirection());
                packetWrapper.writeOptional(
                        mapDecoration.getDisplayName(), PacketWrapper::writeComponent);
            }
        }

        public MapDecorationType getType() {
            return this.type;
        }

        public void setType(MapDecorationType mapDecorationType) {
            this.type = mapDecorationType;
        }

        public byte getX() {
            return this.x;
        }

        public void setX(byte x) {
            this.x = x;
        }

        public byte getY() {
            return this.y;
        }

        public void setY(byte y) {
            this.y = y;
        }

        public byte getDirection() {
            return this.direction;
        }

        public void setDirection(byte direction) {
            this.direction = direction;
        }

        @Nullable
        public Component getDisplayName() {
            return this.displayName;
        }

        public void setDisplayName(@Nullable Component component) {
            this.displayName = component;
        }
    }
}
