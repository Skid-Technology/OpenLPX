package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ItemTool {

    private List<ItemTool.Rule> rules;

    private float defaultMiningSpeed;

    private int damagePerBlock;

    private boolean canDestroyBlocksInCreative;

    public ItemTool(List<ItemTool.Rule> values, float value, int value2) {
        this(values, value, value2, true);
    }

    public ItemTool(
            List<ItemTool.Rule> rules,
            float defaultMiningSpeed,
            int damagePerBlock,
            boolean canDestroyBlocksInCreative) {
        this.rules = rules;
        this.defaultMiningSpeed = defaultMiningSpeed;
        this.damagePerBlock = damagePerBlock;
        this.canDestroyBlocksInCreative = canDestroyBlocksInCreative;
    }

    public static ItemTool read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(ItemTool.Rule::read);
        float value = packetWrapper.readFloat();
        int value2 = packetWrapper.readVarInt();
        boolean matches =
                packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)
                        || packetWrapper.readBoolean();
        return new ItemTool(values, value, value2, matches);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemTool itemTool) {
        packetWrapper.writeList(itemTool.rules, ItemTool.Rule::write);
        packetWrapper.writeFloat(itemTool.defaultMiningSpeed);
        packetWrapper.writeVarInt(itemTool.damagePerBlock);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            packetWrapper.writeBoolean(itemTool.canDestroyBlocksInCreative);
        }
    }

    public void addRule(ItemTool.Rule rule) {
        this.rules.add(rule);
    }

    public List<ItemTool.Rule> getRules() {
        return this.rules;
    }

    public void setRules(List<ItemTool.Rule> rules) {
        this.rules = rules;
    }

    public float getDefaultMiningSpeed() {
        return this.defaultMiningSpeed;
    }

    public void setDefaultMiningSpeed(float defaultMiningSpeed) {
        this.defaultMiningSpeed = defaultMiningSpeed;
    }

    public int getDamagePerBlock() {
        return this.damagePerBlock;
    }

    public void setDamagePerBlock(int damagePerBlock) {
        this.damagePerBlock = damagePerBlock;
    }

    public boolean isCanDestroyBlocksInCreative() {
        return this.canDestroyBlocksInCreative;
    }

    public void setCanDestroyBlocksInCreative(boolean canDestroyBlocksInCreative) {
        this.canDestroyBlocksInCreative = canDestroyBlocksInCreative;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof ItemTool)) {
            return false;
        } else {
            ItemTool itemTool;
            if (Float.compare(
                            (itemTool = (ItemTool) result).defaultMiningSpeed,
                            this.defaultMiningSpeed)
                    != 0) {
                return false;
            } else if (this.damagePerBlock != itemTool.damagePerBlock) {
                return false;
            } else {
                return !this.rules.equals(itemTool.rules)
                        ? false
                        : this.canDestroyBlocksInCreative == itemTool.canDestroyBlocksInCreative;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.rules,
                this.defaultMiningSpeed,
                this.damagePerBlock,
                this.canDestroyBlocksInCreative);
    }

    public static class Rule {

        private MappedEntitySet<StateType.Mapped> blocks;

        @Nullable private Float speed;

        @Nullable private Boolean correctForDrops;

        public Rule(
                MappedEntitySet<StateType.Mapped> mappedEntitySet,
                @Nullable Float speed,
                @Nullable Boolean correctForDrops) {
            this.blocks = mappedEntitySet;
            this.speed = speed;
            this.correctForDrops = correctForDrops;
        }

        public static ItemTool.Rule read(PacketWrapper<?> packetWrapper) {
            MappedEntitySet<StateType.Mapped> mappedEntitySet =
                    MappedEntitySet.read(packetWrapper, StateTypes.getRegistry());
            Float value = packetWrapper.readOptional(PacketWrapper::readFloat);
            Boolean matches = packetWrapper.readOptional(PacketWrapper::readBoolean);
            return new ItemTool.Rule(mappedEntitySet, value, matches);
        }

        public static void write(PacketWrapper<?> packetWrapper, ItemTool.Rule rule) {
            MappedEntitySet.write(packetWrapper, rule.blocks);
            packetWrapper.writeOptional(rule.speed, PacketWrapper::writeFloat);
            packetWrapper.writeOptional(rule.correctForDrops, PacketWrapper::writeBoolean);
        }

        public MappedEntitySet<StateType.Mapped> getBlocks() {
            return this.blocks;
        }

        public void setBlocks(MappedEntitySet<StateType.Mapped> mappedEntitySet) {
            this.blocks = mappedEntitySet;
        }

        @Nullable
        public Float getSpeed() {
            return this.speed;
        }

        public void setSpeed(@Nullable Float speed) {
            this.speed = speed;
        }

        @Nullable
        public Boolean getCorrectForDrops() {
            return this.correctForDrops;
        }

        public void setCorrectForDrops(@Nullable Boolean correctForDrops) {
            this.correctForDrops = correctForDrops;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof ItemTool.Rule)) {
                return false;
            } else {
                ItemTool.Rule rule = (ItemTool.Rule) result;
                if (!this.blocks.equals(rule.blocks)) {
                    return false;
                } else {
                    return !Objects.equals(this.speed, rule.speed)
                            ? false
                            : Objects.equals(this.correctForDrops, rule.correctForDrops);
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.blocks, this.speed, this.correctForDrops);
        }
    }
}
