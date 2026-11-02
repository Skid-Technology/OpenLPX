package it.ytnoos.lpx.check.impl.flood;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;

public class FloodACheck extends AbstractCheck<PacketReceiveEvent> {

    private RollingIntervalCounter rollingIntervalCounter;

    public FloodACheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = ((Integer) (int) 1100);
        RollingIntervalCounter rollingIntervalCounter2 =
                new RollingIntervalCounter(this.calculateLimit((String) "max", ((int) value)));
        this.rollingIntervalCounter = rollingIntervalCounter2;
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, PacketReceiveEvent packetReceiveEvent) {
        if (protocolPlayer.getPrinterData().isUsingPrinter()) {
            return CheckResult.ALLOW;
        }
        if (this.rollingIntervalCounter.increment()) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {
                        FlagInfo.of("pps", this.rollingIntervalCounter.getMaxCounter())
                    };
            this.flagViolation(packetReceiveEvent, flagInfos);
        }
        return CheckResult.ALLOW;
    }
}
