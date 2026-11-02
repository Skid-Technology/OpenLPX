package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class SimpleTypesBuilderData extends TypesBuilderData {

    public SimpleTypesBuilderData(ResourceLocation resourceLocation, int value) {
        super(resourceLocation, new int[] {value});
    }

    @Override
    public int getId(ClientVersion clientVersion) {
        return this.ids[0];
    }
}
