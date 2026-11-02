package it.ytnoos.lpx.check.impl.flood;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RepeatedIntervalCounter;
import it.ytnoos.lpx.api.check.RollingIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.utils.ItemUtils;

public class FloodCCheck extends AbstractCheck<ItemStackDecodeEvent> {

    private RepeatedIntervalCounter repeatedIntervalCounter;

    private RollingIntervalCounter rollingIntervalCounter;

    private long time;

    private long time2;

    private double value;

    public FloodCCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        Integer value2 = Integer.valueOf((byte) 100);
        String text = (String) "reset-interval";
        String text2 = text;
        int value3 = this.calculateLimit(text2, ((int) value2));
        Integer value4 = Integer.valueOf((byte) 6);
        String text3 = "max-periods";
        text = text3;
        String text4 = text;
        int value5 = this.calculateLimit(text4, ((int) value4));
        Integer value6 = Integer.valueOf((byte) 3);
        String text5 = "max-warnings";
        text = text5;
        String text6 = text;
        int value7 = this.calculateLimit(text6, ((int) value6));
        Integer value8 = Integer.valueOf((byte) 32);
        String text7 = "sustained-max";
        text = text7;
        String text8 = text;
        int value9 = this.calculateLimit(text8, ((int) value8));
        Integer value10 = Integer.valueOf((short) 1000);
        String text9 = "sustained-interval";
        text = text9;
        String text10 = text;
        int value11 = this.calculateLimit(text10, ((int) value10));
        Long value12 = ((Long) 6000L);
        String text11 = (String) "big-threshold";
        String text12 = text11;
        this.time = this.calculateTime(text12, ((long) value12));
        Long value13 = ((Long) 512L);
        String text13 = "harmless-threshold";
        text11 = text13;
        String text14 = text11;
        this.time2 = this.calculateTime(text14, ((long) value13));
        Double value14 = ((Double) (double) 0.05);
        this.value =
                Math.max(
                        0.01,
                        this.calculateValue((String) "harmless-multiplier", ((double) value14)));
        Integer value15 = Integer.valueOf((byte) 20);
        RepeatedIntervalCounter repeatedIntervalCounter2 =
                new RepeatedIntervalCounter(
                        this.calculateLimit((String) "max", ((int) value15)),
                        value3,
                        value5,
                        value7);
        this.repeatedIntervalCounter = repeatedIntervalCounter2;
        this.rollingIntervalCounter =
                value9 >= 0 ? new RollingIntervalCounter(value9, value11) : null;
    }

    protected CheckResult check(
            ProtocolPlayer protocolPlayer, ItemStackDecodeEvent itemStackDecodeEvent) {
        if (protocolPlayer.getPrinterData().isUsingPrinter()) {
            return CheckResult.ALLOW;
        }
        int decodedSizeBytes = itemStackDecodeEvent.getItemStack().getDecodedSizeBytes();
        if (decodedSizeBytes <= 0) {
            return CheckResult.ALLOW;
        }
        double value2 = (int) (decodedSizeBytes / this.time / 4L);
        double value3 = value2;
        if (value2 == 0.0) {
            value3 = 1.0;
        }
        if (decodedSizeBytes < this.time) {
            value3 = 0.25;
        }
        if (decodedSizeBytes <= this.time2) {
            value3 *= this.value;
        }
        ItemStack itemStack = itemStackDecodeEvent.getItemStack();
        if (ItemUtils.testItemStack(itemStack)) {
            value3 *= 0.15;
        } else {
            value3 *= protocolPlayer.itemFloodMultiplier();
        }
        if (this.repeatedIntervalCounter.increment(value3)) {
            FlagInfo[] flagInfos =
                    new FlagInfo[] {
                        FlagInfo.of("max-counter", this.repeatedIntervalCounter.getMaxCounter()),
                        null
                    };
            flagInfos[1] = FlagInfo.of("size", decodedSizeBytes);
            this.flagViolation(itemStackDecodeEvent, flagInfos);
            return CheckResult.DENY;
        }
        if (this.rollingIntervalCounter != null) {
            if (this.rollingIntervalCounter.increment(value3)) {
                FlagInfo[] flagInfos2 = new FlagInfo[4];
                flagInfos2[0] = FlagInfo.of("mode", "rolling");
                flagInfos2[1] =
                        FlagInfo.of("max-counter", this.rollingIntervalCounter.getMaxCounter());
                flagInfos2[2] =
                        FlagInfo.of("interval", this.rollingIntervalCounter.getInterval() + "ms");
                flagInfos2[3] = FlagInfo.of("size", decodedSizeBytes);
                this.flagViolation(itemStackDecodeEvent, flagInfos2);
                return CheckResult.DENY;
            }
            if (this.rollingIntervalCounter.isOverLimit()) {
                return CheckResult.DENY;
            }
        }
        return CheckResult.ALLOW;
    }

    public boolean isPreVia() {
        return true;
    }
}
