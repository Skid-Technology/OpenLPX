package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;

public class ItemACheck extends AbstractCheck<ItemStackDecodeEvent> {

    public ItemACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public static boolean isResolved(ItemStack itemStack) {
        boolean matches = itemStack.getType() == ItemTypes.WRITTEN_BOOK;
        NBTCompound nBT = itemStack.getNBT();
        if (nBT != null && nBT.getTags().containsKey("resolved")) {
            matches = nBT.getBoolean("resolved");
        }
        return matches;
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        ItemType type = itemStack.getType();
        if (isResolved(itemStack) && type != ItemTypes.WRITTEN_BOOK) {
            FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("resolved", true), null};
            flagInfos[1] = FlagInfo.of("item", type.getName());
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        } else {
            return CheckResult.ALLOW;
        }
    }

    public boolean isPreVia() {
        return true;
    }
}
