package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json.legacyimpl;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json.LegacyHoverEventSerializer;

import org.jetbrains.annotations.NotNull;

public interface NBTLegacyHoverEventSerializer extends LegacyHoverEventSerializer {

    @NotNull
    static LegacyHoverEventSerializer get() {
        return NBTLegacyHoverEventSerializerImpl.INSTANCE;
    }
}
