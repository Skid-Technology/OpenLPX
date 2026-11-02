package it.ytnoos.lpx.check.impl.netty;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException;

public class PacketLimitCheck extends AbstractCheck<PacketLimitException> {

    private final boolean preVia;

    public PacketLimitCheck(
            ProtocolPlayer protocolPlayer, CheckOptions checkOptions, boolean preVia) {
        super(protocolPlayer, checkOptions);
        this.preVia = preVia;
    }

    public CheckResult check(
            ProtocolPlayer protocolPlayer, PacketLimitException packetLimitException) {
        FlagInfo[] flagInfos =
                new FlagInfo[] {
                    FlagInfo.of("limit", packetLimitException.getLimit().getDescription()),
                    null,
                    null
                };
        flagInfos[1] = FlagInfo.of("value", packetLimitException.getValue());
        flagInfos[2] = FlagInfo.of("max", packetLimitException.getMax());
        this.flagViolation(packetLimitException, flagInfos);
        return CheckResult.DENY;
    }

    public boolean isPreVia() {
        return this.preVia;
    }
}
