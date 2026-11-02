package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionTypeRef;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;

import org.jetbrains.annotations.Nullable;

@Deprecated
public class Dimension {

    private int id;

    private NBTCompound attributes;

    @Deprecated
    public Dimension(DimensionType dimensionType) {
        this.id = dimensionType.getId();
        this.attributes = new NBTCompound();
    }

    public Dimension(int id) {
        this.id = id;
        this.attributes = new NBTCompound();
    }

    public Dimension(NBTCompound nBTCompound) {
        this.attributes = nBTCompound;
    }

    @Deprecated
    public Dimension(int id, NBTCompound nBTCompound) {
        this.id = id;
        this.attributes = nBTCompound;
    }

    public static Dimension fromDimensionTypeRef(DimensionTypeRef dimensionTypeRef) {
        if (dimensionTypeRef instanceof DimensionTypeRef.NameRef) {
            Dimension dimension;
            (dimension = new Dimension(0)).setDimensionName(dimensionTypeRef.getName().toString());
            return dimension;
        } else if (dimensionTypeRef instanceof DimensionTypeRef.IdRef) {
            return new Dimension(dimensionTypeRef.getId());
        } else if (dimensionTypeRef instanceof DimensionTypeRef.DataRef) {
            return new Dimension((NBTCompound) dimensionTypeRef.getData());
        } else if (dimensionTypeRef instanceof DimensionTypeRef.DirectRef) {
            return fromDimensionType(
                    ((DimensionTypeRef.DirectRef) dimensionTypeRef).getDimensionType(),
                    null,
                    ((DimensionTypeRef.DirectRef) dimensionTypeRef).getVersion());
        } else {
            throw new UnsupportedOperationException(
                    "Unsupported DimensionTypeRef implementation: " + dimensionTypeRef);
        }
    }

    public static Dimension fromDimensionType(
            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension
                            .DimensionType
                    dimensionType,
            @Nullable User user,
            @Nullable ClientVersion clientVersion) {
        if (clientVersion == null) {
            clientVersion =
                    user != null && PacketEvents.getAPI().getInjector().isProxy()
                            ? user.getClientVersion()
                            : PacketEvents.getAPI()
                                    .getServerManager()
                                    .getVersion()
                                    .toClientVersion();
        }
        NBTCompound nBTCompound =
                (NBTCompound)
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension
                                .DimensionType.encode(dimensionType, clientVersion);
        return new Dimension(dimensionType.getId(clientVersion), nBTCompound);
    }

    public DimensionTypeRef asDimensionTypeRef() {
        if (this.attributes == null) {
            return new DimensionTypeRef.IdRef(this.id);
        }
        if (this.attributes.size() > 1) {
            return new DimensionTypeRef.DataRef(this.attributes);
        }
        ResourceLocation resourceLocation = new ResourceLocation(this.getDimensionName());
        return new DimensionTypeRef.NameRef(resourceLocation);
    }

    public it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionType
            asDimensionType(@Nullable User user, @Nullable ClientVersion clientVersion) {
        if (clientVersion == null) {
            clientVersion =
                    user != null && PacketEvents.getAPI().getInjector().isProxy()
                            ? user.getClientVersion()
                            : PacketEvents.getAPI()
                                    .getServerManager()
                                    .getVersion()
                                    .toClientVersion();
        }
        IRegistry registry =
                user != null
                        ? user.getRegistryOr(DimensionTypes.getRegistry(), clientVersion)
                        : DimensionTypes.getRegistry();
        String text;
        return !(text = this.getDimensionName()).isEmpty()
                ? (it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension
                                .DimensionType)
                        registry.getByName(new ResourceLocation(text))
                : (it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension
                                .DimensionType)
                        registry.getById(clientVersion, this.id);
    }

    public String getDimensionName() {
        return this.getAttributes().getStringTagValueOrDefault("effects", "");
    }

    public void setDimensionName(String text) {
        NBTCompound nBTCompound;
        (nBTCompound = this.getAttributes()).setTag("effects", new NBTString(text));
        this.setAttributes(nBTCompound);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Deprecated
    public DimensionType getType() {
        return DimensionType.getById(this.id);
    }

    @Deprecated
    public void setType(DimensionType dimensionType) {
        this.id = dimensionType.getId();
    }

    public NBTCompound getAttributes() {
        return this.attributes;
    }

    public void setAttributes(NBTCompound nBTCompound) {
        this.attributes = nBTCompound;
    }
}
