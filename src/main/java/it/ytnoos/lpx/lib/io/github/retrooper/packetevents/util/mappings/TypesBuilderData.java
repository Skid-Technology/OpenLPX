package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.VersionRange;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class TypesBuilderData {

    protected final ResourceLocation name;

    protected final int[] ids;

    protected final TypesBuilder typesBuilder;

    protected final VersionRange versions;

    @Deprecated
    public TypesBuilderData(ResourceLocation resourceLocation, int[] values) {
        this(resourceLocation, values, new TypesBuilder("", true), VersionRange.ALL_VERSIONS);
    }

    @Internal
    public TypesBuilderData(
            ResourceLocation resourceLocation,
            int[] ids,
            TypesBuilder typesBuilder2,
            VersionRange versionRange) {
        this.name = resourceLocation;
        this.ids = ids;
        this.typesBuilder = typesBuilder2;
        this.versions = versionRange;
    }

    public int getId(ClientVersion clientVersion) {
        return this.ids[this.typesBuilder.getDataIndex(clientVersion)];
    }

    public ResourceLocation getName() {
        return this.name;
    }

    @Deprecated
    public int[] getData() {
        return this.ids;
    }

    public VersionRange getVersions() {
        return this.versions;
    }
}
