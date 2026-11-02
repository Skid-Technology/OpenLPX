package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class PacketLoggerOptions extends ConfigSection {

    private final long time;

    private final long time2;

    private final Set<String> values;

    private final Set<String> values2;

    private final boolean enabled;

    private final PacketLogOutput packetLogOutput;

    private final PrinterPacketLoggerOptions printerPacketLoggerOptions;

    private final PacketLoggerOptions.FilterMode filterMode;

    private final List<String> players;

    private final PacketLoggerOptions.FilterMode filterMode2;

    private final List<String> packets;

    public PacketLoggerOptions(Messages messages, LPXPlugin lPXPlugin) {
        super(lPXPlugin, "packet-logger", messages);
        Long value = ((Long) 5000000L);
        this.time = this.getLong((String) "heavy-packet-threshold", value);
        Long value2 = ((Long) 50000000L);
        this.time2 = this.getLong((String) "heavy-listed-packet-threshold", value2);
        HashSet values3 = new HashSet(this.createValues((String) "heavy-ignored-packets"));
        this.values = values3;
        HashSet values4 = new HashSet(this.createValues((String) "heavy-listed-packets"));
        this.values2 = values4;
        this.enabled = this.testText((String) "enabled");
        String string = this.getString((String) "output");
        this.packetLogOutput = this.createPacketLogOutput(string);
        PrinterPacketLoggerOptions printerPacketLoggerOptions2 =
                new PrinterPacketLoggerOptions(lPXPlugin, (String) "packet-logger", messages);
        this.printerPacketLoggerOptions = printerPacketLoggerOptions2;
        String string2 = this.getString((String) "player-mode");
        this.filterMode = this.getType(string2);
        this.players = this.createValues((String) "players");
        String string3 = this.getString((String) "packet-mode");
        this.filterMode2 = this.getType(string3);
        this.packets = this.createValues((String) "packets");
    }

    private PacketLoggerOptions.FilterMode getType(String text) {
        try {
            return PacketLoggerOptions.FilterMode.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException illegalArgumentException) {
            Logger logger = this.plugin.getLogger();
            logger.warning(
                    (String) "Invalid logger type " + text + ", using default value (whitelist)");
            return PacketLoggerOptions.FilterMode.WHITELIST;
        }
    }

    private PacketLogOutput createPacketLogOutput(String text) {
        try {
            return PacketLogOutput.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException illegalArgumentException) {
            Logger logger = this.plugin.getLogger();
            logger.warning((String) "Invalid output type " + text + ", using default value (file)");
            return PacketLogOutput.FILE;
        }
    }

    @Override
    public boolean testText(String text) {
        return this.filterMode == PacketLoggerOptions.FilterMode.WHITELIST
                ? this.players.contains(text)
                : !this.players.contains(text);
    }

    public boolean testText2(String text) {
        return this.filterMode2 == PacketLoggerOptions.FilterMode.WHITELIST
                ? this.packets.contains(text)
                : !this.packets.contains(text);
    }

    public boolean testTime(long value) {
        return this.time == -1L ? false : value >= this.time;
    }

    public boolean testTime(Object result, long value) {
        return this.time2 == -1L ? false : this.values2.contains(result) && value >= this.time2;
    }

    public boolean testValue(Object result) {
        return this.values2.contains(result);
    }

    public boolean testValue2(Object result) {
        return this.values.contains(result);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public PrinterPacketLoggerOptions getPrinterPacketLoggerOptions() {
        return this.printerPacketLoggerOptions;
    }

    public PacketLogOutput getPacketLogOutput() {
        return this.packetLogOutput;
    }

    enum FilterMode {
        WHITELIST,
        BLACKLIST;

        private static final PacketLoggerOptions.FilterMode[] FILTER_MODES = createFilterModes();

        private static PacketLoggerOptions.FilterMode[] createFilterModes() {
            PacketLoggerOptions.FilterMode[] filterModes =
                    new PacketLoggerOptions.FilterMode[] {WHITELIST, null};
            filterModes[1] = BLACKLIST;
            return filterModes;
        }
    }
}
