package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.BlockBoundingBox;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public final class DebugStructureInfos {

    private final List<DebugStructureInfos.DebugStructureInfo> infos;

    public DebugStructureInfos(List<DebugStructureInfos.DebugStructureInfo> infos) {
        this.infos = infos;
    }

    public static DebugStructureInfos read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(DebugStructureInfos.DebugStructureInfo::read);
        return new DebugStructureInfos(values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, DebugStructureInfos debugStructureInfos) {
        packetWrapper.writeList(
                debugStructureInfos.infos, DebugStructureInfos.DebugStructureInfo::write);
    }

    public List<DebugStructureInfos.DebugStructureInfo> getInfos() {
        return this.infos;
    }

    public static final class DebugStructureInfo {

        private final BlockBoundingBox boundingBox;

        private final List<DebugStructureInfos.DebugStructureInfo.Piece> pieces;

        public DebugStructureInfo(
                BlockBoundingBox blockBoundingBox,
                List<DebugStructureInfos.DebugStructureInfo.Piece> pieces) {
            this.boundingBox = blockBoundingBox;
            this.pieces = pieces;
        }

        public static DebugStructureInfos.DebugStructureInfo read(PacketWrapper<?> packetWrapper) {
            BlockBoundingBox blockBoundingBox = BlockBoundingBox.read(packetWrapper);
            List values =
                    packetWrapper.readList(DebugStructureInfos.DebugStructureInfo.Piece::read);
            return new DebugStructureInfos.DebugStructureInfo(blockBoundingBox, values);
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                DebugStructureInfos.DebugStructureInfo debugStructureInfo) {
            BlockBoundingBox.write(packetWrapper, debugStructureInfo.boundingBox);
            packetWrapper.writeList(
                    debugStructureInfo.pieces, DebugStructureInfos.DebugStructureInfo.Piece::write);
        }

        public BlockBoundingBox getBoundingBox() {
            return this.boundingBox;
        }

        public List<DebugStructureInfos.DebugStructureInfo.Piece> getPieces() {
            return this.pieces;
        }

        public static final class Piece {

            private final BlockBoundingBox boundingBox;

            private final boolean start;

            public Piece(BlockBoundingBox blockBoundingBox, boolean start) {
                this.boundingBox = blockBoundingBox;
                this.start = start;
            }

            public static DebugStructureInfos.DebugStructureInfo.Piece read(
                    PacketWrapper<?> packetWrapper) {
                BlockBoundingBox blockBoundingBox = BlockBoundingBox.read(packetWrapper);
                boolean matches = packetWrapper.readBoolean();
                return new DebugStructureInfos.DebugStructureInfo.Piece(blockBoundingBox, matches);
            }

            public static void write(
                    PacketWrapper<?> packetWrapper,
                    DebugStructureInfos.DebugStructureInfo.Piece piece) {
                BlockBoundingBox.write(packetWrapper, piece.boundingBox);
                packetWrapper.writeBoolean(piece.start);
            }

            public BlockBoundingBox getBoundingBox() {
                return this.boundingBox;
            }

            public boolean isStart() {
                return this.start;
            }
        }
    }
}
