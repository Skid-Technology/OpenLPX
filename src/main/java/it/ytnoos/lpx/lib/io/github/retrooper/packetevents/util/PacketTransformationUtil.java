package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.Equipment;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.LightData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChunkData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEquipment;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetCursorItem;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetPlayerInventory;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetSlot;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateLight;

import java.util.Collections;

public class PacketTransformationUtil {

    public static PacketWrapper<?>[] transform(PacketWrapper<?> packetWrapper) {
        if (packetWrapper instanceof WrapperPlayServerDestroyEntities) {
            WrapperPlayServerDestroyEntities wrapperPlayServerDestroyEntities;
            int value =
                    (wrapperPlayServerDestroyEntities =
                                    (WrapperPlayServerDestroyEntities) packetWrapper)
                            .getEntityIds()
                            .length;
            if (packetWrapper.getServerVersion() == ServerVersion.V_1_17 && value > 1) {
                PacketWrapper[] packetWrappers = new PacketWrapper[value];
                for (int index = 0; index < value; index++) {
                    int value2 = wrapperPlayServerDestroyEntities.getEntityIds()[index];
                    packetWrappers[index] = new WrapperPlayServerDestroyEntities(value2);
                }
                return packetWrappers;
            }
        } else if (packetWrapper instanceof WrapperPlayServerEntityEquipment) {
            WrapperPlayServerEntityEquipment wrapperPlayServerEntityEquipment;
            int size2 =
                    (wrapperPlayServerEntityEquipment =
                                    (WrapperPlayServerEntityEquipment) packetWrapper)
                            .getEquipment()
                            .size();
            if (wrapperPlayServerEntityEquipment
                            .getServerVersion()
                            .isOlderThan(ServerVersion.V_1_16)
                    && size2 > 1) {
                PacketWrapper[] packetWrappers2 = new PacketWrapper[size2];
                for (int index2 = 0; index2 < size2; index2++) {
                    Equipment equipment =
                            wrapperPlayServerEntityEquipment.getEquipment().get(index2);
                    packetWrappers2[index2] =
                            new WrapperPlayServerEntityEquipment(
                                    wrapperPlayServerEntityEquipment.getEntityId(),
                                    Collections.singletonList(equipment));
                }
                return packetWrappers2;
            }
        } else if (packetWrapper instanceof WrapperPlayServerChunkData) {
            WrapperPlayServerChunkData wrapperPlayServerChunkData;
            LightData lightData =
                    (wrapperPlayServerChunkData = (WrapperPlayServerChunkData) packetWrapper)
                            .getLightData();
            if (wrapperPlayServerChunkData.getServerVersion().isOlderThan(ServerVersion.V_1_18)
                    && lightData != null) {
                PacketWrapper[] packetWrappers3;
                (packetWrappers3 = new PacketWrapper[2])[0] =
                        new WrapperPlayServerUpdateLight(
                                wrapperPlayServerChunkData.getColumn().getX(),
                                wrapperPlayServerChunkData.getColumn().getZ(),
                                lightData);
                packetWrappers3[1] = wrapperPlayServerChunkData;
                return packetWrappers3;
            }
        } else if (packetWrapper instanceof WrapperPlayServerSetSlot) {
            WrapperPlayServerSetSlot wrapperPlayServerSetSlot;
            if ((wrapperPlayServerSetSlot = (WrapperPlayServerSetSlot) packetWrapper).getSlot()
                    == -1) {
                if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
                    packetWrapper =
                            new WrapperPlayServerSetCursorItem(wrapperPlayServerSetSlot.getItem());
                }
            } else if (wrapperPlayServerSetSlot.getWindowId() == -2
                    && packetWrapper
                            .getServerVersion()
                            .isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
                packetWrapper =
                        new WrapperPlayServerSetPlayerInventory(
                                wrapperPlayServerSetSlot.getSlot(),
                                wrapperPlayServerSetSlot.getItem());
            }
        }
        return new PacketWrapper[] {packetWrapper};
    }
}
