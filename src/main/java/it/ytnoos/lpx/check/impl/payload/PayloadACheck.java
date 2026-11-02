package it.ytnoos.lpx.check.impl.payload;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;

public class PayloadACheck extends AbstractCheck<WrapperPlayClientPluginMessage> {

    public PayloadACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer,
            WrapperPlayClientPluginMessage wrapperPlayClientPluginMessage) {
        if (wrapperPlayClientPluginMessage.getChannelName() != null
                && !wrapperPlayClientPluginMessage.getChannelName().isEmpty()) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos = new FlagInfo[0];
        this.flagViolation(wrapperPlayClientPluginMessage, flagInfos);
        return CheckResult.DENY;
    }
}
