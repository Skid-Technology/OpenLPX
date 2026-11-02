package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NullMarked
public final class RuleBasedStateProvider extends AbstractMappedEntity
        implements BlockStateProvider {

    public static final NbtMapCodec<RuleBasedStateProvider> MAP_CODEC =
            new NbtMapCodec<RuleBasedStateProvider>() {

                public RuleBasedStateProvider decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    BlockStateProvider orNull =
                            nBTCompound.getOrNull(
                                    "fallback", BlockStateProvider.CODEC, packetWrapper);
                    List<
                                    it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol
                                            .world.generation.provider.RuleBasedStateProvider.Rule>
                            listOrEmpty =
                                    nBTCompound.getListOrEmpty(
                                            "rules",
                                            RuleBasedStateProvider.Rule.CODEC,
                                            packetWrapper);
                    return new RuleBasedStateProvider(null, orNull, listOrEmpty);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        RuleBasedStateProvider ruleBasedStateProvider)
                        throws NbtCodecException {
                    if (ruleBasedStateProvider.fallback != null) {
                        nBTCompound.set(
                                "fallback",
                                ruleBasedStateProvider.fallback,
                                BlockStateProvider.CODEC,
                                packetWrapper);
                    }
                    nBTCompound.setList(
                            "rules",
                            ruleBasedStateProvider.rules,
                            RuleBasedStateProvider.Rule.CODEC,
                            packetWrapper);
                }
            };

    @Nullable private final BlockStateProvider fallback;

    private final List<RuleBasedStateProvider.Rule> rules;

    public RuleBasedStateProvider(
            @Nullable BlockStateProvider blockStateProvider,
            List<RuleBasedStateProvider.Rule> values) {
        this(null, blockStateProvider, values);
    }

    @Internal
    public RuleBasedStateProvider(
            @Nullable TypesBuilderData typesBuilderData,
            @Nullable BlockStateProvider blockStateProvider,
            List<RuleBasedStateProvider.Rule> values) {
        super(typesBuilderData);
        this.fallback = blockStateProvider;
        this.rules = Collections.unmodifiableList(values);
    }

    @Nullable
    public BlockStateProvider getFallback() {
        return this.fallback;
    }

    public List<RuleBasedStateProvider.Rule> getRules() {
        return this.rules;
    }

    @Override
    public BlockStateProviderType<?> getType() {
        return BlockStateProviderTypes.RULE_BASED;
    }

    public RuleBasedStateProvider copy(@Nullable TypesBuilderData typesBuilderData) {
        return new RuleBasedStateProvider(typesBuilderData, this.fallback, this.rules);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof RuleBasedStateProvider)) {
            return false;
        }
        RuleBasedStateProvider ruleBasedStateProvider = (RuleBasedStateProvider) result;
        return !Objects.equals(this.fallback, ruleBasedStateProvider.fallback)
                ? false
                : this.rules.equals(ruleBasedStateProvider.rules);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.fallback, this.rules);
    }

    public static final class Rule {

        public static final NbtCodec<RuleBasedStateProvider.Rule> CODEC =
                (new NbtMapCodec<RuleBasedStateProvider.Rule>() {

                            public RuleBasedStateProvider.Rule decode(
                                    NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                    throws NbtCodecException {
                                BlockPredicate orThrow =
                                        nBTCompound.getOrThrow(
                                                "if_true", BlockPredicate.CODEC, packetWrapper);
                                BlockStateProvider orThrow2 =
                                        nBTCompound.getOrThrow(
                                                "then", BlockStateProvider.CODEC, packetWrapper);
                                return new RuleBasedStateProvider.Rule(orThrow, orThrow2);
                            }

                            public void encode(
                                    NBTCompound nBTCompound,
                                    PacketWrapper<?> packetWrapper,
                                    RuleBasedStateProvider.Rule rule)
                                    throws NbtCodecException {
                                nBTCompound.set(
                                        "if_true",
                                        rule.ifTrue,
                                        BlockPredicate.CODEC,
                                        packetWrapper);
                                nBTCompound.set(
                                        "then", rule.then, BlockStateProvider.CODEC, packetWrapper);
                            }
                        })
                        .codec();

        private final BlockPredicate ifTrue;

        private final BlockStateProvider then;

        public Rule(BlockPredicate blockPredicate, BlockStateProvider blockStateProvider) {
            this.ifTrue = blockPredicate;
            this.then = blockStateProvider;
        }

        public BlockPredicate getIfTrue() {
            return this.ifTrue;
        }

        public BlockStateProvider getThen() {
            return this.then;
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (this == result) {
                return true;
            } else if (result != null && this.getClass() == result.getClass()) {
                RuleBasedStateProvider.Rule rule = (RuleBasedStateProvider.Rule) result;
                return !this.ifTrue.equals(rule.ifTrue) ? false : this.then.equals(rule.then);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.ifTrue, this.then);
        }
    }
}
