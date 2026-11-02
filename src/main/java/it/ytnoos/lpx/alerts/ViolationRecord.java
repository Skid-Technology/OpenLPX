package it.ytnoos.lpx.alerts;

import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.PersonalCheckOptions;

public class ViolationRecord {

    private final String name;

    private final PersonalCheckOptions checkOptions;

    private final long timestamp;

    private final FlagInfo[] info;

    public ViolationRecord(
            String name,
            PersonalCheckOptions personalCheckOptions,
            long timestamp,
            FlagInfo[] flagInfos) {
        this.name = name;
        this.checkOptions = personalCheckOptions;
        this.timestamp = timestamp;
        this.info = flagInfos;
    }

    public String getName() {
        return this.name;
    }

    public PersonalCheckOptions getCheckOptions() {
        return this.checkOptions;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public FlagInfo[] getInfo() {
        return this.info;
    }
}
