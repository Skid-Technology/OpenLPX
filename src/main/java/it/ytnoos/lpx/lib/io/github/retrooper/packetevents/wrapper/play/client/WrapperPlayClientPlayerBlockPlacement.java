package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.InteractionHand;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.BlockFace;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3f;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Optional;

public class WrapperPlayClientPlayerBlockPlacement
        extends PacketWrapper<WrapperPlayClientPlayerBlockPlacement> {

    private InteractionHand interactionHand;

    private Vector3i blockPosition;

    private int faceId;

    private BlockFace face;

    private Vector3f cursorPosition;

    private Optional<ItemStack> itemStack;

    private Optional<Boolean> insideBlock;

    private Optional<Boolean> worldBorderHit;

    private int sequence;

    public WrapperPlayClientPlayerBlockPlacement(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPlayerBlockPlacement(
            InteractionHand interactionHand,
            Vector3i vector3i,
            BlockFace blockFace,
            Vector3f vector3f,
            ItemStack itemStack,
            Boolean matches,
            int value) {
        this(interactionHand, vector3i, blockFace, vector3f, itemStack, matches, null, value);
    }

    public WrapperPlayClientPlayerBlockPlacement(
            InteractionHand interactionHand2,
            Vector3i vector3i,
            BlockFace blockFace,
            Vector3f vector3f,
            ItemStack itemStack2,
            Boolean matches,
            Boolean matches2,
            int sequence) {
        super(PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT);
        this.interactionHand = interactionHand2;
        this.blockPosition = vector3i;
        this.face = blockFace;
        this.faceId = blockFace.getFaceValue();
        this.cursorPosition = vector3f;
        this.itemStack = Optional.ofNullable(itemStack2);
        this.insideBlock = Optional.ofNullable(matches);
        this.worldBorderHit = Optional.ofNullable(matches2);
        this.sequence = sequence;
    }

    @Override
    public void read() {
        this.itemStack = Optional.empty();
        this.insideBlock = Optional.empty();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
            this.interactionHand = InteractionHand.getById(this.readVarInt());
            this.blockPosition = this.readBlockPosition();
            this.faceId = this.readVarInt();
            this.face = BlockFace.getBlockFaceByValue(this.faceId);
            this.cursorPosition =
                    new Vector3f(this.readFloat(), this.readFloat(), this.readFloat());
            this.insideBlock = Optional.of(this.readBoolean());
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
                    this.worldBorderHit = Optional.of(this.readBoolean());
                }
                this.sequence = this.readVarInt();
            }
        } else {
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
                this.blockPosition =
                        new Vector3i(this.readInt(), this.readUnsignedByte(), this.readInt());
            } else {
                this.blockPosition = this.readBlockPosition();
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                this.faceId = this.readVarInt();
                this.face = BlockFace.getBlockFaceByValue(this.faceId);
                this.interactionHand = InteractionHand.getById(this.readVarInt());
            } else {
                this.faceId = this.readUnsignedByte();
                this.face = BlockFace.getLegacyBlockFaceByValue(this.faceId);
                this.itemStack = Optional.of(this.readItemStack());
                this.interactionHand = InteractionHand.MAIN_HAND;
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11)) {
                this.cursorPosition =
                        new Vector3f(this.readFloat(), this.readFloat(), this.readFloat());
            } else {
                this.cursorPosition =
                        new Vector3f(
                                this.readUnsignedByte() / 16.0F,
                                this.readUnsignedByte() / 16.0F,
                                this.readUnsignedByte() / 16.0F);
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
            this.writeVarInt(this.interactionHand.getId());
            this.writeBlockPosition(this.blockPosition);
            this.writeVarInt(this.faceId);
            this.writeFloat(this.cursorPosition.x);
            this.writeFloat(this.cursorPosition.y);
            this.writeFloat(this.cursorPosition.z);
            this.writeBoolean(this.insideBlock.orElse(false));
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
                    this.writeBoolean(this.worldBorderHit.orElse(false));
                }
                this.writeVarInt(this.sequence);
            }
        } else {
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
                this.writeInt(this.blockPosition.x);
                this.writeByte(this.blockPosition.y);
                this.writeInt(this.blockPosition.z);
            } else {
                this.writeBlockPosition(this.blockPosition);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                this.writeVarInt(this.faceId);
                this.writeVarInt(this.interactionHand.getId());
            } else {
                this.writeByte(this.faceId);
                this.writeItemStack(this.itemStack.orElse(ItemStack.EMPTY));
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11)) {
                this.writeFloat(this.cursorPosition.x);
                this.writeFloat(this.cursorPosition.y);
                this.writeFloat(this.cursorPosition.z);
            } else {
                this.writeByte((int) (this.cursorPosition.x * 16.0F));
                this.writeByte((int) (this.cursorPosition.y * 16.0F));
                this.writeByte((int) (this.cursorPosition.z * 16.0F));
            }
        }
    }

    public void copy(WrapperPlayClientPlayerBlockPlacement wrapperPlayClientPlayerBlockPlacement) {
        this.interactionHand = wrapperPlayClientPlayerBlockPlacement.interactionHand;
        this.blockPosition = wrapperPlayClientPlayerBlockPlacement.blockPosition;
        this.face = wrapperPlayClientPlayerBlockPlacement.face;
        this.faceId = wrapperPlayClientPlayerBlockPlacement.faceId;
        this.cursorPosition = wrapperPlayClientPlayerBlockPlacement.cursorPosition;
        this.itemStack = wrapperPlayClientPlayerBlockPlacement.itemStack;
        this.insideBlock = wrapperPlayClientPlayerBlockPlacement.insideBlock;
        this.worldBorderHit = wrapperPlayClientPlayerBlockPlacement.worldBorderHit;
        this.sequence = wrapperPlayClientPlayerBlockPlacement.sequence;
    }

    public InteractionHand getHand() {
        return this.interactionHand;
    }

    public void setHand(InteractionHand interactionHand2) {
        this.interactionHand = interactionHand2;
    }

    public Vector3i getBlockPosition() {
        return this.blockPosition;
    }

    public void setBlockPosition(Vector3i vector3i) {
        this.blockPosition = vector3i;
    }

    public int getFaceId() {
        return this.faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
        this.face =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)
                        ? BlockFace.getBlockFaceByValue(faceId)
                        : BlockFace.getLegacyBlockFaceByValue(faceId);
    }

    public BlockFace getFace() {
        return this.face;
    }

    public void setFace(BlockFace blockFace) {
        this.face = blockFace;
        this.faceId = blockFace.getFaceValue();
    }

    public Vector3f getCursorPosition() {
        return this.cursorPosition;
    }

    public void setCursorPosition(Vector3f vector3f) {
        this.cursorPosition = vector3f;
    }

    public Optional<ItemStack> getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(Optional<ItemStack> optional) {
        this.itemStack = optional;
    }

    public Optional<Boolean> getInsideBlock() {
        return this.insideBlock != null ? this.insideBlock : Optional.empty();
    }

    public void setInsideBlock(Optional<Boolean> optional) {
        this.insideBlock = optional;
    }

    public Optional<Boolean> getWorldBorderHit() {
        return this.worldBorderHit != null ? this.worldBorderHit : Optional.empty();
    }

    public void setWorldBorderHit(Optional<Boolean> optional) {
        this.worldBorderHit = optional;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
