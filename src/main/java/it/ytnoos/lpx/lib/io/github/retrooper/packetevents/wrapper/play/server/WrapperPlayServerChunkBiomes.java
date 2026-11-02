package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.Biome;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette.DataPalette;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette.PaletteType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette.SingletonPalette;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.storage.BitStorage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector2i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WrapperPlayServerChunkBiomes extends PacketWrapper<WrapperPlayServerChunkBiomes> {

    private Map<Vector2i, WrapperPlayServerChunkBiomes.ChunkBiomeData> chunks;

    public WrapperPlayServerChunkBiomes(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerChunkBiomes(
            Map<Vector2i, WrapperPlayServerChunkBiomes.ChunkBiomeData> entries) {
        super(PacketType.Play.Server.CHUNK_BIOMES);
        this.chunks = entries;
    }

    @Override
    public void read() {
        this.chunks =
                this.readMap(Vector2i::read, WrapperPlayServerChunkBiomes.ChunkBiomeData::read);
    }

    @Override
    public void write() {
        this.writeMap(
                this.chunks, Vector2i::write, WrapperPlayServerChunkBiomes.ChunkBiomeData::write);
    }

    public void copy(WrapperPlayServerChunkBiomes wrapperPlayServerChunkBiomes) {
        this.chunks = wrapperPlayServerChunkBiomes.chunks;
    }

    public Map<Vector2i, WrapperPlayServerChunkBiomes.ChunkBiomeData> getChunks() {
        return this.chunks;
    }

    public WrapperPlayServerChunkBiomes.ChunkBiomeData getChunk(Vector2i vector2i) {
        return this.chunks.get(vector2i);
    }

    public static class ChunkBiomeData {

        private final List<DataPalette> sections;

        private ChunkBiomeData(List<DataPalette> sections) {
            this.sections = sections;
        }

        public static WrapperPlayServerChunkBiomes.ChunkBiomeData createWithRepeatingPalette(
                DataPalette dataPalette, int value) {
            WrapperPlayServerChunkBiomes.ChunkBiomeData chunkBiomeData =
                    new WrapperPlayServerChunkBiomes.ChunkBiomeData(new ArrayList<>());
            for (int index = 0; index < value; index++) {
                chunkBiomeData.sections.add(dataPalette);
            }
            return chunkBiomeData;
        }

        public static WrapperPlayServerChunkBiomes.ChunkBiomeData createWithSingleBiome(
                Biome biome, ClientVersion clientVersion, int value) {
            return createWithSingleBiome(biome.getId(clientVersion), value);
        }

        public static WrapperPlayServerChunkBiomes.ChunkBiomeData createWithSingleBiome(
                int value, int value2) {
            return createWithRepeatingPalette(
                    new DataPalette(
                            new SingletonPalette(value),
                            new BitStorage(0, PaletteType.BIOME.getStorageSize()),
                            PaletteType.BIOME),
                    value2);
        }

        public static WrapperPlayServerChunkBiomes.ChunkBiomeData read(
                PacketWrapper<?> packetWrapper) {
            int value =
                    packetWrapper.readVarInt() + ByteBufHelper.readerIndex(packetWrapper.buffer);
            ArrayList<DataPalette> values = new ArrayList<>();
            while (ByteBufHelper.readerIndex(packetWrapper.buffer) < value) {
                values.add(PaletteType.BIOME.read(packetWrapper));
            }
            if (ByteBufHelper.readerIndex(packetWrapper.buffer) != value) {
                throw new IllegalStateException(
                        "Unexpected buffer position, expected "
                                + value
                                + ", received "
                                + packetWrapper.buffer);
            } else {
                return new WrapperPlayServerChunkBiomes.ChunkBiomeData(values);
            }
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                WrapperPlayServerChunkBiomes.ChunkBiomeData chunkBiomeData) {
            int value = ByteBufHelper.writerIndex(packetWrapper.buffer);
            for (DataPalette dataPalette : chunkBiomeData.sections) {
                PaletteType.write(packetWrapper, dataPalette);
            }
            int value2 = ByteBufHelper.writerIndex(packetWrapper.buffer) - value;
            ByteBufHelper.writerIndex(packetWrapper.buffer, value);
            packetWrapper.writeVarInt(value2);
            for (DataPalette dataPalette2 : chunkBiomeData.sections) {
                PaletteType.write(packetWrapper, dataPalette2);
            }
        }
    }
}
