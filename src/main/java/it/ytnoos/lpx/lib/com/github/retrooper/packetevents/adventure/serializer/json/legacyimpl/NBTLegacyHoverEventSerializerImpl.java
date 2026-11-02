package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json.legacyimpl;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.BackwardCompatUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json.LegacyHoverEventSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.CompoundBinaryTag;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.TagStringIO;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.util.Codec;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

final class NBTLegacyHoverEventSerializerImpl implements LegacyHoverEventSerializer {

    static final NBTLegacyHoverEventSerializerImpl INSTANCE =
            new NBTLegacyHoverEventSerializerImpl();

    private static final TagStringIO SNBT_IO;

    private static final Codec<CompoundBinaryTag, String, IOException, IOException> SNBT_CODEC;

    static final String ITEM_TYPE = "id";

    static final String ITEM_COUNT = "Count";

    static final String ITEM_TAG = "tag";

    static final String ENTITY_NAME = "name";

    static final String ENTITY_TYPE = "type";

    static final String ENTITY_ID = "id";

    private NBTLegacyHoverEventSerializerImpl() {}

    @Override
    public HoverEvent.@NotNull ShowItem deserializeShowItem(@NotNull Component component)
            throws IOException {
        assertTextComponent(component);
        CompoundBinaryTag compoundBinaryTag;
        CompoundBinaryTag compound =
                (compoundBinaryTag = SNBT_CODEC.decode(((TextComponent) component).content()))
                        .getCompound("tag");
        return BackwardCompatUtil.createShowItem(
                Key.key(compoundBinaryTag.getString("id")),
                compoundBinaryTag.getByte("Count", (byte) 1),
                compound == CompoundBinaryTag.empty()
                        ? null
                        : BinaryTagHolder.encode(compound, SNBT_CODEC));
    }

    @NotNull
    @Override
    public Component serializeShowItem(HoverEvent.@NotNull ShowItem showItem) throws IOException {
        CompoundBinaryTag.Builder builder2 =
                CompoundBinaryTag.builder()
                        .putString("id", showItem.item().asString())
                        .putByte("Count", (byte) showItem.count());
        BinaryTagHolder binaryTagHolder;
        if ((binaryTagHolder = showItem.nbt()) != null) {
            builder2.put("tag", binaryTagHolder.get(SNBT_CODEC));
        }
        return Component.text(SNBT_CODEC.encode(builder2.build()));
    }

    @Override
    public HoverEvent.@NotNull ShowEntity deserializeShowEntity(
            @NotNull Component component,
            Codec.Decoder<Component, String, ? extends RuntimeException> decoder)
            throws IOException {
        assertTextComponent(component);
        CompoundBinaryTag compoundBinaryTag;
        return BackwardCompatUtil.createShowEntity(
                Key.key(
                        (compoundBinaryTag =
                                        SNBT_CODEC.decode(((TextComponent) component).content()))
                                .getString("type")),
                UUID.fromString(compoundBinaryTag.getString("id")),
                (Component) decoder.decode(compoundBinaryTag.getString("name")));
    }

    @NotNull
    @Override
    public Component serializeShowEntity(
            HoverEvent.@NotNull ShowEntity showEntity,
            Codec.Encoder<Component, String, ? extends RuntimeException> encoder)
            throws IOException {
        CompoundBinaryTag.Builder builder2 =
                CompoundBinaryTag.builder()
                        .putString("id", showEntity.id().toString())
                        .putString("type", showEntity.type().asString());
        Component component;
        if ((component = showEntity.name()) != null) {
            builder2.putString("name", (String) encoder.encode(component));
        }
        return Component.text(SNBT_CODEC.encode(builder2.build()));
    }

    private static void assertTextComponent(Component component) {
        if (!(component instanceof TextComponent) || !component.children().isEmpty()) {
            throw new IllegalArgumentException("Legacy events must be single Component instances");
        }
    }

    static {
        TagStringIO tagStringIO2;
        try {
            tagStringIO2 = TagStringIO.tagStringIO();
        } catch (Throwable throwable) {
            tagStringIO2 = TagStringIO.get();
        }
        SNBT_IO = tagStringIO2;
        Codec.Decoder<CompoundBinaryTag, String, IOException> decoder = SNBT_IO::asCompound;
        SNBT_CODEC = BackwardCompatUtil.createCodec(decoder, SNBT_IO::asString);
    }
}
