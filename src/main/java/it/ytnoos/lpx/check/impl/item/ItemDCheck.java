package it.ytnoos.lpx.check.impl.item;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;

public class ItemDCheck extends AbstractCheck<ItemStackDecodeEvent> {

    private int maxBytes;

    private int limit;

    private boolean resize;

    public ItemDCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = ((Integer) (int) 255);
        String text = (String) "max-bytes";
        String text2 = text;
        this.maxBytes = this.calculateLimit(text2, ((int) value));
        Integer value2 = Integer.valueOf((byte) 3);
        String text3 = "resolved-tolerance";
        text = text3;
        String text4 = text;
        this.limit = this.calculateLimit(text4, ((int) value2));
        Boolean matches = ((Boolean) (boolean) true);
        String text5 = "resize";
        text = text5;
        String text6 = text;
        this.resize = this.testEnabled(text6, ((boolean) matches));
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        NBTCompound nBT = itemStackDecodeEvent.getItemStack().getNBT();
        if (nBT == null) {
            return CheckResult.ALLOW;
        }
        NBTList stringListTagOrNull = nBT.getStringListTagOrNull("pages");
        if (stringListTagOrNull == null) {
            return CheckResult.ALLOW;
        }
        boolean matches = false;
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        boolean resolved = ItemACheck.isResolved(itemStack);
        for (int index = 0; index < stringListTagOrNull.size(); index++) {
            NBTString nBTString = (NBTString) stringListTagOrNull.getTag(index);
            byte[] bytes = nBTString.getValue().getBytes();
            int value = bytes.length;
            if (value > this.maxBytes) {
                if (!resolved || value > this.maxBytes * this.limit) {
                    FlagInfo[] flagInfos = new FlagInfo[4];
                    flagInfos[0] = FlagInfo.of("pages", stringListTagOrNull.size());
                    flagInfos[1] = FlagInfo.of("page-index", index);
                    flagInfos[2] = FlagInfo.of("page-bytes", value);
                    flagInfos[3] = FlagInfo.of("resolved", resolved);
                    this.flagViolation(itemStackDecodeEvent, flagInfos);
                    return CheckResult.DENY;
                }
                if (this.resize) {
                    String text = new String(bytes, 0, this.maxBytes);
                    nBTString.setValue(text);
                    matches = true;
                }
            }
        }
        return matches ? CheckResult.CHANGE : CheckResult.ALLOW;
    }

    public boolean isPreVia() {
        return true;
    }
}
