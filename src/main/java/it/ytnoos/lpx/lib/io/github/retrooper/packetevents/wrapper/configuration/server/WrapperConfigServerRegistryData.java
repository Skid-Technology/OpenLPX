package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WrapperConfigServerRegistryData
        extends PacketWrapper<WrapperConfigServerRegistryData> {

    private NBTCompound registryData;

    private ResourceLocation registryKey;

    private List<WrapperConfigServerRegistryData.RegistryElement> elements;

    public WrapperConfigServerRegistryData(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Obsolete
    public WrapperConfigServerRegistryData(NBTCompound nBTCompound) {
        this(nBTCompound, null, null);
    }

    public WrapperConfigServerRegistryData(
            ResourceLocation resourceLocation,
            List<WrapperConfigServerRegistryData.RegistryElement> values) {
        this(null, resourceLocation, values);
    }

    @Obsolete
    public WrapperConfigServerRegistryData(
            @Nullable NBTCompound nBTCompound,
            @Nullable ResourceLocation resourceLocation,
            @Nullable List<WrapperConfigServerRegistryData.RegistryElement> elements) {
        super(PacketType.Configuration.Server.REGISTRY_DATA);
        this.registryData = nBTCompound;
        this.registryKey = resourceLocation;
        this.elements = elements;
    }

    @Override
    public void read() {
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_20_5)) {
            this.registryData = this.readUnlimitedNBT();
        } else {
            this.registryKey = this.readIdentifier();
            this.elements =
                    this.readList(
                            result -> {
                                ResourceLocation resourceLocation = result.readIdentifier();
                                NBT nBT = result.readOptional(PacketWrapper::readNBTRaw);
                                return new WrapperConfigServerRegistryData.RegistryElement(
                                        resourceLocation, nBT);
                            });
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_20_5)) {
            this.writeNBT(this.registryData);
        } else {
            this.writeIdentifier(this.registryKey);
            this.writeList(
                    this.elements,
                    (result, result2) -> {
                        result.writeIdentifier(result2.getId());
                        result.writeOptional(result2.getData(), PacketWrapper::writeNBTRaw);
                    });
        }
    }

    public void copy(WrapperConfigServerRegistryData wrapperConfigServerRegistryData) {
        this.registryData = wrapperConfigServerRegistryData.registryData;
        this.registryKey = wrapperConfigServerRegistryData.registryKey;
        this.elements = wrapperConfigServerRegistryData.elements;
    }

    @Obsolete
    @Nullable
    public NBTCompound getRegistryData() {
        return this.registryData;
    }

    @Obsolete
    public void setRegistryData(NBTCompound nBTCompound) {
        this.registryData = nBTCompound;
    }

    @Nullable
    public ResourceLocation getRegistryKey() {
        return this.registryKey;
    }

    public void setRegistryKey(ResourceLocation resourceLocation) {
        this.registryKey = resourceLocation;
    }

    @Nullable
    public List<WrapperConfigServerRegistryData.RegistryElement> getElements() {
        return this.elements;
    }

    public void setElements(List<WrapperConfigServerRegistryData.RegistryElement> elements) {
        this.elements = elements;
    }

    public static class RegistryElement {

        private final ResourceLocation id;

        @Nullable private final NBT data;

        public RegistryElement(NBTCompound nBTCompound) {
            this(
                    new ResourceLocation(nBTCompound.getStringTagValueOrThrow("name")),
                    nBTCompound.getTagOrNull("element"));
        }

        public RegistryElement(ResourceLocation resourceLocation, @Nullable NBT nBT) {
            this.id = resourceLocation;
            this.data = nBT;
        }

        public static List<WrapperConfigServerRegistryData.RegistryElement> convertNbt(
                NBTList<NBTCompound> nBTList) {
            ArrayList<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration
                                    .server.WrapperConfigServerRegistryData.RegistryElement>
                    values = new ArrayList<>(nBTList.size());
            for (NBTCompound nBTCompound : nBTList.getTags()) {
                values.add(new WrapperConfigServerRegistryData.RegistryElement(nBTCompound));
            }
            return Collections.unmodifiableList(values);
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public NBT getData() {
            return this.data;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof WrapperConfigServerRegistryData.RegistryElement)) {
                return false;
            }
            WrapperConfigServerRegistryData.RegistryElement registryElement =
                    (WrapperConfigServerRegistryData.RegistryElement) result;
            return !this.id.equals(registryElement.id)
                    ? false
                    : Objects.equals(this.data, registryElement.data);
        }

        @Override
        public int hashCode() {
            int value = this.id.hashCode();
            return 31 * value + (this.data != null ? this.data.hashCode() : 0);
        }

        @Override
        public String toString() {
            return "RegistryElement{id=" + this.id + ", data=" + this.data + '}';
        }
    }
}
