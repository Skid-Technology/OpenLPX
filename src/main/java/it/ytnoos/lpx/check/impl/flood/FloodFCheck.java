package it.ytnoos.lpx.check.impl.flood;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class FloodFCheck extends AbstractCheck<PlayerCommandPreprocessEvent> {

    private RollingIntervalCounter rollingIntervalCounter;

    public FloodFCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = ((Integer) (int) 8);
        RollingIntervalCounter rollingIntervalCounter2 =
                new RollingIntervalCounter(this.calculateLimit((String) "max", ((int) value)));
        this.rollingIntervalCounter = rollingIntervalCounter2;
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer,
            PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        if (this.rollingIntervalCounter.increment()) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {
                        FlagInfo.of("command", playerCommandPreprocessEvent.getMessage())
                    };
            this.flagViolation(playerCommandPreprocessEvent, flagInfos);
            return CheckResult.DENY;
        } else {
            return this.rollingIntervalCounter.isOverLimit() ? CheckResult.DENY : CheckResult.ALLOW;
        }
    }
}
