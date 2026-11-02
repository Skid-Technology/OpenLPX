package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.Attribute;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.Attributes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WrapperPlayServerUpdateAttributes
        extends PacketWrapper<WrapperPlayServerUpdateAttributes> {

    private static final List<Entry<String, Attribute>> PRE_1_16_ATTRIBUTES =
            Collections.unmodifiableList(
                    Arrays.asList(
                            new SimpleEntry<>("generic.maxHealth", Attributes.MAX_HEALTH),
                            new SimpleEntry<>("Max Health", Attributes.MAX_HEALTH),
                            new SimpleEntry<>(
                                    "zombie.spawnReinforcements", Attributes.SPAWN_REINFORCEMENTS),
                            new SimpleEntry<>(
                                    "Spawn Reinforcements Chance", Attributes.SPAWN_REINFORCEMENTS),
                            new SimpleEntry<>("horse.jumpStrength", Attributes.HORSE_JUMP_STRENGTH),
                            new SimpleEntry<>("Jump Strength", Attributes.HORSE_JUMP_STRENGTH),
                            new SimpleEntry<>("generic.followRange", Attributes.FOLLOW_RANGE),
                            new SimpleEntry<>("Follow Range", Attributes.FOLLOW_RANGE),
                            new SimpleEntry<>(
                                    "generic.knockbackResistance", Attributes.KNOCKBACK_RESISTANCE),
                            new SimpleEntry<>(
                                    "Knockback Resistance", Attributes.KNOCKBACK_RESISTANCE),
                            new SimpleEntry<>("generic.movementSpeed", Attributes.MOVEMENT_SPEED),
                            new SimpleEntry<>("Movement Speed", Attributes.MOVEMENT_SPEED),
                            new SimpleEntry<>("generic.flyingSpeed", Attributes.FLYING_SPEED),
                            new SimpleEntry<>("Flying Speed", Attributes.FLYING_SPEED),
                            new SimpleEntry<>("generic.attackDamage", Attributes.ATTACK_DAMAGE),
                            new SimpleEntry<>(
                                    "generic.attackKnockback", Attributes.ATTACK_KNOCKBACK),
                            new SimpleEntry<>("generic.attackSpeed", Attributes.ATTACK_SPEED),
                            new SimpleEntry<>("generic.armorToughness", Attributes.ARMOR_TOUGHNESS),
                            new SimpleEntry<>("generic.armor", Attributes.ARMOR),
                            new SimpleEntry<>("generic.luck", Attributes.LUCK)));

    private static final Map<String, Attribute> PRE_1_16_ATTRIBUTES_MAP =
            PRE_1_16_ATTRIBUTES.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    private static final Map<Attribute, String> PRE_1_16_ATTRIBUTES_RMAP =
            PRE_1_16_ATTRIBUTES.stream()
                    .collect(
                            Collectors.toMap(
                                    Entry::getValue, Entry::getKey, (result, result2) -> result));

    private int entityID;

    private List<WrapperPlayServerUpdateAttributes.Property> properties;

    public WrapperPlayServerUpdateAttributes(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerUpdateAttributes(
            int entityID, List<WrapperPlayServerUpdateAttributes.Property> properties) {
        super(PacketType.Play.Server.UPDATE_ATTRIBUTES);
        this.entityID = entityID;
        this.properties = properties;
    }

    @Override
    public void read() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.entityID = this.readInt();
        } else {
            this.entityID = this.readVarInt();
        }
        this.readProperties();
    }

    protected void readProperties() {
        int value;
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            value = this.readVarInt();
        } else {
            value = this.readInt();
        }
        this.properties = new ArrayList<>(value);
        for (int index = 0; index < value; index++) {
            Attribute attribute;
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
                attribute = this.readMappedEntity(Attributes::getById);
            } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
                attribute = Attributes.getByName(this.readIdentifier().toString());
            } else {
                String text = this.readString(64);
                if ((attribute = PRE_1_16_ATTRIBUTES_MAP.get(text)) == null) {
                    attribute = Attributes.getByName(text);
                }
                if (attribute == null) {
                    throw new IllegalStateException(
                            "Can't find attribute for name "
                                    + text
                                    + " (version: "
                                    + this.serverVersion.name()
                                    + ")");
                }
            }
            double value2 = this.readDouble();
            int value3;
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
                value3 = this.readShort();
            } else {
                value3 = this.readVarInt();
            }
            ArrayList<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server
                                    .WrapperPlayServerUpdateAttributes.PropertyModifier>
                    values = new ArrayList<>(value3);
            for (int index2 = 0; index2 < value3; index2++) {
                ResourceLocation resourceLocation;
                UUID uUID;
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21)) {
                    uUID =
                            WrapperPlayServerUpdateAttributes.PropertyModifier.generateSemiUniqueId(
                                    resourceLocation = this.readIdentifier());
                } else {
                    uUID = this.readUUID();
                    resourceLocation = new ResourceLocation(uUID.toString());
                }
                double value4 = this.readDouble();
                byte value5 = this.readByte();
                WrapperPlayServerUpdateAttributes.PropertyModifier.Operation operation =
                        WrapperPlayServerUpdateAttributes.PropertyModifier.Operation.VALUES[value5];
                values.add(
                        new WrapperPlayServerUpdateAttributes.PropertyModifier(
                                resourceLocation, uUID, value4, operation));
            }
            this.properties.add(
                    new WrapperPlayServerUpdateAttributes.Property(attribute, value2, values));
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeInt(this.entityID);
        } else {
            this.writeVarInt(this.entityID);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            this.writeVarInt(this.properties.size());
        } else {
            this.writeInt(this.properties.size());
        }
        for (WrapperPlayServerUpdateAttributes.Property property : this.properties) {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
                this.writeVarInt(
                        property.getAttribute().getId(this.serverVersion.toClientVersion()));
            } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
                this.writeIdentifier(
                        property.getAttribute().getName(this.serverVersion.toClientVersion()));
            } else {
                String text = PRE_1_16_ATTRIBUTES_RMAP.get(property.getAttribute());
                this.writeString(
                        text != null ? text : property.getAttribute().getName().toString());
            }
            this.writeDouble(property.value);
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
                this.writeShort(property.modifiers.size());
            } else {
                this.writeVarInt(property.modifiers.size());
            }
            for (WrapperPlayServerUpdateAttributes.PropertyModifier propertyModifier :
                    property.modifiers) {
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21)) {
                    this.writeIdentifier(propertyModifier.name);
                } else {
                    this.writeUUID(propertyModifier.uuid);
                }
                this.writeDouble(propertyModifier.amount);
                this.writeByte(propertyModifier.operation.ordinal());
            }
        }
    }

    public void copy(WrapperPlayServerUpdateAttributes wrapperPlayServerUpdateAttributes) {
        this.entityID = wrapperPlayServerUpdateAttributes.entityID;
        this.properties = wrapperPlayServerUpdateAttributes.properties;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public List<WrapperPlayServerUpdateAttributes.Property> getProperties() {
        return this.properties;
    }

    public void setProperties(List<WrapperPlayServerUpdateAttributes.Property> properties) {
        this.properties = properties;
    }

    public static class Property {

        private Attribute attribute;

        private double value;

        private List<WrapperPlayServerUpdateAttributes.PropertyModifier> modifiers;

        private transient Double calculatedValue = null;

        @Deprecated
        public Property(
                String text,
                double value,
                List<WrapperPlayServerUpdateAttributes.PropertyModifier> values) {
            this(Attributes.getByName(text), value, values);
        }

        public Property(
                Attribute attribute2,
                double value,
                List<WrapperPlayServerUpdateAttributes.PropertyModifier> modifiers) {
            this.attribute = attribute2;
            this.value = value;
            this.modifiers = modifiers;
        }

        public double calcValue() {
            if (this.calculatedValue == null) {
                this.calculatedValue = this.calcValue0();
            }
            return this.calculatedValue;
        }

        public double calcValue0() {
            double value = this.getValue();
            Iterator<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server
                                    .WrapperPlayServerUpdateAttributes.PropertyModifier>
                    iterator2 = this.modifiers.iterator();
            while (iterator2.hasNext()) {
                WrapperPlayServerUpdateAttributes.PropertyModifier propertyModifier;
                if ((propertyModifier =
                                        (WrapperPlayServerUpdateAttributes.PropertyModifier)
                                                iterator2.next())
                                .getOperation()
                        == WrapperPlayServerUpdateAttributes.PropertyModifier.Operation.ADDITION) {
                    value += propertyModifier.getAmount();
                }
            }
            double value2 = value;
            Iterator<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server
                                    .WrapperPlayServerUpdateAttributes.PropertyModifier>
                    iterator3 = this.modifiers.iterator();
            while (iterator3.hasNext()) {
                WrapperPlayServerUpdateAttributes.PropertyModifier propertyModifier2;
                if ((propertyModifier2 =
                                        (WrapperPlayServerUpdateAttributes.PropertyModifier)
                                                iterator3.next())
                                .getOperation()
                        == WrapperPlayServerUpdateAttributes.PropertyModifier.Operation
                                .MULTIPLY_BASE) {
                    value2 += value * propertyModifier2.getAmount();
                }
            }
            iterator3 = this.modifiers.iterator();
            while (iterator3.hasNext()) {
                WrapperPlayServerUpdateAttributes.PropertyModifier propertyModifier3;
                if ((propertyModifier3 =
                                        (WrapperPlayServerUpdateAttributes.PropertyModifier)
                                                iterator3.next())
                                .getOperation()
                        == WrapperPlayServerUpdateAttributes.PropertyModifier.Operation
                                .MULTIPLY_TOTAL) {
                    value2 *= 1.0 + propertyModifier3.getAmount();
                }
            }
            return this.attribute.sanitizeValue(value2);
        }

        public Attribute getAttribute() {
            return this.attribute;
        }

        public void setAttribute(Attribute attribute2) {
            this.attribute = attribute2;
        }

        @Deprecated
        public String getKey() {
            return this.getAttribute().getName().toString();
        }

        @Deprecated
        public void setKey(String text) {
            this.setAttribute(Attributes.getByName(text));
        }

        public double getValue() {
            return this.value;
        }

        public void setValue(double value) {
            this.value = value;
            this.setDirty();
        }

        public void addModifier(
                WrapperPlayServerUpdateAttributes.PropertyModifier propertyModifier) {
            this.modifiers.add(propertyModifier);
            this.setDirty();
        }

        public boolean removeModifier(ResourceLocation resourceLocation) {
            return this.removeModifierIf(result -> resourceLocation.equals(result.getName()));
        }

        @Obsolete
        public boolean removeModifier(UUID uUID) {
            return this.removeModifierIf(result -> uUID.equals(result.getUUID()));
        }

        @Obsolete
        public boolean removeModifier(ResourceLocation resourceLocation, UUID uUID) {
            return this.removeModifierIf(
                    result ->
                            uUID.equals(result.getUUID())
                                    || resourceLocation.equals(result.getName()));
        }

        public boolean removeModifierIf(
                Predicate<WrapperPlayServerUpdateAttributes.PropertyModifier> predicate) {
            boolean matches;
            if (matches = this.modifiers.removeIf(predicate)) {
                this.setDirty();
            }
            return matches;
        }

        public List<WrapperPlayServerUpdateAttributes.PropertyModifier> getModifiers() {
            return this.modifiers;
        }

        public void setModifiers(
                List<WrapperPlayServerUpdateAttributes.PropertyModifier> modifiers) {
            this.modifiers = modifiers;
            this.setDirty();
        }

        public void setDirty() {
            this.calculatedValue = null;
        }
    }

    public static class PropertyModifier {

        private ResourceLocation name;

        private UUID uuid;

        private double amount;

        private WrapperPlayServerUpdateAttributes.PropertyModifier.Operation operation;

        public PropertyModifier(
                UUID uUID,
                double value,
                WrapperPlayServerUpdateAttributes.PropertyModifier.Operation operation) {
            this(new ResourceLocation(uUID.toString()), uUID, value, operation);
        }

        public PropertyModifier(
                ResourceLocation resourceLocation,
                double value,
                WrapperPlayServerUpdateAttributes.PropertyModifier.Operation operation) {
            this(resourceLocation, generateSemiUniqueId(resourceLocation), value, operation);
        }

        public PropertyModifier(
                ResourceLocation resourceLocation,
                UUID uuid,
                double amount,
                WrapperPlayServerUpdateAttributes.PropertyModifier.Operation operation2) {
            this.name = resourceLocation;
            this.uuid = uuid;
            this.amount = amount;
            this.operation = operation2;
        }

        @Internal
        public static UUID generateSemiUniqueId(ResourceLocation resourceLocation) {
            return UUID.nameUUIDFromBytes(
                    ("packetevents_" + resourceLocation.toString())
                            .getBytes(StandardCharsets.UTF_8));
        }

        public ResourceLocation getName() {
            return this.name;
        }

        public void setName(ResourceLocation resourceLocation) {
            this.name = resourceLocation;
            this.uuid = generateSemiUniqueId(resourceLocation);
        }

        @Obsolete
        public UUID getUUID() {
            return this.uuid;
        }

        @Obsolete
        public void setUUID(UUID uuid) {
            this.name = new ResourceLocation(uuid.toString());
            this.uuid = uuid;
        }

        public void setNameAndUUID(ResourceLocation resourceLocation, UUID uuid) {
            this.name = resourceLocation;
            this.uuid = uuid;
        }

        public double getAmount() {
            return this.amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public WrapperPlayServerUpdateAttributes.PropertyModifier.Operation getOperation() {
            return this.operation;
        }

        public void setOperation(
                WrapperPlayServerUpdateAttributes.PropertyModifier.Operation operation2) {
            this.operation = operation2;
        }

        public enum Operation {
            ADDITION,
            MULTIPLY_BASE,
            MULTIPLY_TOTAL;

            public static final WrapperPlayServerUpdateAttributes.PropertyModifier.Operation[]
                    VALUES = values();
        }
    }
}
