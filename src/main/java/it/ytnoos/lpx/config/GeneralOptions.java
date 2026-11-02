package it.ytnoos.lpx.config;

import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.settings.PacketEventsSettings;

import org.bukkit.configuration.file.FileConfiguration;

public class GeneralOptions extends ConfigSection {

    private final boolean enabled;

    private final boolean enabled2;

    private final boolean enabled3;

    private final boolean enabled4;

    private final boolean enabled5;

    private final boolean geyser;

    private final boolean enabled6;

    private final boolean enabled7;

    private final boolean bStats;

    private final boolean enabled8;

    private final int limit;

    private final PrinterOptions printerOptions;

    private final ItemStripOptions itemStripOptions;

    private final int limit2;

    private final boolean enabled9;

    private final int limit3;

    private final boolean enabled10;

    private final int limit4;

    private final boolean enabled11;

    private final boolean enabled12;

    private final boolean enabled13;

    private final boolean enabled14;

    private final long time;

    private final boolean enabled15;

    private final int limit5;

    private final String server;

    private final int limit6;

    private final boolean debug;

    private final boolean enabled16;

    public GeneralOptions(Messages messages, LPXPlugin lPXPlugin) {
        super(lPXPlugin, "options", messages);
        this.enabled = this.testText((String) "silent-failures");
        PacketEvents.getAPI().getSettings().ignoreFailures(this.enabled);
        this.enabled2 = this.testText((String) "alerts-on-join");
        this.enabled3 = this.testText((String) "kick-on-exception");
        PacketEvents.getAPI().getSettings().kickOnPacketException(this.enabled3);
        this.update();
        this.enabled4 = this.testText((String) "hidden-command");
        this.enabled5 = this.testText((String) "bypass-permission");
        this.geyser = this.testText((String) "geyser");
        this.enabled6 = this.testText((String) "axiom");
        if (this.enabled6) {
            PacketEvents.getAPI().getSettings().maxPayloadMultiplier(15);
        }
        this.enabled7 = this.testText((String) "check-updates");
        this.bStats = this.getConfig().getBoolean(this.key + ".bstats", true);
        this.enabled8 = this.testText((String) "clear-task.enabled");
        this.limit = this.getInt((String) "clear-task.delay");
        PrinterOptions printerOptions2 =
                new PrinterOptions(lPXPlugin, (String) "options", messages);
        this.printerOptions = printerOptions2;
        ItemStripOptions itemStripOptions2 =
                new ItemStripOptions(lPXPlugin, (String) "options", messages);
        this.itemStripOptions = itemStripOptions2;
        this.limit2 = this.getInt((String) "mechanics.nether-portal-delay");
        this.enabled9 = this.limit2 != -1;
        this.limit3 = this.getInt((String) "mechanics.max-arrow-velocity");
        this.enabled10 = this.limit3 != -1;
        this.limit4 = this.getInt((String) "mechanics.shears-cooldown");
        this.enabled11 = this.limit4 != -1;
        this.enabled12 = this.testText((String) "mechanics.break-close-inventory");
        this.enabled13 = this.testText((String) "mechanics.trapdoor-rail-redstone");
        this.time = this.getInt((String) "mechanics.interact-container-delay");
        this.enabled14 = this.time != -1L;
        this.enabled15 = this.testText((String) "alerts.store");
        this.limit5 = this.getInt((String) "alerts.days");
        this.server = this.getString((String) "server");
        this.limit6 = this.getInt((String) "punish-delay");
        this.debug = this.testText((String) "debug");
        this.enabled16 = this.getConfig().getBoolean(this.key + ".log-cancelled-packets", true);
    }

    private void update() {
        PacketEventsSettings settings = PacketEvents.getAPI().getSettings();
        Integer value = Integer.valueOf((byte) 4);
        String text = (String) "max-item-depth";
        String text2 = text;
        settings.maxItemDepth(this.calculateLimit(text2, ((int) value)));
        Integer value2 = Integer.valueOf((short) 1458);
        String text3 = "max-items-per-packet";
        text = text3;
        String text4 = text;
        settings.maxItemsPerPacket(this.calculateLimit(text4, ((int) value2)));
        Integer value3 = Integer.valueOf((short) 2500);
        String text5 = "max-nbt-elements-per-packet";
        text = text5;
        String text6 = text;
        settings.maxReceivedNbtElementsPerPacket(this.calculateLimit(text6, ((int) value3)));
        Integer value4 = Integer.valueOf((byte) 64);
        String text7 = "max-nbt-depth";
        text = text7;
        String text8 = text;
        settings.maxReceivedNbtDepth(this.calculateLimit(text8, ((int) value4)));
    }

    private int calculateLimit(String text, int value) {
        FileConfiguration config = this.getConfig();
        return config.getInt((String) "checks.netty.b.options." + text, value);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isEnabled2() {
        return this.enabled2;
    }

    public boolean isEnabled3() {
        return this.enabled3;
    }

    public boolean isEnabled4() {
        return this.enabled4;
    }

    public boolean isEnabled5() {
        return this.enabled5;
    }

    public boolean isGeyser() {
        return this.geyser;
    }

    public boolean isEnabled6() {
        return this.enabled6;
    }

    public boolean isEnabled7() {
        return this.enabled7;
    }

    public boolean isBStats() {
        return this.bStats;
    }

    public boolean isEnabled8() {
        return this.enabled8;
    }

    public int getLimit() {
        return this.limit;
    }

    public PrinterOptions getPrinterOptions() {
        return this.printerOptions;
    }

    public ItemStripOptions getItemStripOptions() {
        return this.itemStripOptions;
    }

    public int getLimit2() {
        return this.limit2;
    }

    public boolean isEnabled9() {
        return this.enabled9;
    }

    public int getLimit3() {
        return this.limit3;
    }

    public boolean isEnabled10() {
        return this.enabled10;
    }

    public int getLimit4() {
        return this.limit4;
    }

    public boolean isEnabled11() {
        return this.enabled11;
    }

    public boolean isEnabled12() {
        return this.enabled12;
    }

    public boolean isEnabled13() {
        return this.enabled13;
    }

    public boolean isEnabled14() {
        return this.enabled14;
    }

    public long getTime() {
        return this.time;
    }

    public boolean isEnabled15() {
        return this.enabled15;
    }

    public int getLimit5() {
        return this.limit5;
    }

    public String getServer() {
        return this.server;
    }

    public int getLimit6() {
        return this.limit6;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public boolean isEnabled16() {
        return this.enabled16;
    }
}
