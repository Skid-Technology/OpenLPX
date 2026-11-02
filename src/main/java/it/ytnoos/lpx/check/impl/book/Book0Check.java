package it.ytnoos.lpx.check.impl.book;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;

import java.util.ArrayList;

public class Book0Check extends AbstractCheck<WrapperPlayClientEditBook> {

    public Book0Check(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer, WrapperPlayClientEditBook wrapperPlayClientEditBook) {
        ItemStack itemStack = wrapperPlayClientEditBook.getItemStack();
        NBTList nBTList = new NBTList(NBTType.STRING);
        NBTList nBTList2 = nBTList;
        NBTCompound nBTCompound;
        if (itemStack == null) {
            NBTCompound nBTCompound2 = new NBTCompound();
            nBTCompound = nBTCompound2;
            for (String text : wrapperPlayClientEditBook.getPages()) {
                NBTString nBTString = new NBTString(text);
                nBTList2.addTag(nBTString);
            }
            nBTCompound.setTag("pages", nBTList2);
            itemStack =
                    ItemStack.builder()
                            .type(ItemTypes.WRITABLE_BOOK)
                            .nbt(nBTCompound)
                            .amount(1)
                            .build();
        } else {
            NBTCompound nBT = itemStack.getNBT();
            nBTCompound = nBT;
            if (nBT != null) {
                NBTList stringListTagOrNull = nBTCompound.getStringListTagOrNull("pages");
                if (stringListTagOrNull != null) {
                    nBTList2 = stringListTagOrNull;
                }
            } else {
                NBTCompound nBTCompound3 = new NBTCompound();
                nBTCompound = nBTCompound3;
                itemStack.setNBT(nBTCompound);
            }
        }
        protocolPlayer.setProcessingPayload(true);
        try {
            NBTDecodeEvent nBTDecodeEvent =
                    new NBTDecodeEvent(wrapperPlayClientEditBook, null, nBTCompound);
            CheckResult checkResult =
                    protocolPlayer
                            .handleChecks(NBTDecodeEvent.class, nBTDecodeEvent, this.isPreVia())
                            .result();
            CheckResult checkResult2 = checkResult;
            if (checkResult == CheckResult.DENY) {
                return checkResult2;
            }
            ItemStackDecodeEvent itemStackDecodeEvent =
                    new ItemStackDecodeEvent(wrapperPlayClientEditBook, null, itemStack);
            CheckResult checkResult3 =
                    checkResult2.merge(
                            protocolPlayer
                                    .handleChecks(
                                            ItemStackDecodeEvent.class,
                                            itemStackDecodeEvent,
                                            this.isPreVia())
                                    .result());
            checkResult2 = checkResult3;
            if (checkResult3 == CheckResult.DENY) {
                return checkResult2;
            }
            if (checkResult2 == CheckResult.CHANGE) {
                ArrayList<String> values = new ArrayList<>();
                for (NBTString nBTString2 :
                        ((Iterable<NBTString>) ((Iterable<?>) nBTList2.getTags()))) {
                    values.add(nBTString2.getValue());
                }
                wrapperPlayClientEditBook.setPages(values);
            }
            return checkResult2;
        } finally {
            protocolPlayer.setProcessingPayload(false);
        }
    }

    public boolean isPreVia() {
        return true;
    }
}
