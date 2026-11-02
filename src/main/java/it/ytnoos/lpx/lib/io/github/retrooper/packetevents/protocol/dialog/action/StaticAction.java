package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.action;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.clickevent.ClickEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.clickevent.ClickEventAction;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.clickevent.ClickEventActions;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class StaticAction implements Action {

    private final ActionType<?> actionType;

    private final ClickEvent clickEvent;

    public StaticAction(ClickEvent clickEvent2) {
        if (!clickEvent2.getAction().isAllowFromServer()) {
            throw new IllegalArgumentException(
                    "Can't create action for unreadable click event with action "
                            + clickEvent2.getAction());
        }
        this.actionType =
                ActionTypes.getRegistry().getByNameOrThrow(clickEvent2.getAction().getName());
        this.clickEvent = clickEvent2;
    }

    public static StaticAction decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        String stringTagValueOrThrow = nBTCompound.getStringTagValueOrThrow("type");
        ClickEvent clickEvent =
                ClickEventActions.getRegistry()
                        .getByNameOrThrow(stringTagValueOrThrow)
                        .decode(nBTCompound, packetWrapper);
        return new StaticAction(clickEvent);
    }

    public static void encode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, StaticAction staticAction) {
        ((ClickEventAction<ClickEvent>) staticAction.clickEvent.getAction())
                .encode(nBTCompound, packetWrapper, staticAction.clickEvent);
    }

    @Override
    public ActionType<?> getType() {
        return this.actionType;
    }

    public ClickEvent getClickEvent() {
        return this.clickEvent;
    }
}
