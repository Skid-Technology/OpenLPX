package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.StructureMirror;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.StructureRotation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientSetStructureBlock
        extends PacketWrapper<WrapperPlayClientSetStructureBlock> {

    private Vector3i position;

    private WrapperPlayClientSetStructureBlock.UpdateType updateType;

    private WrapperPlayClientSetStructureBlock.StructureMode mode;

    private String name;

    private Vector3i offset;

    private Vector3i size;

    private StructureMirror mirror;

    private StructureRotation rotation;

    private String data;

    private boolean ignoreEntities;

    private boolean strict;

    private boolean showAir;

    private boolean showBoundingBox;

    private float integrity;

    private long seed;

    public WrapperPlayClientSetStructureBlock(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSetStructureBlock(
            Vector3i vector3i,
            WrapperPlayClientSetStructureBlock.UpdateType updateType,
            WrapperPlayClientSetStructureBlock.StructureMode structureMode,
            String text,
            Vector3i vector3i2,
            Vector3i vector3i3,
            StructureMirror structureMirror,
            StructureRotation structureRotation,
            String text2,
            boolean matches,
            boolean matches2,
            boolean matches3,
            float value,
            long value2) {
        this(
                vector3i,
                updateType,
                structureMode,
                text,
                vector3i2,
                vector3i3,
                structureMirror,
                structureRotation,
                text2,
                matches,
                false,
                matches2,
                matches3,
                value,
                value2);
    }

    public WrapperPlayClientSetStructureBlock(
            Vector3i vector3i,
            WrapperPlayClientSetStructureBlock.UpdateType updateType2,
            WrapperPlayClientSetStructureBlock.StructureMode structureMode,
            String name,
            Vector3i vector3i2,
            Vector3i vector3i3,
            StructureMirror structureMirror,
            StructureRotation structureRotation,
            String data,
            boolean ignoreEntities,
            boolean strict,
            boolean showAir,
            boolean showBoundingBox,
            float integrity,
            long seed) {
        super(PacketType.Play.Client.UPDATE_STRUCTURE_BLOCK);
        this.position = vector3i;
        this.updateType = updateType2;
        this.mode = structureMode;
        this.name = name;
        this.offset = vector3i2;
        this.size = vector3i3;
        this.mirror = structureMirror;
        this.rotation = structureRotation;
        this.data = data;
        this.ignoreEntities = ignoreEntities;
        this.strict = strict;
        this.showAir = showAir;
        this.showBoundingBox = showBoundingBox;
        this.integrity = integrity;
        this.seed = seed;
    }

    @Override
    public void read() {
        this.position = this.readBlockPosition();
        this.updateType = this.readEnum(WrapperPlayClientSetStructureBlock.UpdateType.class);
        this.mode = this.readEnum(WrapperPlayClientSetStructureBlock.StructureMode.class);
        this.name = this.readString();
        int value = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2) ? 48 : 32;
        this.offset =
                new Vector3i(
                        MathUtil.clamp(this.readByte(), -value, value),
                        MathUtil.clamp(this.readByte(), -value, value),
                        MathUtil.clamp(this.readByte(), -value, value));
        this.size =
                new Vector3i(
                        MathUtil.clamp(this.readByte(), 0, value),
                        MathUtil.clamp(this.readByte(), 0, value),
                        MathUtil.clamp(this.readByte(), 0, value));
        this.mirror = this.readEnum(StructureMirror.class);
        this.rotation = this.readEnum(StructureRotation.class);
        this.data =
                this.readString(
                        this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17) ? 128 : 12);
        this.integrity = MathUtil.clamp(this.readFloat(), 0.0F, 1.0F);
        this.seed = this.readVarLong();
        byte value2 = this.readByte();
        this.ignoreEntities = (value2 & 1) != 0;
        this.strict = (value2 & 8) != 0;
        this.showAir = (value2 & 2) != 0;
        this.showBoundingBox = (value2 & 4) != 0;
    }

    @Override
    public void write() {
        this.writeBlockPosition(this.position);
        this.writeEnum(this.updateType);
        this.writeEnum(this.mode);
        this.writeString(this.name);
        this.writeByte(this.offset.x);
        this.writeByte(this.offset.y);
        this.writeByte(this.offset.z);
        this.writeByte(this.size.x);
        this.writeByte(this.size.y);
        this.writeByte(this.size.z);
        this.writeEnum(this.mirror);
        this.writeEnum(this.rotation);
        this.writeString(this.data);
        this.writeFloat(this.integrity);
        this.writeVarLong(this.seed);
        this.writeByte(
                0
                        | (this.ignoreEntities ? 1 : 0)
                        | (this.showAir ? 2 : 0)
                        | (this.showBoundingBox ? 4 : 0)
                        | (this.strict ? 8 : 0));
    }

    public void copy(WrapperPlayClientSetStructureBlock wrapperPlayClientSetStructureBlock) {
        this.position = wrapperPlayClientSetStructureBlock.position;
        this.updateType = wrapperPlayClientSetStructureBlock.updateType;
        this.mode = wrapperPlayClientSetStructureBlock.mode;
        this.name = wrapperPlayClientSetStructureBlock.name;
        this.offset = wrapperPlayClientSetStructureBlock.offset;
        this.size = wrapperPlayClientSetStructureBlock.size;
        this.mirror = wrapperPlayClientSetStructureBlock.mirror;
        this.rotation = wrapperPlayClientSetStructureBlock.rotation;
        this.data = wrapperPlayClientSetStructureBlock.data;
        this.ignoreEntities = wrapperPlayClientSetStructureBlock.ignoreEntities;
        this.strict = wrapperPlayClientSetStructureBlock.strict;
        this.showAir = wrapperPlayClientSetStructureBlock.showAir;
        this.showBoundingBox = wrapperPlayClientSetStructureBlock.showBoundingBox;
        this.integrity = wrapperPlayClientSetStructureBlock.integrity;
        this.seed = wrapperPlayClientSetStructureBlock.seed;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    public WrapperPlayClientSetStructureBlock.UpdateType getUpdateType() {
        return this.updateType;
    }

    public void setUpdateType(WrapperPlayClientSetStructureBlock.UpdateType updateType2) {
        this.updateType = updateType2;
    }

    public WrapperPlayClientSetStructureBlock.StructureMode getMode() {
        return this.mode;
    }

    public void setMode(WrapperPlayClientSetStructureBlock.StructureMode structureMode) {
        this.mode = structureMode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector3i getOffset() {
        return this.offset;
    }

    public void setOffset(Vector3i vector3i) {
        this.offset = vector3i;
    }

    public Vector3i getSize() {
        return this.size;
    }

    public void setSize(Vector3i vector3i) {
        this.size = vector3i;
    }

    public StructureMirror getMirror() {
        return this.mirror;
    }

    public void setMirror(StructureMirror structureMirror) {
        this.mirror = structureMirror;
    }

    public StructureRotation getRotation() {
        return this.rotation;
    }

    public void setRotation(StructureRotation structureRotation) {
        this.rotation = structureRotation;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isIgnoreEntities() {
        return this.ignoreEntities;
    }

    public void setIgnoreEntities(boolean ignoreEntities) {
        this.ignoreEntities = ignoreEntities;
    }

    public boolean isStrict() {
        return this.strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    public boolean isShowAir() {
        return this.showAir;
    }

    public void setShowAir(boolean showAir) {
        this.showAir = showAir;
    }

    public boolean isShowBoundingBox() {
        return this.showBoundingBox;
    }

    public void setShowBoundingBox(boolean showBoundingBox) {
        this.showBoundingBox = showBoundingBox;
    }

    public float getIntegrity() {
        return this.integrity;
    }

    public void setIntegrity(float integrity) {
        this.integrity = integrity;
    }

    public long getSeed() {
        return this.seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public enum StructureMode {
        SAVE,
        LOAD,
        CORNER,
        DATA
    }

    public enum UpdateType {
        UPDATE_DATA,
        SAVE_AREA,
        LOAD_AREA,
        SCAN_AREA
    }
}
