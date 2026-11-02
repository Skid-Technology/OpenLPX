package it.ytnoos.lpx.config;

import java.util.List;

public final class ConfigMigration {

    static final List<ConfigMigration.Version> VERSIONS;

    private ConfigMigration() {}

    private static ConfigMigration.Change createChange(String text, String text2, String[] texts) {
        return new ConfigMigration.Change(text, text2, List.of(texts));
    }

    static {
        ConfigMigration.Change[] changes = new ConfigMigration.Change[22];
        String[] texts = new String[5];
        texts[0] = "64";
        texts[1] = "128";
        texts[2] = "256";
        texts[3] = "1024";
        texts[4] = "1500";
        changes[0] = createChange((String) "checks.creative.c.options.max", (String) "3000", texts);
        String[] texts2 = new String[] {"2048"};
        changes[1] =
                createChange((String) "checks.creative.d.options.max", (String) "16384", texts2);
        String[] texts3 = new String[] {"900", null};
        texts3[1] = "1000";
        changes[2] = createChange((String) "checks.flood.a.options.max", (String) "1100", texts3);
        String[] texts4 = new String[] {"60"};
        changes[3] =
                createChange(
                        (String) "checks.flood.b.options.limits.USE_ITEM.max",
                        (String) "80",
                        texts4);
        String[] texts5 = new String[] {"2048", null};
        texts5[1] = "4096";
        changes[4] =
                createChange(
                        (String) "checks.flood.c.options.big-threshold", (String) "6000", texts5);
        String[] texts6 = new String[5];
        texts6[0] = "70";
        texts6[1] = "80";
        texts6[2] = "50";
        texts6[3] = "10";
        texts6[4] = "15";
        changes[5] = createChange((String) "checks.flood.c.options.max", (String) "20", texts6);
        String[] texts7 = new String[] {"500"};
        changes[6] =
                createChange(
                        (String) "checks.flood.c.options.reset-interval", (String) "100", texts7);
        String[] texts8 = new String[] {"256", null};
        texts8[1] = "2048";
        changes[7] =
                createChange(
                        (String) "checks.flood.d.options.big-threshold", (String) "4000", texts8);
        String[] texts9 = new String[] {"10"};
        changes[8] = createChange((String) "checks.flood.d.options.max", (String) "13", texts9);
        String[] texts10 = new String[] {"4"};
        changes[9] = createChange((String) "checks.flood.d.options.max-big", (String) "6", texts10);
        String[] texts11 = new String[] {"500"};
        changes[10] =
                createChange((String) "checks.flood.g.options.interval", (String) "250", texts11);
        String[] texts12 = new String[] {"65536", null};
        texts12[1] = "131328";
        changes[11] =
                createChange((String) "checks.netty.a.options.max", (String) "98304", texts12);
        String[] texts13 = new String[] {"3"};
        changes[12] =
                createChange(
                        (String) "checks.netty.b.options.max-item-depth", (String) "4", texts13);
        String[] texts14 = new String[] {"20", null};
        texts14[1] = "40";
        changes[13] = createChange((String) "checks.place.a.options.max", (String) "100", texts14);
        String[] texts15 = new String[] {"40"};
        changes[14] = createChange((String) "checks.place.c.options.max", (String) "50", texts15);
        String[] texts16 = new String[] {"5000"};
        changes[15] =
                createChange((String) "checks.position.b.options.delay", (String) "2000", texts16);
        String[] texts17 = new String[] {"2048", null, null};
        texts17[1] = "15000";
        texts17[2] = "30000";
        changes[16] =
                createChange((String) "checks.position.c.options.max", (String) "300000", texts17);
        String[] texts18 = new String[] {"100000"};
        changes[17] =
                createChange((String) "checks.position.d.options.max", (String) "1000000", texts18);
        String[] texts19 = new String[] {"2048", null};
        texts19[1] = "64";
        changes[18] =
                createChange((String) "checks.tab.b.options.max-length", (String) "256", texts19);
        String[] texts20 = new String[] {"80"};
        changes[19] =
                createChange(
                        (String) "options.mechanics.interact-container-delay",
                        (String) "100",
                        texts20);
        String[] texts21 = new String[] {"32"};
        changes[20] =
                createChange(
                        (String) "checks.window.e.options.max-hashed-slot-changes",
                        (String) "90",
                        texts21);
        String[] texts22 = new String[] {"32"};
        changes[21] =
                createChange(
                        (String) "checks.window.e.options.max-hashed-components-per-stack",
                        (String) "128",
                        texts22);
        ConfigMigration.Version version = new ConfigMigration.Version(1, List.of(changes));
        String[] texts23 = new String[] {"1800"};
        ConfigMigration.Version version2 =
                new ConfigMigration.Version(
                        2,
                        List.of(
                                createChange(
                                        (String)
                                                "checks.netty.b.options.max-nbt-elements-per-packet",
                                        (String) "2500",
                                        texts23)));
        VERSIONS = List.of(version, version2);
    }

    record Change(String path, String newValue, List<String> values) {

        public List<String> getValues() {
            return this.values;
        }
    }

    record Version(int version, List<ConfigMigration.Change> values) {

        public List<ConfigMigration.Change> getValues() {
            return this.values;
        }
    }
}
