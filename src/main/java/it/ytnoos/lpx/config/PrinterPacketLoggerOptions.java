package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;

public class PrinterPacketLoggerOptions extends ConfigSection {

    private final long time;

    private final long time2;

    private final long time3;

    public PrinterPacketLoggerOptions(LPXPlugin lPXPlugin, String text, Messages messages) {
        super(lPXPlugin, text + ".printer", messages);
        Long value = ((Long) 50L);
        this.time = this.getLong((String) "max-time", value);
        Long value2 = ((Long) 25L);
        this.time2 = this.getLong((String) "check-frequency", value2);
        Long value3 = ((Long) 5000L);
        this.time3 = this.getLong((String) "report-cooldown", value3);
    }

    public long getTime() {
        return this.time;
    }

    public long getTime2() {
        return this.time2;
    }

    public long getTime3() {
        return this.time3;
    }
}
