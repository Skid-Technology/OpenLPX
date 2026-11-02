package it.ytnoos.lpx.check.impl.flood;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RepeatedIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ReadSlotsEvent;

import org.bukkit.configuration.file.FileConfiguration;

public class FloodGCheck extends AbstractCheck<ReadSlotsEvent> {

    private RepeatedIntervalCounter counter;

    private boolean cancel;

    private int limit;

    private double value;

    public FloodGCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        FileConfiguration fileConfiguration = ((FileConfiguration) this.getCheckConfiguration());
        int intValue = fileConfiguration.getInt(this.createText((String) "max"), 70);
        fileConfiguration = ((FileConfiguration) this.getCheckConfiguration());
        int intValue2 = fileConfiguration.getInt(this.createText((String) "interval"), 250);
        fileConfiguration = ((FileConfiguration) this.getCheckConfiguration());
        int intValue3 = fileConfiguration.getInt(this.createText((String) "periods"), 5);
        fileConfiguration = ((FileConfiguration) this.getCheckConfiguration());
        int intValue4 = fileConfiguration.getInt(this.createText((String) "warnings"), 2);
        RepeatedIntervalCounter repeatedIntervalCounter =
                new RepeatedIntervalCounter(intValue, intValue2, intValue3, intValue4);
        this.counter = repeatedIntervalCounter;
        FileConfiguration fileConfiguration2 = ((FileConfiguration) this.getCheckConfiguration());
        this.cancel = fileConfiguration2.getBoolean(this.createText((String) "cancel"), true);
        Integer value2 = Integer.valueOf((byte) 3);
        String text = (String) "low-slots-threshold";
        String text2 = text;
        this.limit = Math.max(0, this.calculateLimit(text2, ((int) value2)));
        Double value3 = ((Double) (double) 0.7);
        String text3 = "low-slots-multiplier";
        text = text3;
        String text4 = text;
        this.value = Math.max(0.1, this.calculateValue(text4, ((double) value3)));
    }

    protected CheckResult check(ProtocolPlayer protocolPlayer, ReadSlotsEvent readSlotsEvent) {
        double value2 = readSlotsEvent.getSlotsCount() * protocolPlayer.itemFloodMultiplier();
        if (readSlotsEvent.getSlotsCount() <= this.limit) {
            value2 *= this.value;
        }
        if (this.counter.increment(value2)) {
            FlagInfo[] flagInfos = new FlagInfo[4];
            flagInfos[0] = FlagInfo.of("max-counter", this.counter.getMaxCounter());
            flagInfos[1] = FlagInfo.of("interval", this.counter.getInterval() + "ms");
            flagInfos[2] = FlagInfo.of("slots-count", readSlotsEvent.getSlotsCount());
            flagInfos[3] = FlagInfo.of("amount", value2);
            this.flagViolation(readSlotsEvent, flagInfos);
            return this.cancel ? CheckResult.DENY : CheckResult.ALLOW;
        } else {
            return this.cancel && this.counter.isOverLimit() ? CheckResult.DENY : CheckResult.ALLOW;
        }
    }

    public boolean isPreVia() {
        return true;
    }
}
