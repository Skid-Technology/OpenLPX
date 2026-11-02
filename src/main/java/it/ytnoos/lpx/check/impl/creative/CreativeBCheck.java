package it.ytnoos.lpx.check.impl.creative;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment.Enchantment;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;

public class CreativeBCheck extends AbstractCheck<WrapperPlayClientCreativeInventoryAction> {

    private int max;

    private int min;

    public CreativeBCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = ((Integer) (int) 127);
        String text = (String) "max";
        String text2 = text;
        this.max = this.calculateLimit(text2, ((int) value));
        Integer value2 = Integer.valueOf((byte) 0);
        String text3 = "min";
        text = text3;
        String text4 = text;
        this.min = this.calculateLimit(text4, ((int) value2));
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientCreativeInventoryAction wrapperPlayClientCreativeInventoryAction) {
        try {
            for (Enchantment enchantment :
                    wrapperPlayClientCreativeInventoryAction
                            .getItemStack()
                            .getEnchantments(protocolPlayer.getUser().getClientVersion())) {
                int level = enchantment.getLevel();
                if (level > this.max || level < this.min) {
                    FlagInfo[] flagInfos =
                            new FlagInfo[] {
                                FlagInfo.of("enchantment", enchantment.getType().getName()), null
                            };
                    flagInfos[1] = FlagInfo.of("enchantment-level", level);
                    this.flagViolation(wrapperPlayClientCreativeInventoryAction, flagInfos);
                    return CheckResult.DENY;
                }
            }
        } catch (Exception exception) {
            return CheckResult.ALLOW;
        }
        return CheckResult.ALLOW;
    }
}
