package it.ytnoos.lpx.check.impl.creative;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.utils.PacketUtils;

import java.util.List;

public class CreativeECheck extends AbstractCheck<ItemStackDecodeEvent> {

    private static final String[] TEXTS;

    private List<String> blacklist;

    public CreativeECheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        String[] texts = TEXTS;
        this.blacklist = this.createValues((String) "blacklist", texts);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        ItemType type = itemStack.getType();
        if (type != ItemTypes.BOOK
                && type != ItemTypes.WRITABLE_BOOK
                && type != ItemTypes.WRITTEN_BOOK) {
            return CheckResult.ALLOW;
        }
        NBTCompound nBT = itemStack.getNBT();
        if (nBT == null) {
            return CheckResult.ALLOW;
        }
        for (String text : this.blacklist) {
            NBT nBT2 = (NBT) nBT;
            if (PacketUtils.testText(nBT2, text)) {
                FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("blacklisted", text)};
                this.flagViolation(itemStackDecodeEvent, flagInfos);
                return CheckResult.DENY;
            }
        }
        return CheckResult.ALLOW;
    }

    static {
        String[] texts = new String[] {"run_command", null};
        texts[1] = "translation.test.invalid";
        TEXTS = texts;
    }
}
