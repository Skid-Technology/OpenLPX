package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.body;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapDecoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapEncoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DialogBodyTypes {

    private static final VersionedRegistry<DialogBodyType<?>> REGISTRY =
            new VersionedRegistry<>("dialog_body_type");

    public static final DialogBodyType<ItemDialogBody> ITEM =
            define("item", ItemDialogBody::decode, ItemDialogBody::encode);

    public static final DialogBodyType<PlainMessageDialogBody> PLAIN_MESSAGE =
            define("plain_message", PlainMessageDialogBody::decode, PlainMessageDialogBody::encode);

    private DialogBodyTypes() {}

    @Internal
    public static <T extends DialogBody> DialogBodyType<T> define(
            String text, NbtMapDecoder<T> nbtMapDecoder, NbtMapEncoder<T> nbtMapEncoder) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticDialogBodyType<>(
                                result,
                                ((NbtMapDecoder) nbtMapDecoder),
                                ((NbtMapEncoder) nbtMapEncoder)));
    }

    public static VersionedRegistry<DialogBodyType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
