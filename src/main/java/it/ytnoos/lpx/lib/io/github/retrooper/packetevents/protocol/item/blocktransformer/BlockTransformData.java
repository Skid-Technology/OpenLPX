package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.blocktransformer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Direction;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NullMarked
public final class BlockTransformData {

    public static final NbtCodec<BlockTransformData> CODEC =
            (new NbtMapCodec<BlockTransformData>() {

                        public BlockTransformData decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            BlockStateProvider orThrow =
                                    nBTCompound.getOrThrow(
                                            "block_state_provider",
                                            BlockStateProvider.CODEC,
                                            packetWrapper);
                            Sound or =
                                    nBTCompound.getOr(
                                            "sound",
                                            Sound.CODEC,
                                            Sounds.INTENTIONALLY_EMPTY,
                                            packetWrapper);
                            BlockTransformData.Particle or2 =
                                    nBTCompound.getOr(
                                            "particle",
                                            BlockTransformData.Particle.CODEC,
                                            BlockTransformData.Particle.NONE,
                                            packetWrapper);
                            List<Direction> listOrEmpty =
                                    nBTCompound.getListOrEmpty(
                                            "disallowed_faces", Direction.CODEC, packetWrapper);
                            ResourceLocation orNull =
                                    nBTCompound.getOrNull(
                                            "loot", ResourceLocation.CODEC, packetWrapper);
                            BlockTransformData.DropStrategy or3 =
                                    nBTCompound.getOr(
                                            "drop_strategy",
                                            BlockTransformData.DropStrategy.CODEC,
                                            BlockTransformData.DropStrategy.FROM_MIDDLE,
                                            packetWrapper);
                            boolean or4 =
                                    nBTCompound.getOr(
                                            "update_from_neighbors",
                                            NbtCodecs.BOOLEAN,
                                            true,
                                            packetWrapper);
                            BlockTransformData.TransformType or5 =
                                    nBTCompound.getOr(
                                            "transform_type",
                                            BlockTransformData.TransformType.CODEC,
                                            BlockTransformData.TransformType.SINGLE_BLOCK,
                                            packetWrapper);
                            boolean or6 =
                                    nBTCompound.getOr(
                                            "consume_on_use",
                                            NbtCodecs.BOOLEAN,
                                            true,
                                            packetWrapper);
                            int or7 =
                                    nBTCompound.getOr(
                                            "item_damage_per_use", NbtCodecs.INT, 0, packetWrapper);
                            return new BlockTransformData(
                                    orThrow, or, or2, listOrEmpty, orNull, or3, or4, or5, or6, or7);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                BlockTransformData blockTransformData)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "block_state_provider",
                                    blockTransformData.blockStateProvider,
                                    BlockStateProvider.CODEC,
                                    packetWrapper);
                            if (!Sounds.INTENTIONALLY_EMPTY.equals(blockTransformData.sound)) {
                                nBTCompound.set(
                                        "sound",
                                        blockTransformData.sound,
                                        Sound.CODEC,
                                        packetWrapper);
                            }
                            if (blockTransformData.particle != BlockTransformData.Particle.NONE) {
                                nBTCompound.set(
                                        "particle",
                                        blockTransformData.particle,
                                        BlockTransformData.Particle.CODEC,
                                        packetWrapper);
                            }
                            if (!blockTransformData.disallowedFaces.isEmpty()) {
                                nBTCompound.setList(
                                        "disallowed_faces",
                                        blockTransformData.disallowedFaces,
                                        Direction.CODEC,
                                        packetWrapper);
                            }
                            if (blockTransformData.loot != null) {
                                nBTCompound.set(
                                        "loot",
                                        blockTransformData.loot,
                                        ResourceLocation.CODEC,
                                        packetWrapper);
                            }
                            if (blockTransformData.dropStrategy
                                    != BlockTransformData.DropStrategy.FROM_MIDDLE) {
                                nBTCompound.set(
                                        "drop_strategy",
                                        blockTransformData.dropStrategy,
                                        BlockTransformData.DropStrategy.CODEC,
                                        packetWrapper);
                            }
                            if (!blockTransformData.updateFromNeighbors) {
                                nBTCompound.set(
                                        "update_from_neighbors",
                                        false,
                                        NbtCodecs.BOOLEAN,
                                        packetWrapper);
                            }
                            if (blockTransformData.transformType
                                    != BlockTransformData.TransformType.SINGLE_BLOCK) {
                                nBTCompound.set(
                                        "transform_type",
                                        blockTransformData.transformType,
                                        BlockTransformData.TransformType.CODEC,
                                        packetWrapper);
                            }
                            if (!blockTransformData.consumeOnUse) {
                                nBTCompound.set(
                                        "consume_on_use", false, NbtCodecs.BOOLEAN, packetWrapper);
                            }
                            if (blockTransformData.itemDamagePerUse != 0) {
                                nBTCompound.set(
                                        "item_damage_per_use",
                                        blockTransformData.itemDamagePerUse,
                                        NbtCodecs.INT,
                                        packetWrapper);
                            }
                        }
                    })
                    .codec();

    private final BlockStateProvider blockStateProvider;

    private final Sound sound;

    private final BlockTransformData.Particle particle;

    private final List<Direction> disallowedFaces;

    @Nullable private final ResourceLocation loot;

    private final BlockTransformData.DropStrategy dropStrategy;

    private final boolean updateFromNeighbors;

    private final BlockTransformData.TransformType transformType;

    private final boolean consumeOnUse;

    private final int itemDamagePerUse;

    private BlockTransformData(
            BlockStateProvider blockStateProvider2,
            Sound sound2,
            BlockTransformData.Particle particle2,
            List<Direction> values,
            @Nullable ResourceLocation resourceLocation,
            BlockTransformData.DropStrategy dropStrategy2,
            boolean updateFromNeighbors,
            BlockTransformData.TransformType transformType2,
            boolean consumeOnUse,
            int itemDamagePerUse) {
        this.blockStateProvider = blockStateProvider2;
        this.sound = sound2;
        this.particle = particle2;
        this.disallowedFaces = Collections.unmodifiableList(values);
        this.loot = resourceLocation;
        this.dropStrategy = dropStrategy2;
        this.updateFromNeighbors = updateFromNeighbors;
        this.transformType = transformType2;
        this.consumeOnUse = consumeOnUse;
        this.itemDamagePerUse = itemDamagePerUse;
    }

    public static BlockTransformData.Builder builder(BlockStateProvider blockStateProvider) {
        return new BlockTransformData.Builder(blockStateProvider);
    }

    public BlockStateProvider getBlockStateProvider() {
        return this.blockStateProvider;
    }

    public Sound getSound() {
        return this.sound;
    }

    public BlockTransformData.Particle getParticle() {
        return this.particle;
    }

    public List<Direction> getDisallowedFaces() {
        return this.disallowedFaces;
    }

    @Nullable
    public ResourceLocation getLoot() {
        return this.loot;
    }

    public BlockTransformData.DropStrategy getDropStrategy() {
        return this.dropStrategy;
    }

    public boolean isUpdateFromNeighbors() {
        return this.updateFromNeighbors;
    }

    public BlockTransformData.TransformType getTransformType() {
        return this.transformType;
    }

    public boolean isConsumeOnUse() {
        return this.consumeOnUse;
    }

    public int getItemDamagePerUse() {
        return this.itemDamagePerUse;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (!(result instanceof BlockTransformData)) {
            return false;
        } else {
            BlockTransformData blockTransformData = (BlockTransformData) result;
            if (this.updateFromNeighbors != blockTransformData.updateFromNeighbors) {
                return false;
            } else if (this.consumeOnUse != blockTransformData.consumeOnUse) {
                return false;
            } else if (this.itemDamagePerUse != blockTransformData.itemDamagePerUse) {
                return false;
            } else if (!this.blockStateProvider.equals(blockTransformData.blockStateProvider)) {
                return false;
            } else if (!this.sound.equals(blockTransformData.sound)) {
                return false;
            } else if (this.particle != blockTransformData.particle) {
                return false;
            } else if (!this.disallowedFaces.equals(blockTransformData.disallowedFaces)) {
                return false;
            } else if (!Objects.equals(this.loot, blockTransformData.loot)) {
                return false;
            } else {
                return this.dropStrategy != blockTransformData.dropStrategy
                        ? false
                        : this.transformType == blockTransformData.transformType;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.blockStateProvider,
                this.sound,
                this.particle,
                this.disallowedFaces,
                this.loot,
                this.dropStrategy,
                this.updateFromNeighbors,
                this.transformType,
                this.consumeOnUse,
                this.itemDamagePerUse);
    }

    public static class Builder {

        private final BlockStateProvider blockStateProvider;

        private Sound sound = Sounds.INTENTIONALLY_EMPTY;

        private BlockTransformData.Particle particle = BlockTransformData.Particle.NONE;

        private List<Direction> disallowedFaces = Collections.emptyList();

        @Nullable private ResourceLocation loot;

        private BlockTransformData.DropStrategy dropStrategy =
                BlockTransformData.DropStrategy.FROM_MIDDLE;

        private boolean updateFromNeighbors = true;

        private BlockTransformData.TransformType transformType =
                BlockTransformData.TransformType.SINGLE_BLOCK;

        private boolean consumeOnUse = true;

        private int itemDamagePerUse = 1;

        private Builder(BlockStateProvider blockStateProvider2) {
            this.blockStateProvider = blockStateProvider2;
        }

        public BlockTransformData.Builder setSound(Sound sound2) {
            this.sound = sound2;
            return this;
        }

        public BlockTransformData.Builder setParticle(BlockTransformData.Particle particle2) {
            this.particle = particle2;
            return this;
        }

        public BlockTransformData.Builder setDisallowedFaces(List<Direction> disallowedFaces) {
            this.disallowedFaces = disallowedFaces;
            return this;
        }

        public BlockTransformData.Builder setDisallowedFaces(Direction... direction) {
            this.disallowedFaces = Arrays.asList(direction);
            return this;
        }

        public BlockTransformData.Builder setLoot(@Nullable ResourceLocation resourceLocation) {
            this.loot = resourceLocation;
            return this;
        }

        public BlockTransformData.Builder setDropStrategy(
                BlockTransformData.DropStrategy dropStrategy2) {
            this.dropStrategy = dropStrategy2;
            return this;
        }

        public BlockTransformData.Builder setUpdateFromNeighbors(boolean updateFromNeighbors) {
            this.updateFromNeighbors = updateFromNeighbors;
            return this;
        }

        public BlockTransformData.Builder setTransformType(
                BlockTransformData.TransformType transformType2) {
            this.transformType = transformType2;
            return this;
        }

        public BlockTransformData.Builder setConsumeOnUse(boolean consumeOnUse) {
            this.consumeOnUse = consumeOnUse;
            return this;
        }

        public BlockTransformData.Builder setItemDamagePerUse(int itemDamagePerUse) {
            this.itemDamagePerUse = itemDamagePerUse;
            return this;
        }

        public BlockTransformData build() {
            return new BlockTransformData(
                    this.blockStateProvider,
                    this.sound,
                    this.particle,
                    this.disallowedFaces,
                    this.loot,
                    this.dropStrategy,
                    this.updateFromNeighbors,
                    this.transformType,
                    this.consumeOnUse,
                    this.itemDamagePerUse);
        }
    }

    public enum DropStrategy implements CodecNameable {
        CLICKED_FACE("clicked_face"),
        FROM_MIDDLE("from_middle");

        public static final NbtCodec<BlockTransformData.DropStrategy> CODEC =
                NbtCodecs.forEnum(values());

        private final String id;

        DropStrategy(String id) {
            this.id = id;
        }

        @Override
        public String getCodecName() {
            return this.id;
        }
    }

    public enum Particle implements CodecNameable {
        NONE("none"),
        SCRAPE("scrape"),
        WAX_ON("wax_on"),
        WAX_OFF("wax_off");

        public static final NbtCodec<BlockTransformData.Particle> CODEC =
                NbtCodecs.forEnum(values());

        private final String id;

        Particle(String id) {
            this.id = id;
        }

        @Override
        public String getCodecName() {
            return this.id;
        }
    }

    public enum TransformType implements CodecNameable {
        SINGLE_BLOCK("single_block"),
        COPPER_CHEST("copper_chest");

        public static final NbtCodec<BlockTransformData.TransformType> CODEC =
                NbtCodecs.forEnum(values());

        private final String id;

        TransformType(String id) {
            this.id = id;
        }

        @Override
        public String getCodecName() {
            return this.id;
        }
    }
}
