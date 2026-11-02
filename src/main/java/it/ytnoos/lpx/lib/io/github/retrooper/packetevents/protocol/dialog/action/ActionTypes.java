package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.action;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapDecoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapEncoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ActionTypes {

    private static final VersionedRegistry<ActionType<?>> REGISTRY =
            new VersionedRegistry<>("dialog_action_type");

    public static final ActionType<StaticAction> OPEN_URL =
            define("open_url", StaticAction::decode, StaticAction::encode);

    public static final ActionType<StaticAction> RUN_COMMAND =
            define("run_command", StaticAction::decode, StaticAction::encode);

    public static final ActionType<StaticAction> SUGGEST_COMMAND =
            define("suggest_command", StaticAction::decode, StaticAction::encode);

    public static final ActionType<StaticAction> SHOW_DIALOG =
            define("show_dialog", StaticAction::decode, StaticAction::encode);

    public static final ActionType<StaticAction> CHANGE_PAGE =
            define("change_page", StaticAction::decode, StaticAction::encode);

    public static final ActionType<StaticAction> COPY_TO_CLIPBOARD =
            define("copy_to_clipboard", StaticAction::decode, StaticAction::encode);

    public static final ActionType<StaticAction> CUSTOM =
            define("custom", StaticAction::decode, StaticAction::encode);

    public static final ActionType<DynamicRunCommandAction> DYNAMIC_RUN_COMMAND =
            define(
                    "dynamic/run_command",
                    DynamicRunCommandAction::decode,
                    DynamicRunCommandAction::encode);

    public static final ActionType<DynamicCustomAction> DYNAMIC_CUSTOM =
            define("dynamic/custom", DynamicCustomAction::decode, DynamicCustomAction::encode);

    private ActionTypes() {}

    @Internal
    public static <T extends Action> ActionType<T> define(
            String text, NbtMapDecoder<T> nbtMapDecoder, NbtMapEncoder<T> nbtMapEncoder) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticActionType<>(
                                result,
                                ((NbtMapDecoder) nbtMapDecoder),
                                ((NbtMapEncoder) nbtMapEncoder)));
    }

    public static VersionedRegistry<ActionType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
