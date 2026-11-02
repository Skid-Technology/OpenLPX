package it.ytnoos.lpx.check.impl.creative;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;
import it.ytnoos.lpx.utils.ItemUtils;

public class CreativeDCheck extends AbstractCheck<WrapperPlayClientCreativeInventoryAction> {

    private int max;

    public CreativeDCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = ((Integer) (int) 16384);
        this.max = this.calculateLimit((String) "max", ((int) value));
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientCreativeInventoryAction wrapperPlayClientCreativeInventoryAction) {
        ItemStack itemStack = wrapperPlayClientCreativeInventoryAction.getItemStack();
        NBTList nBTList = (NBTList) ItemUtils.createOptional(itemStack).orElse(null);
        if (nBTList != null && !nBTList.isEmpty()) {
            int value = 0;
            for (NBTString nBTString : ((Iterable<NBTString>) ((Iterable<?>) nBTList.getTags()))) {
                value += nBTString.getValue().length();
            }
            if (value <= this.max) {
                return CheckResult.ALLOW;
            }
            FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("lore-length", value)};
            this.flagViolation(wrapperPlayClientCreativeInventoryAction, flagInfos);
            return CheckResult.DENY;
        } else {
            return CheckResult.ALLOW;
        }
    }
}
