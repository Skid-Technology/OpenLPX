package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client.WrapperCommonClientCustomClickAction;

import org.jspecify.annotations.Nullable;

public class WrapperConfigClientCustomClickAction
        extends WrapperCommonClientCustomClickAction<WrapperConfigClientCustomClickAction> {

    public WrapperConfigClientCustomClickAction(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperConfigClientCustomClickAction(
            ResourceLocation resourceLocation, @Nullable NBT nBT) {
        super(PacketType.Configuration.Client.CUSTOM_CLICK_ACTION, resourceLocation, nBT);
    }
}
