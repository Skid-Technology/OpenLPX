package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client.WrapperCommonClientCustomClickAction;

import org.jspecify.annotations.Nullable;

public class WrapperPlayClientCustomClickAction
        extends WrapperCommonClientCustomClickAction<WrapperPlayClientCustomClickAction> {

    public WrapperPlayClientCustomClickAction(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientCustomClickAction(
            ResourceLocation resourceLocation, @Nullable NBT nBT) {
        super(PacketType.Play.Client.CUSTOM_CLICK_ACTION, resourceLocation, nBT);
    }
}
