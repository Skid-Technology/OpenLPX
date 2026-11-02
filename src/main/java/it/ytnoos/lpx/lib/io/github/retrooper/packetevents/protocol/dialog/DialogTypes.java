package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapDecoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapEncoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DialogTypes {

    private static final VersionedRegistry<DialogType<?>> REGISTRY =
            new VersionedRegistry<>("dialog_type");

    public static final DialogType<NoticeDialog> NOTICE =
            define("notice", NoticeDialog::decode, NoticeDialog::encode);

    public static final DialogType<ServerLinksDialog> SERVER_LINKS =
            define("server_links", ServerLinksDialog::decode, ServerLinksDialog::encode);

    public static final DialogType<DialogListDialog> DIALOG_LIST =
            define("dialog_list", DialogListDialog::decode, DialogListDialog::encode);

    public static final DialogType<MultiActionDialog> MULTI_ACTION =
            define("multi_action", MultiActionDialog::decode, MultiActionDialog::encode);

    public static final DialogType<ConfirmationDialog> CONFIRMATION =
            define("confirmation", ConfirmationDialog::decode, ConfirmationDialog::encode);

    private DialogTypes() {}

    public static VersionedRegistry<DialogType<?>> getRegistry() {
        return REGISTRY;
    }

    @Internal
    public static <T extends Dialog> DialogType<T> define(
            String text, NbtMapDecoder<T> nbtMapDecoder, NbtMapEncoder<T> nbtMapEncoder) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticDialogType<>(
                                result,
                                ((NbtMapDecoder) nbtMapDecoder),
                                ((NbtMapEncoder) nbtMapEncoder)));
    }

    static {
        REGISTRY.unloadMappings();
    }
}
