package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

public class WrapperPlayServerMultiBlockChange
        extends PacketWrapper<WrapperPlayServerMultiBlockChange> {

    private Vector3i chunkPosition;

    private Boolean trustEdges;

    private WrapperPlayServerMultiBlockChange.EncodedBlock[] blockData;

    public WrapperPlayServerMultiBlockChange(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerMultiBlockChange(
            Vector3i vector3i,
            @Nullable Boolean trustEdges,
            WrapperPlayServerMultiBlockChange.EncodedBlock[] encodedBlocks) {
        super(PacketType.Play.Server.MULTI_BLOCK_CHANGE);
        this.chunkPosition = vector3i;
        this.trustEdges = trustEdges;
        this.blockData = encodedBlocks;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            long value;
            int value2 = (int) ((value = this.readLong()) >> 42);
            int value3 = (int) (value << 44 >> 44);
            int value4 = (int) (value << 22 >> 42);
            this.chunkPosition = new Vector3i(value2, value3, value4);
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_4)) {
                this.trustEdges = this.readBoolean();
            }
            this.blockData = new WrapperPlayServerMultiBlockChange.EncodedBlock[this.readVarInt()];
            for (int index = 0; index < this.blockData.length; index++) {
                this.blockData[index] =
                        new WrapperPlayServerMultiBlockChange.EncodedBlock(
                                this.chunkPosition, this.readVarLong());
            }
        } else {
            int value5 = this.readInt();
            int value6 = this.readInt();
            this.chunkPosition = new Vector3i(value5, 0, value6);
            int value7 = this.readVarInt();
            this.blockData = new WrapperPlayServerMultiBlockChange.EncodedBlock[value7];
            for (int index2 = 0; index2 < value7; index2++) {
                short value8 = this.readShort();
                int value9 = (value5 << 4) + (value8 >> 12 & 15);
                int value10 = value8 & 255;
                int value11 = (value6 << 4) + (value8 >> 8 & 15);
                int value12 = this.readVarInt();
                this.blockData[index2] =
                        new WrapperPlayServerMultiBlockChange.EncodedBlock(
                                value12, value9, value10, value11);
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            long value =
                    0L
                            | (this.chunkPosition.getX() & 4194303L) << 42
                            | (this.chunkPosition.getZ() & 4194303L) << 20;
            this.writeLong(value | this.chunkPosition.getY() & 1048575L);
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_4)) {
                this.writeBoolean(Boolean.TRUE.equals(this.trustEdges));
            }
            this.writeVarInt(this.blockData.length);
            WrapperPlayServerMultiBlockChange.EncodedBlock[] encodedBlocks = this.blockData;
            int value2 = this.blockData.length;
            for (int index = 0; index < value2; index++) {
                WrapperPlayServerMultiBlockChange.EncodedBlock encodedBlock = encodedBlocks[index];
                this.writeVarLong(encodedBlock.toLong());
            }
        } else {
            this.writeInt(this.chunkPosition.getX());
            this.writeInt(this.chunkPosition.getZ());
            this.writeVarInt(this.blockData.length);
            WrapperPlayServerMultiBlockChange.EncodedBlock[] encodedBlocks2 = this.blockData;
            int value3 = this.blockData.length;
            for (int index2 = 0; index2 < value3; index2++) {
                WrapperPlayServerMultiBlockChange.EncodedBlock encodedBlock2;
                int value4 = (encodedBlock2 = encodedBlocks2[index2]).getX() & 15;
                int value5 = encodedBlock2.getZ() & 15;
                short value6 = (short) (value4 << 12 | value5 << 8 | encodedBlock2.getY());
                this.writeShort(value6);
                this.writeVarInt(encodedBlock2.getBlockId());
            }
        }
    }

    public void copy(WrapperPlayServerMultiBlockChange wrapperPlayServerMultiBlockChange) {
        this.chunkPosition = wrapperPlayServerMultiBlockChange.chunkPosition;
        this.trustEdges = wrapperPlayServerMultiBlockChange.trustEdges;
        this.blockData = wrapperPlayServerMultiBlockChange.blockData;
    }

    public Vector3i getChunkPosition() {
        return this.chunkPosition;
    }

    public void setChunkPosition(Vector3i vector3i) {
        this.chunkPosition = vector3i;
    }

    public boolean getTrustEdges() {
        return Boolean.TRUE.equals(this.trustEdges);
    }

    public void setTrustEdges(Boolean trustEdges) {
        this.trustEdges = trustEdges;
    }

    public WrapperPlayServerMultiBlockChange.EncodedBlock[] getBlocks() {
        return this.blockData;
    }

    public void setBlocks(WrapperPlayServerMultiBlockChange.EncodedBlock[] encodedBlocks) {
        this.blockData = encodedBlocks;
    }

    public static class EncodedBlock {

        private int blockID;

        private int x;

        private int y;

        private int z;

        public EncodedBlock(int blockID, int x, int y, int z) {
            this.blockID = blockID;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public EncodedBlock(
                WrappedBlockState wrappedBlockState, int value, int value2, int value3) {
            this(wrappedBlockState.getGlobalId(), value, value2, value3);
        }

        public EncodedBlock(Vector3i vector3i, long value) {
            short value2 = (short) (value & 4095L);
            this.x = (vector3i.getX() << 4) + (value2 >>> 8 & 15);
            this.y = (vector3i.getY() << 4) + (value2 & 15);
            this.z = (vector3i.getZ() << 4) + (value2 >>> 4 & 15);
            this.blockID = (int) (value >>> 12);
        }

        public long toLong() {
            return (long) this.blockID << 12
                    | (this.x & 15) << 8
                    | (this.z & 15) << 4
                    | this.y & 15;
        }

        public int getBlockId() {
            return this.blockID;
        }

        public void setBlockId(int blockID) {
            this.blockID = blockID;
        }

        public WrappedBlockState getBlockState(ClientVersion clientVersion) {
            return WrappedBlockState.getByGlobalId(clientVersion, this.blockID);
        }

        public void setBlockState(WrappedBlockState wrappedBlockState) {
            this.blockID = wrappedBlockState.getGlobalId();
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getZ() {
            return this.z;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setZ(int z) {
            this.z = z;
        }
    }
}
