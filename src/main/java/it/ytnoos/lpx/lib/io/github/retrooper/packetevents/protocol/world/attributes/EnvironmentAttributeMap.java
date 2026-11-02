package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers.AttributeModifier;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Either;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

@NullMarked
public class EnvironmentAttributeMap {

    public static final NbtCodec<EnvironmentAttributeMap> CODEC =
            (new NbtMapCodec<EnvironmentAttributeMap>() {

                        public EnvironmentAttributeMap decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            VersionedRegistry registry = EnvironmentAttributes.getRegistry();
                            ClientVersion clientVersion =
                                    packetWrapper.getServerVersion().toClientVersion();
                            HashMap entries = new HashMap();
                            for (String text : nBTCompound.getTagNames()) {
                                EnvironmentAttribute environmentAttribute;
                                if ((environmentAttribute =
                                                (EnvironmentAttribute)
                                                        registry.getByNameOrThrow(
                                                                clientVersion, text))
                                        .isSynced()) {
                                    entries.put(
                                            environmentAttribute,
                                            (EnvironmentAttributeMap.Entry)
                                                    nBTCompound.getOrThrow(
                                                            text,
                                                            EnvironmentAttributeMap.Entry.codec(
                                                                    environmentAttribute),
                                                            packetWrapper));
                                }
                            }
                            return new EnvironmentAttributeMap(entries);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                EnvironmentAttributeMap environmentAttributeMap)
                                throws NbtCodecException {
                            Iterator<
                                            Map.Entry<
                                                    it.ytnoos.lpx.lib.io.github.retrooper
                                                                    .packetevents.protocol.world
                                                                    .attributes
                                                                    .EnvironmentAttribute<
                                                            ?>,
                                                    it.ytnoos.lpx.lib.io.github.retrooper
                                                                    .packetevents.protocol.world
                                                                    .attributes
                                                                    .EnvironmentAttributeMap.Entry<
                                                            ?, ?>>>
                                    iterator2 =
                                            environmentAttributeMap.entries.entrySet().iterator();
                            while (iterator2.hasNext()) {
                                Map.Entry entry;
                                if (((EnvironmentAttribute)
                                                (entry = (Map.Entry) iterator2.next()).getKey())
                                        .isSynced()) {
                                    this.encode0(
                                            nBTCompound,
                                            (EnvironmentAttribute<?>) entry.getKey(),
                                            (EnvironmentAttributeMap.Entry) entry.getValue(),
                                            packetWrapper);
                                }
                            }
                        }

                        private <T> void encode0(
                                NBTCompound nBTCompound,
                                EnvironmentAttribute<?> environmentAttribute,
                                EnvironmentAttributeMap.Entry<T, ?> entry,
                                PacketWrapper<?> packetWrapper) {
                            nBTCompound.set(
                                    environmentAttribute.getName().toString(),
                                    entry,
                                    EnvironmentAttributeMap.Entry.codec(
                                            ((EnvironmentAttribute) environmentAttribute)),
                                    packetWrapper);
                        }
                    })
                    .codec();

    public static final EnvironmentAttributeMap EMPTY =
            new EnvironmentAttributeMap(Collections.emptyMap());

    private final Map<EnvironmentAttribute<?>, EnvironmentAttributeMap.Entry<?, ?>> entries;

    private EnvironmentAttributeMap(
            Map<EnvironmentAttribute<?>, EnvironmentAttributeMap.Entry<?, ?>> entries2) {
        this.entries = entries2;
    }

    public static EnvironmentAttributeMap create() {
        return new EnvironmentAttributeMap(new HashMap<>());
    }

    public EnvironmentAttributeMap copyImmutable() {
        HashMap entries2 = new HashMap<>(this.entries);
        return new EnvironmentAttributeMap(Collections.unmodifiableMap(entries2));
    }

    public EnvironmentAttributeMap copyMutable() {
        return new EnvironmentAttributeMap(new HashMap<>(this.entries));
    }

    public <T> EnvironmentAttributeMap set(EnvironmentAttribute<T> environmentAttribute, T t) {
        this.set(environmentAttribute, t, AttributeModifier.override());
        return this;
    }

    public <T, A> EnvironmentAttributeMap set(
            EnvironmentAttribute<T> environmentAttribute,
            A a,
            AttributeModifier<T, A> attributeModifier) {
        this.entries.put(
                environmentAttribute, new EnvironmentAttributeMap.Entry<>(a, attributeModifier));
        return this;
    }

    public void setAll(EnvironmentAttributeMap environmentAttributeMap) {
        this.entries.putAll(environmentAttributeMap.entries);
    }

    public <T> T getOrDefault(EnvironmentAttribute<T> environmentAttribute) {
        return this.apply(environmentAttribute, (T) environmentAttribute.getDefaultValue());
    }

    public <T> EnvironmentAttributeMap.@Nullable Entry<T, ?> get(
            EnvironmentAttribute<T> environmentAttribute) {
        return (EnvironmentAttributeMap.Entry<T, ?>) this.entries.get(environmentAttribute);
    }

    public <T> T apply(EnvironmentAttribute<T> environmentAttribute, T t) {
        EnvironmentAttributeMap.Entry<T, ?> entry;
        return (T) ((entry = this.get(environmentAttribute)) != null ? entry.getValue(t) : t);
    }

    public boolean contains(EnvironmentAttribute<?> environmentAttribute) {
        return this.entries.containsKey(environmentAttribute);
    }

    public Set<EnvironmentAttribute<?>> keySet() {
        return this.entries.keySet();
    }

    public int size() {
        return this.entries.size();
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    @Override
    public boolean equals(Object result) {
        return !(result instanceof EnvironmentAttributeMap)
                ? false
                : this.entries.equals(((EnvironmentAttributeMap) result).entries);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.entries);
    }

    public static final class Entry<T, A> {

        private final A argument;

        private final AttributeModifier<T, A> modifier;

        public Entry(A a, AttributeModifier<T, A> attributeModifier) {
            this.argument = (A) a;
            this.modifier = attributeModifier;
        }

        public static <T> EnvironmentAttributeMap.Entry<T, T> createOverride(T t) {
            return new EnvironmentAttributeMap.Entry<>((T) t, AttributeModifier.override());
        }

        public static <T> NbtCodec<EnvironmentAttributeMap.Entry<T, ?>> codec(
                final EnvironmentAttribute<T> environmentAttribute) {
            NbtCodec<T> valueCodec = environmentAttribute.getType().getValueCodec();
            final NbtCodec<AttributeModifier<T, ?>> modifierCodec =
                    environmentAttribute.getType().getModifierCodec();
            return NbtCodecs.<T, EnvironmentAttributeMap.Entry<T, ?>>either(
                            valueCodec,
                            (new NbtMapCodec<EnvironmentAttributeMap.Entry<T, ?>>() {

                                        public EnvironmentAttributeMap.Entry<T, ?> decode(
                                                NBTCompound valueCodec,
                                                PacketWrapper<?> packetWrapper)
                                                throws NbtCodecException {
                                            AttributeModifier result =
                                                    ((AttributeModifier)
                                                            valueCodec.getOrThrow(
                                                                    "modifier",
                                                                    modifierCodec,
                                                                    packetWrapper));
                                            T t =
                                                    ((T)
                                                            valueCodec.getOrThrow(
                                                                    "argument",
                                                                    result.argumentCodec(
                                                                            environmentAttribute),
                                                                    packetWrapper));
                                            return new EnvironmentAttributeMap.Entry<>(t, result);
                                        }

                                        public void encode(
                                                NBTCompound valueCodec,
                                                PacketWrapper<?> packetWrapper,
                                                EnvironmentAttributeMap.Entry<T, ?> result)
                                                throws NbtCodecException {
                                            this.encode0(valueCodec, packetWrapper, result);
                                        }

                                        private <A> void encode0(
                                                NBTCompound valueCodec,
                                                PacketWrapper<?> packetWrapper,
                                                EnvironmentAttributeMap.Entry<T, A> result)
                                                throws NbtCodecException {
                                            valueCodec.set(
                                                    "modifier",
                                                    result.modifier,
                                                    modifierCodec,
                                                    packetWrapper);
                                            valueCodec.set(
                                                    "argument",
                                                    result.argument,
                                                    result.modifier.argumentCodec(
                                                            environmentAttribute),
                                                    packetWrapper);
                                        }
                                    })
                                    .codec())
                    .apply(
                            result2 ->
                                    result2.map(
                                            EnvironmentAttributeMap.Entry::createOverride,
                                            Function.identity()),
                            result2 ->
                                    result2.isOverride()
                                            ? Either
                                                    .<T, EnvironmentAttributeMap.Entry<T, ?>>
                                                            createLeft((T) result2.argument)
                                            : Either.createRight(result2));
        }

        public boolean isOverride() {
            return this.modifier == AttributeModifier.override();
        }

        public T getValue(T t) {
            return this.modifier.apply((T) t, this.argument);
        }

        public A getArgument() {
            return this.argument;
        }

        public AttributeModifier<T, A> getModifier() {
            return this.modifier;
        }

        @Override
        public boolean equals(Object result) {
            if (result != null && this.getClass() == result.getClass()) {
                EnvironmentAttributeMap.Entry entry = (EnvironmentAttributeMap.Entry) result;
                return this.modifier != entry.modifier
                        ? false
                        : this.argument.equals(entry.argument);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.argument, this.modifier);
        }
    }
}
