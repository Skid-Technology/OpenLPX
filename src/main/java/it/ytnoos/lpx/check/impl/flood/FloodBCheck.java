package it.ytnoos.lpx.check.impl.flood;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.RepeatedIntervalCounter;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.check.AbstractCheck;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.player.ProtocolPlayerImpl;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class FloodBCheck extends AbstractCheck<PacketReceiveEvent> {

    private Map<PacketTypeCommon, List<FloodBCheck.WindowSample>> entries;

    public FloodBCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        HashMap entries2 = new HashMap();
        this.entries = entries2;
        FileConfiguration fileConfiguration =
                ((FileConfiguration) ((FileConfiguration) this.getCheckConfiguration()));
        ConfigurationSection configurationSection =
                fileConfiguration.getConfigurationSection(this.createText((String) "limits"));
        if (configurationSection != null) {
            for (String text : configurationSection.getKeys(false)) {
                ArrayList<it.ytnoos.lpx.check.impl.flood.FloodBCheck.WindowSample> values =
                        new ArrayList<>();
                if (configurationSection.isList(text)) {
                    for (Map<?, ?> entries3 : configurationSection.getMapList(text)) {
                        values.add(this.createWindowSample(entries3));
                    }
                } else {
                    ConfigurationSection configurationSection2 =
                            configurationSection.getConfigurationSection(text);
                    if (configurationSection2 != null) {
                        values.add(this.createWindowSample(configurationSection2));
                    }
                }
                if (!values.isEmpty()) {
                    this.handleValue(text, values);
                }
            }
        } else {
            fileConfiguration =
                    ((FileConfiguration) ((FileConfiguration) this.getCheckConfiguration()));
            for (String text2 :
                    fileConfiguration.getStringList(this.createText((String) "limits"))) {
                String[] texts = text2.split(",");
                int value = texts.length;
                if (value < 2) {
                    ProtocolPlayerImpl protocolPlayerImpl = (ProtocolPlayerImpl) this.player;
                    Logger logger = protocolPlayerImpl.getLpx().getLogger();
                    logger.warning(() -> text2 + " is not a valid FloodB parameter!");
                } else {
                    try {
                        int value2 = Integer.parseInt(texts[1]);
                        RepeatedIntervalCounter repeatedIntervalCounter;
                        if (value == 2) {
                            RepeatedIntervalCounter repeatedIntervalCounter2 =
                                    new RepeatedIntervalCounter(value2, 6, 3);
                            repeatedIntervalCounter = repeatedIntervalCounter2;
                        } else if (value == 3) {
                            RepeatedIntervalCounter repeatedIntervalCounter3 =
                                    new RepeatedIntervalCounter(
                                            value2, Integer.parseInt(texts[2]), 3, 1);
                            repeatedIntervalCounter = repeatedIntervalCounter3;
                        } else {
                            RepeatedIntervalCounter repeatedIntervalCounter4 =
                                    new RepeatedIntervalCounter(
                                            value2,
                                            Integer.parseInt(texts[2]),
                                            Integer.parseInt(texts[3]),
                                            Integer.parseInt(texts[4]));
                            repeatedIntervalCounter = repeatedIntervalCounter4;
                        }
                        String text3 = texts[0];
                        long value3 = -1L;
                        long value4 = -1L;
                        RepeatedIntervalCounter repeatedIntervalCounter5 = repeatedIntervalCounter;
                        FloodBCheck.WindowSample windowSample =
                                new FloodBCheck.WindowSample(
                                        repeatedIntervalCounter5,
                                        (boolean) false,
                                        value4,
                                        value3,
                                        (boolean) false);
                        this.registerFallbackLimit(text3, windowSample);
                    } catch (NumberFormatException numberFormatException) {
                        ProtocolPlayerImpl protocolPlayerImpl2 = (ProtocolPlayerImpl) this.player;
                        protocolPlayerImpl2
                                .getLpx()
                                .getLogger()
                                .severe("Second and third parameter must be numbers for FloodB!");
                    }
                }
            }
        }
    }

    private FloodBCheck.WindowSample createWindowSample(ConfigurationSection section) {
        int intValue = section.getInt("max", 20);
        int intValue2 = section.getInt("interval", 500);
        int intValue3 = section.getInt("periods", 5);
        int intValue4 = section.getInt("warnings", 2);
        boolean matches = "drop".equalsIgnoreCase(section.getString("action", "flag"));
        long longValue = section.getLong("min-bytes", -1L);
        long longValue2 = section.getLong("max-bytes", -1L);
        boolean booleanValue = section.getBoolean("allow-duplicates", false);
        RepeatedIntervalCounter repeatedIntervalCounter =
                new RepeatedIntervalCounter(intValue, intValue2, intValue3, intValue4);
        return new FloodBCheck.WindowSample(
                repeatedIntervalCounter, matches, longValue, longValue2, booleanValue);
    }

    private FloodBCheck.WindowSample createWindowSample(Map entries) {
        Integer value = Integer.valueOf((byte) 20);
        Map entries2 = entries;
        Map entries3 = entries2;
        int intValue = getInt(entries3, (String) "max", ((int) value));
        Integer value2 = Integer.valueOf((short) 500);
        entries2 = entries;
        Map entries4 = entries2;
        int intValue2 = getInt(entries4, (String) "interval", ((int) value2));
        Integer value3 = Integer.valueOf((byte) 5);
        entries2 = entries;
        Map entries5 = entries2;
        int intValue3 = getInt(entries5, (String) "periods", ((int) value3));
        Integer value4 = Integer.valueOf((byte) 2);
        entries2 = entries;
        Map entries6 = entries2;
        int intValue4 = getInt(entries6, (String) "warnings", ((int) value4));
        Object result = entries.get("action");
        boolean matches = "drop".equalsIgnoreCase(result != null ? String.valueOf(result) : "flag");
        long value5 = -1L;
        Long value6 = ((Long) value5);
        entries2 = entries;
        Map entries7 = entries2;
        long longValue = getLong(entries7, (String) "min-bytes", ((long) value6));
        long value7 = -1L;
        Long value8 = ((Long) value7);
        entries2 = entries;
        Map entries8 = entries2;
        long longValue2 = getLong(entries8, (String) "max-bytes", ((long) value8));
        Boolean matches2 = ((Boolean) (boolean) false);
        entries2 = entries;
        Map entries9 = entries2;
        boolean booleanValue =
                getBoolean(entries9, (String) "allow-duplicates", ((boolean) matches2));
        RepeatedIntervalCounter repeatedIntervalCounter =
                new RepeatedIntervalCounter(intValue, intValue2, intValue3, intValue4);
        return new FloodBCheck.WindowSample(
                repeatedIntervalCounter, matches, longValue, longValue2, booleanValue);
    }

    private static int getInt(Map entries, Object result, int value) {
        Object result2 = entries.get(result);
        return result2 instanceof Number ? ((Number) result2).intValue() : value;
    }

    private static long getLong(Map entries, Object result, long value) {
        Object result2 = entries.get(result);
        return result2 instanceof Number ? ((Number) result2).longValue() : value;
    }

    private static boolean getBoolean(Map entries, Object result, boolean matches) {
        Object result2 = entries.get(result);
        if (result2 instanceof Boolean) {
            return (Boolean) result2;
        } else {
            return result2 instanceof String ? Boolean.parseBoolean((String) result2) : matches;
        }
    }

    public CheckResult check(ProtocolPlayer protocolPlayer, PacketReceiveEvent packetReceiveEvent) {
        if (protocolPlayer.getPrinterData().isUsingPrinter()) {
            return CheckResult.ALLOW;
        }
        FloodBCheck.PacketSample packetSample =
                this.createPacketSample(protocolPlayer, packetReceiveEvent);
        List<FloodBCheck.WindowSample> values = this.createValues(packetSample);
        if (values == null) {
            return CheckResult.ALLOW;
        }
        PacketTypeCommon packetType2 = packetReceiveEvent.getPacketType();
        double weight = this.getWeight(packetType2, protocolPlayer);
        boolean matches = false;
        long now = System.currentTimeMillis();
        for (FloodBCheck.WindowSample windowSample : values) {
            long time = packetSample.getTime();
            Long value = ((Long) time);
            if (windowSample.testTime(((long) value))) {
                long time2 = packetSample.getTime();
                Long value2 = ((Long) now);
                Long value3 = ((Long) time2);
                long value4 = ((long) value3);
                if (!windowSample.testTime(value4, ((long) value2))) {
                    boolean matches2 = windowSample.counter.increment(weight);
                    if (matches2) {
                        FlagInfo[] flagInfos = new FlagInfo[5];
                        flagInfos[0] = FlagInfo.of("packet", packetSample.packetType().getName());
                        flagInfos[1] =
                                FlagInfo.of("max-counter", windowSample.counter.getMaxCounter());
                        flagInfos[2] =
                                FlagInfo.of("interval", windowSample.counter.getInterval() + "ms");
                        flagInfos[3] =
                                FlagInfo.of("max-periods", windowSample.counter.getMaxPeriods());
                        flagInfos[4] =
                                FlagInfo.of("max-warnings", windowSample.counter.getMaxWarnings());
                        this.flagViolation(packetReceiveEvent, flagInfos);
                    }
                    if (windowSample.enabled && (matches2 || windowSample.counter.isOverLimit())) {
                        matches = true;
                    }
                }
            }
        }
        return matches ? CheckResult.DENY : CheckResult.ALLOW;
    }

    private List<it.ytnoos.lpx.check.impl.flood.FloodBCheck.WindowSample> createValues(
            FloodBCheck.PacketSample packetSample) {
        return this.entries.get(packetSample.packetType());
    }

    private FloodBCheck.PacketSample createPacketSample(
            ProtocolPlayer protocolPlayer, PacketReceiveEvent packetReceiveEvent) {
        PacketTypeCommon packetType = packetReceiveEvent.getPacketType();
        return new FloodBCheck.PacketSample(packetType, this.calculateTime(protocolPlayer));
    }

    private long calculateTime(ProtocolPlayer protocolPlayer) {
        return protocolPlayer.getLastReceivedPacket() != null
                ? protocolPlayer.getLastReceivedPacket().getSize()
                : -1L;
    }

    private void handleValue(String text, Object result) {
        String text2 = normalizePacketName(text);
        Client client = this.createClient(text2);
        if (client == null) {
            this.warnInvalidPacketKey(text);
        } else {
            this.entries.put(client, (List<FloodBCheck.WindowSample>) result);
        }
    }

    private void registerFallbackLimit(String text, Object result) {
        String text2 = normalizePacketName(text);
        Client client = this.createClient(text2);
        if (client == null) {
            this.warnInvalidPacketKey(text);
        } else {
            this.entries
                    .computeIfAbsent(client, result2 -> new ArrayList<>())
                    .add((FloodBCheck.WindowSample) result);
        }
    }

    private Client createClient(String text) {
        try {
            return Client.valueOf(text);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    private void warnInvalidPacketKey(String text) {
        ProtocolPlayerImpl protocolPlayerImpl = (ProtocolPlayerImpl) this.player;
        Logger logger = protocolPlayerImpl.getLpx().getLogger();
        logger.warning(
                () -> {
                    return (String) "Invalid FloodB packet key '"
                            + text
                            + "' in config, skipping it.";
                });
    }

    private static String normalizePacketName(String text) {
        return text.trim().toUpperCase(Locale.ROOT);
    }

    private double getWeight(PacketTypeCommon packetTypeCommon, ProtocolPlayer protocolPlayer) {
        return packetTypeCommon != Client.CLICK_WINDOW
                        && packetTypeCommon != Client.CREATIVE_INVENTORY_ACTION
                ? 1.0
                : protocolPlayer.itemFloodMultiplier();
    }

    public boolean isPreVia() {
        return true;
    }

    record PacketSample(PacketTypeCommon packetType, long time) {

        public long getTime() {
            return this.time;
        }
    }

    static final class WindowSample {

        private final RepeatedIntervalCounter counter;

        private final boolean enabled;

        private final long time;

        private final long maxBytes;

        private final boolean enabled2;

        private long time2 = Long.MIN_VALUE;

        private long time3 = Long.MIN_VALUE;

        private WindowSample(
                RepeatedIntervalCounter repeatedIntervalCounter,
                boolean enabled,
                long time,
                long maxBytes,
                boolean enabled2) {
            this.counter = repeatedIntervalCounter;
            this.enabled = enabled;
            this.time = time;
            this.maxBytes = maxBytes;
            this.enabled2 = enabled2;
        }

        public RepeatedIntervalCounter counter() {
            return this.counter;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public boolean testTime(long value) {
            if (value < 0L) {
                return this.time < 0L && this.maxBytes < 0L;
            } else {
                return this.time >= 0L && value < this.time
                        ? false
                        : this.maxBytes < 0L || value <= this.maxBytes;
            }
        }

        public boolean testTime(long time2, long time3) {
            if (this.enabled2 && time2 >= 0L) {
                boolean matches =
                        this.time2 == time2 && time3 - this.time3 <= this.counter.getInterval();
                this.time2 = time2;
                this.time3 = time3;
                return matches;
            } else {
                return false;
            }
        }
    }
}
