package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.JointType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayClientUpdateJigsawBlock
        extends PacketWrapper<WrapperPlayClientUpdateJigsawBlock> {

    private Vector3i position;

    private ResourceLocation name;

    @Nullable private ResourceLocation target;

    private ResourceLocation pool;

    private String finalState;

    @Nullable private JointType jointType;

    private int selectionPriority;

    private int placementPriority;

    public WrapperPlayClientUpdateJigsawBlock(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientUpdateJigsawBlock(
            Vector3i vector3i,
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2,
            String text) {
        this(vector3i, resourceLocation, null, resourceLocation2, text, null);
    }

    public WrapperPlayClientUpdateJigsawBlock(
            Vector3i vector3i,
            ResourceLocation resourceLocation,
            @Nullable ResourceLocation resourceLocation2,
            ResourceLocation resourceLocation3,
            String text,
            @Nullable JointType jointType) {
        this(
                vector3i,
                resourceLocation,
                resourceLocation2,
                resourceLocation3,
                text,
                jointType,
                0,
                0);
    }

    public WrapperPlayClientUpdateJigsawBlock(
            Vector3i vector3i,
            ResourceLocation resourceLocation,
            @Nullable ResourceLocation resourceLocation2,
            ResourceLocation resourceLocation3,
            String finalState,
            @Nullable JointType jointType2,
            int selectionPriority,
            int placementPriority) {
        super(PacketType.Play.Client.UPDATE_JIGSAW_BLOCK);
        this.position = vector3i;
        this.name = resourceLocation;
        this.target = resourceLocation2;
        this.pool = resourceLocation3;
        this.finalState = finalState;
        this.jointType = jointType2;
        this.selectionPriority = selectionPriority;
        this.placementPriority = placementPriority;
    }

    @Override
    public void read() {
        this.position = this.readBlockPosition();
        this.name = this.readIdentifier();
        if (this.v1_16()) {
            this.target = this.readIdentifier();
        }
        this.pool = this.readIdentifier();
        this.finalState = this.readString();
        if (this.v1_16()) {
            this.jointType = JointType.byName(this.readString()).orElse(JointType.ALIGNED);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            this.selectionPriority = this.readVarInt();
            this.placementPriority = this.readVarInt();
        }
    }

    @Override
    public void write() {
        this.writeBlockPosition(this.position);
        this.writeIdentifier(this.name);
        if (this.v1_16()) {
            this.writeIdentifier(this.target);
        }
        this.writeIdentifier(this.pool);
        this.writeString(this.finalState);
        if (this.v1_16()) {
            this.writeString(this.jointType.getSerializedName());
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            this.writeVarInt(this.selectionPriority);
            this.writeVarInt(this.placementPriority);
        }
    }

    public void copy(WrapperPlayClientUpdateJigsawBlock wrapperPlayClientUpdateJigsawBlock) {
        this.position = wrapperPlayClientUpdateJigsawBlock.position;
        this.name = wrapperPlayClientUpdateJigsawBlock.name;
        this.target = wrapperPlayClientUpdateJigsawBlock.target;
        this.pool = wrapperPlayClientUpdateJigsawBlock.pool;
        this.finalState = wrapperPlayClientUpdateJigsawBlock.finalState;
        this.jointType = wrapperPlayClientUpdateJigsawBlock.jointType;
        this.selectionPriority = wrapperPlayClientUpdateJigsawBlock.selectionPriority;
        this.placementPriority = wrapperPlayClientUpdateJigsawBlock.placementPriority;
    }

    private boolean v1_16() {
        return this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16);
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    public ResourceLocation getName() {
        return this.name;
    }

    public void setName(ResourceLocation resourceLocation) {
        this.name = resourceLocation;
    }

    public Optional<ResourceLocation> getTarget() {
        return Optional.ofNullable(this.target);
    }

    public void setTarget(@Nullable ResourceLocation resourceLocation) {
        this.target = resourceLocation;
    }

    public ResourceLocation getPool() {
        return this.pool;
    }

    public void setPool(ResourceLocation resourceLocation) {
        this.pool = resourceLocation;
    }

    public String getFinalState() {
        return this.finalState;
    }

    public void setFinalState(String finalState) {
        this.finalState = finalState;
    }

    public Optional<JointType> getJointType() {
        return Optional.ofNullable(this.jointType);
    }

    public void setJointType(@Nullable JointType jointType2) {
        this.jointType = jointType2;
    }

    public int getSelectionPriority() {
        return this.selectionPriority;
    }

    public void setSelectionPriority(int selectionPriority) {
        this.selectionPriority = selectionPriority;
    }

    public int getPlacementPriority() {
        return this.placementPriority;
    }

    public void setPlacementPriority(int placementPriority) {
        this.placementPriority = placementPriority;
    }
}
