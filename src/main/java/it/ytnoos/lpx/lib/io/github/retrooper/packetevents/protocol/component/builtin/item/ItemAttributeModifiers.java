package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.Attribute;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.AttributeDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.AttributeOperation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.Attributes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.DefaultAttributeDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ItemAttributeModifiers {

    public static final ItemAttributeModifiers EMPTY =
            new ItemAttributeModifiers(Collections.emptyList(), true) {

                @Override
                public void setModifiers(List<ItemAttributeModifiers.ModifierEntry> values) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void setShowInTooltip(boolean matches) {
                    throw new UnsupportedOperationException();
                }
            };

    private List<ItemAttributeModifiers.ModifierEntry> modifiers;

    @Obsolete private boolean showInTooltip;

    public ItemAttributeModifiers(List<ItemAttributeModifiers.ModifierEntry> values) {
        this(values, true);
    }

    @Obsolete
    public ItemAttributeModifiers(
            List<ItemAttributeModifiers.ModifierEntry> modifiers, boolean showInTooltip) {
        this.modifiers = modifiers;
        this.showInTooltip = showInTooltip;
    }

    public static ItemAttributeModifiers read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(ItemAttributeModifiers.ModifierEntry::read);
        boolean matches =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        || packetWrapper.readBoolean();
        return new ItemAttributeModifiers(values, matches);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemAttributeModifiers itemAttributeModifiers) {
        packetWrapper.writeList(
                itemAttributeModifiers.modifiers, ItemAttributeModifiers.ModifierEntry::write);
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            packetWrapper.writeBoolean(itemAttributeModifiers.showInTooltip);
        }
    }

    public void addModifier(ItemAttributeModifiers.ModifierEntry modifierEntry) {
        this.modifiers.add(modifierEntry);
    }

    public List<ItemAttributeModifiers.ModifierEntry> getModifiers() {
        return this.modifiers;
    }

    public void setModifiers(List<ItemAttributeModifiers.ModifierEntry> modifiers) {
        this.modifiers = modifiers;
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
        if (!(result instanceof ItemAttributeModifiers)) {
            return false;
        }
        ItemAttributeModifiers itemAttributeModifiers = (ItemAttributeModifiers) result;
        return this.showInTooltip != itemAttributeModifiers.showInTooltip
                ? false
                : this.modifiers.equals(itemAttributeModifiers.modifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.modifiers, this.showInTooltip);
    }

    @Override
    public String toString() {
        return "ItemAttributeModifiers{modifiers="
                + this.modifiers
                + ", showInTooltip="
                + this.showInTooltip
                + 125;
    }

    public enum EquipmentSlotGroup {
        ANY("any"),
        MAINHAND("mainhand"),
        OFFHAND("offhand"),
        HAND("hand"),
        FEET("feet"),
        LEGS("legs"),
        CHEST("chest"),
        HEAD("head"),
        ARMOR("armor"),
        BODY("body"),
        SADDLE("saddle");

        public static final Index<String, ItemAttributeModifiers.EquipmentSlotGroup> ID_INDEX =
                Index.create(
                        ItemAttributeModifiers.EquipmentSlotGroup.class,
                        ItemAttributeModifiers.EquipmentSlotGroup::getId);

        private final String id;

        EquipmentSlotGroup(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    public static class Modifier {

        private UUID id;

        private String name;

        private double value;

        private AttributeOperation operation;

        public Modifier(
                ResourceLocation resourceLocation,
                double value,
                AttributeOperation attributeOperation) {
            this(
                    WrapperPlayServerUpdateAttributes.PropertyModifier.generateSemiUniqueId(
                            resourceLocation),
                    resourceLocation.toString(),
                    value,
                    attributeOperation);
        }

        @Obsolete
        public Modifier(UUID id, String name, double value, AttributeOperation attributeOperation) {
            this.id = id;
            this.name = name;
            this.value = value;
            this.operation = attributeOperation;
        }

        public static ItemAttributeModifiers.Modifier read(PacketWrapper<?> packetWrapper) {
            UUID uUID;
            String text;
            if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21)) {
                ResourceLocation resourceLocation;
                text = (resourceLocation = packetWrapper.readIdentifier()).toString();
                uUID =
                        WrapperPlayServerUpdateAttributes.PropertyModifier.generateSemiUniqueId(
                                resourceLocation);
            } else {
                uUID = packetWrapper.readUUID();
                text = packetWrapper.readString();
            }
            double value = packetWrapper.readDouble();
            AttributeOperation attributeOperation =
                    packetWrapper.readEnum(AttributeOperation.values());
            return new ItemAttributeModifiers.Modifier(uUID, text, value, attributeOperation);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, ItemAttributeModifiers.Modifier modifier) {
            if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21)) {
                packetWrapper.writeIdentifier(new ResourceLocation(modifier.name));
            } else {
                packetWrapper.writeUUID(modifier.id);
                packetWrapper.writeString(modifier.name);
            }
            packetWrapper.writeDouble(modifier.value);
            packetWrapper.writeEnum(modifier.operation);
        }

        public ResourceLocation getNameKey() {
            return new ResourceLocation(this.name);
        }

        public void setNameKey(ResourceLocation resourceLocation) {
            this.name = resourceLocation.toString();
        }

        @Obsolete
        public UUID getId() {
            return this.id;
        }

        @Obsolete
        public void setId(UUID id) {
            this.id = id;
        }

        @Obsolete
        public String getName() {
            return this.name;
        }

        @Obsolete
        public void setName(String name) {
            this.name = name;
        }

        public double getValue() {
            return this.value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public AttributeOperation getOperation() {
            return this.operation;
        }

        public void setOperation(AttributeOperation attributeOperation) {
            this.operation = attributeOperation;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof ItemAttributeModifiers.Modifier)) {
                return false;
            } else {
                ItemAttributeModifiers.Modifier modifier;
                if (Double.compare(
                                (modifier = (ItemAttributeModifiers.Modifier) result).value,
                                this.value)
                        != 0) {
                    return false;
                } else if (!this.id.equals(modifier.id)) {
                    return false;
                } else {
                    return !this.name.equals(modifier.name)
                            ? false
                            : this.operation == modifier.operation;
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.id, this.name, this.value, this.operation);
        }

        @Override
        public String toString() {
            return "Modifier{id="
                    + this.id
                    + ", name='"
                    + this.name
                    + '\''
                    + ", value="
                    + this.value
                    + ", operation="
                    + this.operation
                    + '}';
        }
    }

    public static class ModifierEntry {

        private Attribute attribute;

        private ItemAttributeModifiers.Modifier modifier;

        private ItemAttributeModifiers.EquipmentSlotGroup slotGroup;

        private AttributeDisplay display;

        @Obsolete
        public ModifierEntry(
                Attribute attribute,
                ItemAttributeModifiers.Modifier modifier,
                ItemAttributeModifiers.EquipmentSlotGroup equipmentSlotGroup) {
            this(attribute, modifier, equipmentSlotGroup, DefaultAttributeDisplay.INSTANCE);
        }

        public ModifierEntry(
                Attribute attribute2,
                ItemAttributeModifiers.Modifier modifier2,
                ItemAttributeModifiers.EquipmentSlotGroup equipmentSlotGroup,
                AttributeDisplay attributeDisplay) {
            this.attribute = attribute2;
            this.modifier = modifier2;
            this.slotGroup = equipmentSlotGroup;
            this.display = attributeDisplay;
        }

        public static ItemAttributeModifiers.ModifierEntry read(PacketWrapper<?> packetWrapper) {
            Attribute attribute = packetWrapper.readMappedEntity(Attributes::getById);
            ItemAttributeModifiers.Modifier modifier =
                    ItemAttributeModifiers.Modifier.read(packetWrapper);
            ItemAttributeModifiers.EquipmentSlotGroup equipmentSlotGroup =
                    packetWrapper.readEnum(ItemAttributeModifiers.EquipmentSlotGroup.values());
            AttributeDisplay attributeDisplay =
                    packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_6)
                            ? AttributeDisplay.read(packetWrapper)
                            : DefaultAttributeDisplay.INSTANCE;
            return new ItemAttributeModifiers.ModifierEntry(
                    attribute, modifier, equipmentSlotGroup, attributeDisplay);
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                ItemAttributeModifiers.ModifierEntry modifierEntry) {
            packetWrapper.writeMappedEntity(modifierEntry.attribute);
            ItemAttributeModifiers.Modifier.write(packetWrapper, modifierEntry.modifier);
            packetWrapper.writeEnum(modifierEntry.slotGroup);
            if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
                AttributeDisplay.write(packetWrapper, modifierEntry.display);
            }
        }

        public Attribute getAttribute() {
            return this.attribute;
        }

        public void setAttribute(Attribute attribute2) {
            this.attribute = attribute2;
        }

        public ItemAttributeModifiers.Modifier getModifier() {
            return this.modifier;
        }

        public void setModifier(ItemAttributeModifiers.Modifier modifier2) {
            this.modifier = modifier2;
        }

        public ItemAttributeModifiers.EquipmentSlotGroup getSlotGroup() {
            return this.slotGroup;
        }

        public void setSlotGroup(ItemAttributeModifiers.EquipmentSlotGroup equipmentSlotGroup) {
            this.slotGroup = equipmentSlotGroup;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof ItemAttributeModifiers.ModifierEntry)) {
                return false;
            } else {
                ItemAttributeModifiers.ModifierEntry modifierEntry =
                        (ItemAttributeModifiers.ModifierEntry) result;
                if (!this.attribute.equals(modifierEntry.attribute)) {
                    return false;
                } else {
                    return !this.modifier.equals(modifierEntry.modifier)
                            ? false
                            : this.slotGroup == modifierEntry.slotGroup;
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.attribute, this.modifier, this.slotGroup);
        }

        @Override
        public String toString() {
            return "ModifierEntry{attribute="
                    + this.attribute
                    + ", modifier="
                    + this.modifier
                    + ", slotGroup="
                    + this.slotGroup
                    + 125;
        }
    }
}
