package it.ytnoos.lpx.check.impl.book;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import it.ytnoos.lpx.utils.TimeUtils;

public class BookBCheck extends AbstractCheck<WrapperPlayClientEditBook> {

    private long last;

    private int delay;

    private RollingIntervalCounter rollingIntervalCounter;

    public BookBCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value = Integer.valueOf((short) 1000);
        this.delay = this.calculateLimit((String) "delay", ((int) value));
        Integer value2 = Integer.valueOf((byte) 15);
        RollingIntervalCounter rollingIntervalCounter2 =
                new RollingIntervalCounter(this.calculateLimit((String) "max", ((int) value2)));
        this.rollingIntervalCounter = rollingIntervalCounter2;
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer, WrapperPlayClientEditBook wrapperPlayClientEditBook) {
        if (this.rollingIntervalCounter.increment()) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {
                        FlagInfo.of("pps", this.rollingIntervalCounter.getMaxCounter())
                    };
            this.flagViolation(wrapperPlayClientEditBook, flagInfos);
            return CheckResult.DENY;
        } else if (this.rollingIntervalCounter.isOverLimit()) {
            return CheckResult.DENY;
        } else {
            long value = this.last;
            Long value2 = ((Long) value);
            long value3 = TimeUtils.calculateTime(value2);
            this.last = TimeUtils.current();
            if (value3 < this.delay) {
                FlagInfo[] flagInfos2 = new FlagInfo[] {FlagInfo.of("delay", value3)};
                this.flagViolation(wrapperPlayClientEditBook, flagInfos2);
                return CheckResult.DENY;
            } else {
                return CheckResult.ALLOW;
            }
        }
    }

    public boolean isPreVia() {
        return true;
    }
}
