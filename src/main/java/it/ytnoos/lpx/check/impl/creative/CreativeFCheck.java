package it.ytnoos.lpx.check.impl.creative;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;

public class CreativeFCheck extends AbstractCheck<WrapperPlayClientCreativeInventoryAction> {

    private int max;

    public CreativeFCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = ((Integer) (int) 64);
        this.max = this.calculateLimit((String) "max", ((int) value));
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientCreativeInventoryAction wrapperPlayClientCreativeInventoryAction) {
        int amount = wrapperPlayClientCreativeInventoryAction.getItemStack().getAmount();
        if (amount <= this.max) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("amount", amount)};
        this.flagViolation(wrapperPlayClientCreativeInventoryAction, flagInfos);
        return CheckResult.DENY;
    }
}
