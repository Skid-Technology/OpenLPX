package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.StaticComponentMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class StaticItemType extends AbstractMappedEntity implements ItemType {

    private final int maxAmount;

    private final int maxDurability;

    private final ItemType craftRemainder;

    @Nullable private final StateType placedType;

    private final Set<ItemTypes.ItemAttribute> attributes;

    private final Map<ClientVersion, StaticComponentMap> components;

    @Internal
    public StaticItemType(
            @Nullable TypesBuilderData typesBuilderData,
            int maxAmount,
            int maxDurability,
            ItemType itemType,
            @Nullable StateType stateType,
            Set<ItemTypes.ItemAttribute> attributes) {
        super(typesBuilderData);
        this.maxAmount = maxAmount;
        this.maxDurability = maxDurability;
        this.craftRemainder = itemType;
        this.placedType = stateType;
        this.attributes = attributes;
        this.components = new EnumMap<>(ClientVersion.class);
    }

    @Override
    public int getMaxAmount() {
        return this.maxAmount;
    }

    @Override
    public int getMaxDurability() {
        return this.maxDurability;
    }

    @Override
    public ItemType getCraftRemainder() {
        return this.craftRemainder;
    }

    @Nullable
    @Override
    public StateType getPlacedType() {
        return this.placedType;
    }

    @Override
    public Set<ItemTypes.ItemAttribute> getAttributes() {
        return this.attributes;
    }

    @Override
    public StaticComponentMap getComponents(ClientVersion clientVersion) {
        if (!clientVersion.isRelease()) {
            throw new IllegalArgumentException(
                    "Unsupported version for getting components of "
                            + this.getName()
                            + ": "
                            + clientVersion);
        } else {
            return this.components.getOrDefault(
                    clientVersion, StaticComponentMap.SHARED_ITEM_COMPONENTS);
        }
    }

    void setComponents(ClientVersion clientVersion, StaticComponentMap staticComponentMap) {
        if (this.components.containsKey(clientVersion)) {
            throw new IllegalStateException(
                    "Components are already defined for "
                            + this.getName()
                            + " in version "
                            + clientVersion);
        }
        if (!clientVersion.isRelease()) {
            throw new IllegalArgumentException(
                    "Unsupported version for setting components of "
                            + this.getName()
                            + ": "
                            + clientVersion);
        }
        this.components.put(clientVersion, staticComponentMap);
    }

    boolean hasComponents(ClientVersion clientVersion) {
        return this.components.containsKey(clientVersion);
    }

    void fillComponents() {
        StaticComponentMap staticComponentMap = null;
        ClientVersion[] clientVersions;
        int value = (clientVersions = ClientVersion.values()).length;
        for (int index = 0; index < value; index++) {
            ClientVersion clientVersion;
            if ((clientVersion = clientVersions[index]).isRelease()) {
                StaticComponentMap staticComponentMap2;
                if ((staticComponentMap2 = this.components.get(clientVersion)) == null) {
                    if (staticComponentMap != null) {
                        this.components.put(clientVersion, staticComponentMap);
                    }
                } else {
                    if (staticComponentMap == null) {
                        ClientVersion[] clientVersions2;
                        int value2 = (clientVersions2 = ClientVersion.values()).length;
                        ClientVersion clientVersion2;
                        for (int index2 = 0;
                                index2 < value2
                                        && (clientVersion2 = clientVersions2[index2])
                                                != clientVersion;
                                index2++) {
                            this.components.put(clientVersion2, staticComponentMap2);
                        }
                    }
                    staticComponentMap = staticComponentMap2;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "StaticItemType[" + this.getName() + "]";
    }
}
