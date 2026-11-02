package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.predicates.ComponentMatchers;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemAdventurePredicate {

    private List<ItemAdventurePredicate.BlockPredicate> predicates;

    @Obsolete private boolean showInTooltip;

    public ItemAdventurePredicate(List<ItemAdventurePredicate.BlockPredicate> values) {
        this(values, true);
    }

    @Obsolete
    public ItemAdventurePredicate(
            List<ItemAdventurePredicate.BlockPredicate> predicates, boolean showInTooltip) {
        this.predicates = predicates;
        this.showInTooltip = showInTooltip;
    }

    public static ItemAdventurePredicate read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(ItemAdventurePredicate.BlockPredicate::read);
        boolean matches =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        || packetWrapper.readBoolean();
        return new ItemAdventurePredicate(values, matches);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemAdventurePredicate itemAdventurePredicate) {
        packetWrapper.writeList(
                itemAdventurePredicate.predicates, ItemAdventurePredicate.BlockPredicate::write);
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            packetWrapper.writeBoolean(itemAdventurePredicate.showInTooltip);
        }
    }

    public void addPredicate(ItemAdventurePredicate.BlockPredicate blockPredicate) {
        this.predicates.add(blockPredicate);
    }

    public List<ItemAdventurePredicate.BlockPredicate> getPredicates() {
        return this.predicates;
    }

    public void setPredicates(List<ItemAdventurePredicate.BlockPredicate> predicates) {
        this.predicates = predicates;
    }

    @Obsolete
    public boolean isShowInTooltip() {
        return this.showInTooltip;
    }

    @Obsolete
    public void setShowInTooltip(boolean showInTooltip) {
        this.showInTooltip = showInTooltip;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemAdventurePredicate)) {
            return false;
        }
        ItemAdventurePredicate itemAdventurePredicate = (ItemAdventurePredicate) result;
        return this.showInTooltip != itemAdventurePredicate.showInTooltip
                ? false
                : this.predicates.equals(itemAdventurePredicate.predicates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.predicates, this.showInTooltip);
    }

    public static class BlockPredicate {

        @Nullable private MappedEntitySet<StateType.Mapped> blocks;

        @Nullable private List<ItemAdventurePredicate.PropertyMatcher> properties;

        @Nullable private NBTCompound nbt;

        private ComponentMatchers matchers;

        public BlockPredicate(
                @Nullable MappedEntitySet<StateType.Mapped> mappedEntitySet,
                @Nullable List<ItemAdventurePredicate.PropertyMatcher> values,
                @Nullable NBTCompound nBTCompound) {
            this(mappedEntitySet, values, nBTCompound, new ComponentMatchers());
        }

        public BlockPredicate(
                @Nullable MappedEntitySet<StateType.Mapped> mappedEntitySet,
                @Nullable List<ItemAdventurePredicate.PropertyMatcher> properties,
                @Nullable NBTCompound nBTCompound,
                ComponentMatchers componentMatchers) {
            this.blocks = mappedEntitySet;
            this.properties = properties;
            this.nbt = nBTCompound;
            this.matchers = componentMatchers;
        }

        public static ItemAdventurePredicate.BlockPredicate read(PacketWrapper<?> packetWrapper) {
            MappedEntitySet mappedEntitySet =
                    packetWrapper.readOptional(
                            result -> MappedEntitySet.read(result, StateTypes.getRegistry()));
            List values =
                    packetWrapper.readOptional(
                            result2 ->
                                    packetWrapper.readList(
                                            ItemAdventurePredicate.PropertyMatcher::read));
            NBTCompound nBTCompound = packetWrapper.readOptional(PacketWrapper::readNBT);
            ComponentMatchers componentMatchers =
                    packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                            ? ComponentMatchers.read(packetWrapper)
                            : new ComponentMatchers();
            return new ItemAdventurePredicate.BlockPredicate(
                    mappedEntitySet, values, nBTCompound, componentMatchers);
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                ItemAdventurePredicate.BlockPredicate blockPredicate) {
            packetWrapper.writeOptional(blockPredicate.blocks, MappedEntitySet::write);
            packetWrapper.writeOptional(
                    blockPredicate.properties,
                    (result, result2) ->
                            result.writeList(
                                    result2, ItemAdventurePredicate.PropertyMatcher::write));
            packetWrapper.writeOptional(blockPredicate.nbt, PacketWrapper::writeNBT);
            if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
                ComponentMatchers.write(packetWrapper, blockPredicate.matchers);
            }
        }

        @Nullable
        public MappedEntitySet<StateType.Mapped> getBlocks() {
            return this.blocks;
        }

        public void setBlocks(@Nullable MappedEntitySet<StateType.Mapped> mappedEntitySet) {
            this.blocks = mappedEntitySet;
        }

        public void addProperty(ItemAdventurePredicate.PropertyMatcher propertyMatcher) {
            if (this.properties == null) {
                this.properties = new ArrayList<>(4);
            }
            this.properties.add(propertyMatcher);
        }

        @Nullable
        public List<ItemAdventurePredicate.PropertyMatcher> getProperties() {
            return this.properties;
        }

        public void setProperties(
                @Nullable List<ItemAdventurePredicate.PropertyMatcher> properties) {
            this.properties = properties;
        }

        @Nullable
        public NBTCompound getNbt() {
            return this.nbt;
        }

        public void setNbt(@Nullable NBTCompound nBTCompound) {
            this.nbt = nBTCompound;
        }

        public ComponentMatchers getMatchers() {
            return this.matchers;
        }

        public void setMatchers(ComponentMatchers componentMatchers) {
            this.matchers = componentMatchers;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof ItemAdventurePredicate.BlockPredicate)) {
                return false;
            } else {
                ItemAdventurePredicate.BlockPredicate blockPredicate =
                        (ItemAdventurePredicate.BlockPredicate) result;
                if (!Objects.equals(this.blocks, blockPredicate.blocks)) {
                    return false;
                } else if (!Objects.equals(this.properties, blockPredicate.properties)) {
                    return false;
                } else {
                    return !Objects.equals(this.nbt, blockPredicate.nbt)
                            ? false
                            : this.matchers.equals(blockPredicate.matchers);
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.blocks, this.properties, this.nbt, this.matchers);
        }
    }

    public static class ExactValueMatcher implements ItemAdventurePredicate.ValueMatcher {

        private String value;

        public ExactValueMatcher(String value) {
            this.value = value;
        }

        public static ItemAdventurePredicate.ExactValueMatcher read(
                PacketWrapper<?> packetWrapper) {
            return new ItemAdventurePredicate.ExactValueMatcher(packetWrapper.readString());
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                ItemAdventurePredicate.ExactValueMatcher exactValueMatcher) {
            packetWrapper.writeString(exactValueMatcher.value);
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof ItemAdventurePredicate.ExactValueMatcher)) {
                return false;
            }
            ItemAdventurePredicate.ExactValueMatcher exactValueMatcher =
                    (ItemAdventurePredicate.ExactValueMatcher) result;
            return this.value.equals(exactValueMatcher.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.value);
        }
    }

    public static class PropertyMatcher {

        private String name;

        private ItemAdventurePredicate.ValueMatcher matcher;

        public PropertyMatcher(String name, ItemAdventurePredicate.ValueMatcher valueMatcher) {
            this.name = name;
            this.matcher = valueMatcher;
        }

        public static ItemAdventurePredicate.PropertyMatcher read(PacketWrapper<?> packetWrapper) {
            String text = packetWrapper.readString();
            ItemAdventurePredicate.ValueMatcher valueMatcher =
                    ItemAdventurePredicate.ValueMatcher.read(packetWrapper);
            return new ItemAdventurePredicate.PropertyMatcher(text, valueMatcher);
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                ItemAdventurePredicate.PropertyMatcher propertyMatcher) {
            packetWrapper.writeString(propertyMatcher.name);
            ItemAdventurePredicate.ValueMatcher.write(packetWrapper, propertyMatcher.matcher);
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ItemAdventurePredicate.ValueMatcher getMatcher() {
            return this.matcher;
        }

        public void setMatcher(ItemAdventurePredicate.ValueMatcher valueMatcher) {
            this.matcher = valueMatcher;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof ItemAdventurePredicate.PropertyMatcher)) {
                return false;
            }
            ItemAdventurePredicate.PropertyMatcher propertyMatcher =
                    (ItemAdventurePredicate.PropertyMatcher) result;
            return !this.name.equals(propertyMatcher.name)
                    ? false
                    : this.matcher.equals(propertyMatcher.matcher);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.name, this.matcher);
        }
    }

    public static class RangedValueMatcher implements ItemAdventurePredicate.ValueMatcher {

        @Nullable private String minValue;

        @Nullable private String maxValue;

        public RangedValueMatcher(@Nullable String minValue, @Nullable String maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public static ItemAdventurePredicate.RangedValueMatcher read(
                PacketWrapper<?> packetWrapper) {
            String text = packetWrapper.readOptional(PacketWrapper::readString);
            String text2 = packetWrapper.readOptional(PacketWrapper::readString);
            return new ItemAdventurePredicate.RangedValueMatcher(text, text2);
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                ItemAdventurePredicate.RangedValueMatcher rangedValueMatcher) {
            packetWrapper.writeOptional(rangedValueMatcher.minValue, PacketWrapper::writeString);
            packetWrapper.writeOptional(rangedValueMatcher.maxValue, PacketWrapper::writeString);
        }

        @Nullable
        public String getMinValue() {
            return this.minValue;
        }

        public void setMinValue(@Nullable String minValue) {
            this.minValue = minValue;
        }

        @Nullable
        public String getMaxValue() {
            return this.maxValue;
        }

        public void setMaxValue(@Nullable String maxValue) {
            this.maxValue = maxValue;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof ItemAdventurePredicate.RangedValueMatcher)) {
                return false;
            }
            ItemAdventurePredicate.RangedValueMatcher rangedValueMatcher =
                    (ItemAdventurePredicate.RangedValueMatcher) result;
            return !Objects.equals(this.minValue, rangedValueMatcher.minValue)
                    ? false
                    : Objects.equals(this.maxValue, rangedValueMatcher.maxValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.minValue, this.maxValue);
        }
    }

    public interface ValueMatcher {

        static ItemAdventurePredicate.ValueMatcher read(PacketWrapper<?> packetWrapper) {
            return packetWrapper.readBoolean()
                    ? ItemAdventurePredicate.ExactValueMatcher.read(packetWrapper)
                    : ItemAdventurePredicate.RangedValueMatcher.read(packetWrapper);
        }

        static void write(
                PacketWrapper<?> packetWrapper, ItemAdventurePredicate.ValueMatcher valueMatcher) {
            if (valueMatcher instanceof ItemAdventurePredicate.ExactValueMatcher) {
                packetWrapper.writeBoolean(true);
                ItemAdventurePredicate.ExactValueMatcher.write(
                        packetWrapper, (ItemAdventurePredicate.ExactValueMatcher) valueMatcher);
            } else {
                if (!(valueMatcher instanceof ItemAdventurePredicate.RangedValueMatcher)) {
                    throw new IllegalArgumentException(
                            "Illegal matcher implementation: " + valueMatcher);
                }
                packetWrapper.writeBoolean(false);
                ItemAdventurePredicate.RangedValueMatcher.write(
                        packetWrapper, (ItemAdventurePredicate.RangedValueMatcher) valueMatcher);
            }
        }
    }
}
