package it.ytnoos.lpx.check.impl.creative;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;
import it.ytnoos.lpx.utils.ItemUtils;

public class CreativeCCheck extends AbstractCheck<WrapperPlayClientCreativeInventoryAction> {

    private int max;

    public CreativeCCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = ((Integer) (int) 3000);
        this.max = this.calculateLimit((String) "max", ((int) value));
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientCreativeInventoryAction wrapperPlayClientCreativeInventoryAction) {
        ItemStack itemStack = wrapperPlayClientCreativeInventoryAction.getItemStack();
        String text = (String) ItemUtils.getDisplayName(itemStack).orElse(null);
        if (text == null) {
            return CheckResult.ALLOW;
        }
        int size = text.length();
        if (size <= this.max) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("length", size), null};
        flagInfos[1] = FlagInfo.of("display-name", text);
        this.flagViolation(wrapperPlayClientCreativeInventoryAction, flagInfos);
        return CheckResult.DENY;
    }
}
