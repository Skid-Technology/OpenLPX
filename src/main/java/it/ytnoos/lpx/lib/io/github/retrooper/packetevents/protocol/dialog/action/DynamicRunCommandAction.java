package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.action;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class DynamicRunCommandAction implements Action {

    private final DialogTemplate template;

    public DynamicRunCommandAction(DialogTemplate dialogTemplate) {
        this.template = dialogTemplate;
    }

    public static DynamicRunCommandAction decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        DialogTemplate orThrow =
                nBTCompound.getOrThrow("template", DialogTemplate::decode, packetWrapper);
        return new DynamicRunCommandAction(orThrow);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            DynamicRunCommandAction dynamicRunCommandAction) {
        nBTCompound.set(
                "template",
                dynamicRunCommandAction.template,
                DialogTemplate::encode,
                packetWrapper);
    }

    public DialogTemplate getTemplate() {
        return this.template;
    }

    @Override
    public ActionType<?> getType() {
        return ActionTypes.DYNAMIC_RUN_COMMAND;
    }
}
