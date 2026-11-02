package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class TagKey {

    public static final NbtCodec<TagKey> CODEC =
            NbtCodecs.STRING.apply(
                    result -> {
                        TagKey tagKey;
                        if ((tagKey = tryParse(result)) == null) {
                            throw new NbtCodecException("Not a tag: " + result);
                        } else {
                            return tagKey;
                        }
                    },
                    TagKey::toString);

    private final ResourceLocation id;

    public TagKey(ResourceLocation resourceLocation) {
        this.id = resourceLocation;
    }

    public static TagKey parse(String text) {
        TagKey tagKey;
        if ((tagKey = tryParse(text)) == null) {
            throw new IllegalArgumentException("Not a tag: " + text);
        } else {
            return tagKey;
        }
    }

    @Nullable
    public static TagKey tryParse(String text) {
        return !text.isEmpty() && text.charAt(0) == '#'
                ? new TagKey(new ResourceLocation(text.substring(1)))
                : null;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return '#' + this.id.toString();
    }
}
